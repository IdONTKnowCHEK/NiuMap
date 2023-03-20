package niu.edu.niumap;

import android.annotation.SuppressLint;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Collections;
import java.util.Comparator;

public class ListFragment extends Fragment {

    private EditText editText;
    private View view;
    // private ArrayAdapter<String> arrayAdapter;
    private ListRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    // private ArrayList<Building> buildingArrayList;
    private Button searchButton;
    // private String[] building_items = {"行政大樓", "綜合教學大樓", "農林修養室", "教穡大樓", "生資大樓", "格致大樓", "工學大樓", "人文及管理學院",
    //       "求真樓", "圖書資訊館", "體育館", "學生活動中心", "經德大樓", "語言中心", "礪金滋蘭學舍", "時化學舍", "時習大樓"};
    private FusedLocationProviderClient fusedLocationClient;
    private Query query;
    private FirebaseRecyclerOptions<Building> options;
    private int i = 0;

    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.building_listView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        query = FirebaseDatabase.getInstance().getReference().child("building");
        options = new FirebaseRecyclerOptions.Builder<Building>()
                .setQuery(query, Building.class)
                .build();
        adapter = new ListRecyclerAdapter(options);
        recyclerView.setAdapter(adapter);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        DataHolder.getInstance().setData(location);
                        options = new FirebaseRecyclerOptions.Builder<Building>()
                                .setQuery(query.orderByChild("distance"), Building.class)
                                .build();
                        adapter = new ListRecyclerAdapter(options);
                        recyclerView.setAdapter(adapter);
                        adapter.startListening();

                    }
                });
        editText = (EditText) view.findViewById(R.id.search_bar_list);
        searchButton = (Button) view.findViewById(R.id.search_button_list);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchKey = editText.getText().toString();
                if(!searchKey.equals("")){
                    FirebaseSearch(searchKey);
                }
            }
        });
        return view;
    }



    private void FirebaseSearch(String searchKey) {
        Toast.makeText(getActivity(), "Search：" + searchKey, Toast.LENGTH_SHORT).show();
        Query firebaseSearchQuery = FirebaseDatabase.getInstance().getReference().child("building").orderByChild("title").startAt(searchKey).endAt(searchKey + "\uf8ff");
        FirebaseRecyclerOptions<Building> options = new FirebaseRecyclerOptions.Builder<Building>()
                .setQuery(firebaseSearchQuery, Building.class)
                .build();
        adapter = new ListRecyclerAdapter(options);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
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

}