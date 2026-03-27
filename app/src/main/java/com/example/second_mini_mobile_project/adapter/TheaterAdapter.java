package com.example.second_mini_mobile_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appth.R;
import com.example.appth.data.entity.Theater;

import java.util.List;

public class TheaterAdapter extends RecyclerView.Adapter<TheaterAdapter.ViewHolder> {

    private final List<Theater> theaters;

    public TheaterAdapter(List<Theater> theaters) {
        this.theaters = theaters;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theater, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Theater theater = theaters.get(position);
        holder.tvName.setText(theater.name);
        holder.tvAddress.setText(theater.address);
        holder.tvSeats.setText("Sức chứa: " + theater.totalSeats + " ghế");
    }

    @Override
    public int getItemCount() {
        return theaters.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvSeats;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvTheaterName);
            tvAddress = itemView.findViewById(R.id.tvTheaterAddress);
            tvSeats = itemView.findViewById(R.id.tvTotalSeats);
        }
    }
}
