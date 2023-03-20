package niu.edu.niumap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MapRecyclerAdapter extends FirebaseRecyclerAdapter<Building, MapRecyclerAdapter.BuildingViewHolder> {

    public MapRecyclerAdapter(@NonNull FirebaseRecyclerOptions<Building> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BuildingViewHolder holder, int position, @NonNull Building model) {
        Glide.with(holder.building_image.getContext()).load(model.getImage()).into(holder.building_image);
    }

    @NonNull
    @Override
    public BuildingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_hor, parent,false);
        return new BuildingViewHolder(view);
    }

    public static class BuildingViewHolder extends RecyclerView.ViewHolder {

        ImageView building_image;

        public BuildingViewHolder(@NonNull View itemView) {
            super(itemView);
            building_image = itemView.findViewById(R.id.building_image_hor);
        }
    }
}
