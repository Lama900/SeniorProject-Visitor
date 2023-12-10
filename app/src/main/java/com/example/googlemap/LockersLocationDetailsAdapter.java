package com.example.googlemap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.googlemap.models.LockersLocation;
import com.example.googlemap.utils.Globals;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

public class LockersLocationDetailsAdapter extends RecyclerView.Adapter<LockersLocationDetailsAdapter.ViewHolder> {
    private List<LockersLocation> markerInfoList;
    private Context context; // Add a context field
    private Marker marker;
    public LockersLocationDetailsAdapter(Context context, List<LockersLocation> markerInfoList) {
        this.context = context;
        this.markerInfoList = markerInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marker_info_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LockersLocation lockersLocation = markerInfoList.get(position);

        // Bind the properties from the Model to your TextViews or other views
        holder.textViewTitle.setText(lockersLocation.getName());
        holder.textViewDescription.setText("Location: " + lockersLocation.getAddress());
        holder.textViewLockers.setText("Number of Locker: " + lockersLocation.getNumberOfLockers());
        holder.textViewCapacity.setText("Available Lockers: " + availableLockers(lockersLocation));

        // Add a click listener to the item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle item click here
                // For example, start SeatBookingActivity with the selected Model
                Intent intent = new Intent(context, LockersBookingScreen.class);
                intent.putExtra("model", lockersLocation); // Pass the selected Model to the next activity
                context.startActivity(intent);
            }
        });
        // Handle button clicks
        holder.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle "Next" button click here
                // For example, start SeatBookingActivity with the selected Model
                Intent intent = new Intent(context, LockersBookingScreen.class);
                Globals.currentPost = lockersLocation;
                context.startActivity(intent);
            }
        });

    }

    private int availableLockers(LockersLocation selectedLockersLocation)
    {
        return selectedLockersLocation.getNumberOfLockers() - selectedLockersLocation.getBookedLockers().size();

    }


    @Override
    public int getItemCount() {
        return markerInfoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewCapacity;
        TextView textViewDescription;
        TextView textViewLockers;
        Button buttonNext;


        ViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewCapacity = itemView.findViewById(R.id.textViewCapacity);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewLockers = itemView.findViewById(R.id.textViewLockers); // Initialize other views here if needed
            buttonNext = itemView.findViewById(R.id.buttonNext);
        }
    }
}