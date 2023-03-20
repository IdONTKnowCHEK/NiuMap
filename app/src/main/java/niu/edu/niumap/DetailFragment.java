package niu.edu.niumap;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;


public class DetailFragment extends Fragment {

    public String title;
    public String image;
    public double latitude;
    public double longitude;
    public String description;

    public DetailFragment() {
        // Required empty public constructor
    }


    public DetailFragment(String title, String image, double latitude, double longitude, String description) {
        this.title = title;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    ImageView building_image;
    TextView building_title;
    TextView building_description;
    Button back_button;
    Button forward_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        building_image = (ImageView) view.findViewById(R.id.building_image_detail);
        building_title = (TextView) view.findViewById(R.id.title_detail);
        building_description = (TextView) view.findViewById(R.id.text_description);
        back_button = (Button) view.findViewById(R.id.back_button);
        forward_button = (Button) view.findViewById(R.id.forward_button);

        Glide.with(getContext()).load(image).into(building_image);
        building_title.setText(title);
        building_description.setText(description.replace("\\n","\n"));

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        forward_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onForwardPressed();
            }
        });

        return view;
    }

    public void onForwardPressed() {
        MapFragment myMapFragment = new MapFragment();
        Bundle data = new Bundle();
        data.putString("title", title);
        data.putDouble("latitude", latitude);
        data.putDouble("longitude", longitude);
        myMapFragment.setArguments(data);
        AppCompatActivity activity = (AppCompatActivity) getContext();
        MainActivity.chipNavigationBar.setItemSelected(R.id.nav_map, true);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myMapFragment).commit();
    }

    public void onBackPressed() {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ListFragment()).commit();
    }
}