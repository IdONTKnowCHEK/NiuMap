package niu.edu.niumap;

import static java.lang.Math.round;

import android.annotation.SuppressLint;
import android.app.AppComponentFactory;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashSet;
import java.util.Set;

public class ListRecyclerAdapter extends FirebaseRecyclerAdapter<Building, ListRecyclerAdapter.BuildingViewHolder> {
    private FusedLocationProviderClient fusedLocationClient;
    private Location currentLocation = DataHolder.getInstance().getData();
    private int distance;
    private Set<String> set = new HashSet<>();

    public ListRecyclerAdapter(@NonNull FirebaseRecyclerOptions<Building> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BuildingViewHolder holder, int position, @NonNull Building model) {
        Location location = new Location("l");
        location.setLatitude(model.getLatitude());
        location.setLongitude(model.getLongitude());
        distance = round(currentLocation.distanceTo(location));
        if (set.add(model.title)) {
            FirebaseDatabase.getInstance().getReference().child("building").child(model.getTitle()).child("distance").setValue(distance);
        }
        holder.building_name.setText(model.getTitle());
        holder.building_distance.setText("距離約 " + distance + " 公尺");
        Glide.with(holder.building_image.getContext()).load(model.getImage()).into(holder.building_image);
        holder.building_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DetailFragment(model.getTitle(), model.getImage(), model.getLatitude(), model.getLongitude(), model.getDescription())).commit();
            }
        });
    }

    @SuppressLint("MissingPermission")
    @NonNull
    @Override
    public BuildingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new BuildingViewHolder(view);
    }

    public static class BuildingViewHolder extends RecyclerView.ViewHolder {

        CardView building_card;
        ImageView building_image;
        TextView building_name;
        TextView building_distance;

        public BuildingViewHolder(@NonNull View itemView) {
            super(itemView);
            building_image = itemView.findViewById(R.id.building_image);
            building_name = itemView.findViewById(R.id.building_text);
            building_distance = itemView.findViewById(R.id.distance_text);
            building_card = itemView.findViewById(R.id.building_card);
        }
    }
}
