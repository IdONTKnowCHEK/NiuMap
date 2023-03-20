package niu.edu.niumap;

import android.Manifest.permission;
import android.annotation.SuppressLint;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MapFragment extends Fragment implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final String TAG = MapFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private MapRecyclerAdapter adapter;
    private EditText editText;
    private Button button;
    private Marker marker;
    private int i = 0;
    private GoogleMap map;
    private Bundle data;
    LatLngBounds australiaBounds = new LatLngBounds(
            new LatLng(24.7457, 121.7435), // SW bounds
            new LatLng(24.7472, 121.7479)  // NE bounds
    );

    LatLng niuView = new LatLng(24.7463, 121.74606);
    CameraPosition cameraPosition = new CameraPosition.Builder()
            .target(niuView)      // Sets the center of the map to Mountain View
            .zoom(16.95f)                   // Sets the zoom
            .bearing(270)                // Sets the orientation of the camera to east
            .tilt(0)                   // Sets the tilt of the camera to 30 degrees
            .build();                   // Creates a CameraPosition from the builder


    LatLngBounds newarkBounds = new LatLngBounds(
            new LatLng(24.74477, 121.74235),       // South west corner
            new LatLng(24.74825, 121.74930));      // North east corner




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.building_listView_hor);
        editText = (EditText) view.findViewById(R.id.search_bar_map);
        button = (Button) view.findViewById(R.id.search_button_map);
        recyclerView.setLayoutManager(layoutManager);
        FirebaseRecyclerOptions<Building> options = new FirebaseRecyclerOptions.Builder<Building>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("building"), Building.class)
                .build();
        adapter = new MapRecyclerAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapSearch();
            }
        });
        data = getArguments();
        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.setOnMyLocationButtonClickListener(this);
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        map.setLatLngBoundsForCameraTarget(australiaBounds);
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = map.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.map_style));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.school_map))
                .positionFromBounds(newarkBounds);
        map.addGroundOverlay(newarkMap);
        map.setIndoorEnabled(false);
        enableMyLocation();
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                addMarker(latLng, latLng.latitude + " : " + latLng.longitude);
            }
        });

        getLocation(data);
    }

    private void getLocation(Bundle data) {
        if(data != null){
            LatLng building = new LatLng(data.getDouble("latitude"), data.getDouble("longitude"));
            addMarker(building, data.getString("title"));
        }
    }

    private void mapSearch() {
        String searchKey = editText.getText().toString();
        if (!searchKey.equals("")) {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            try {
                List<Address> addressList = geocoder.getFromLocationName("宜蘭大學" + searchKey, 1);
                if (addressList.size() > 0) {
                    Toast.makeText(getActivity(), "Search：" + searchKey, Toast.LENGTH_SHORT).show();
                    LatLng latLng = new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());
                    addMarker(latLng, searchKey);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addMarker(LatLng latLng, String title) {
        if(i != 0){
            marker.remove();
        }
        i++;
        marker = map.addMarker(
                new MarkerOptions()
                        .position(latLng)
                        .title(title)
        );
        marker.showInfoWindow();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @SuppressLint("MissingPermission")
    public void enableMyLocation() {
        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            return;
        }

        // 2. Otherwise, request location permissions from the user.

        PermissionUtils.requestLocationPermissions(getActivity(), LOCATION_PERMISSION_REQUEST_CODE, true);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getActivity(), "Show me where", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getActivity(), "Current location:\n" + location, Toast.LENGTH_LONG)
                .show();
    }


}