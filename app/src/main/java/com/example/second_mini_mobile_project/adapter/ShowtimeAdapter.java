package com.example.second_mini_mobile_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appth.R;
import com.example.appth.model.ShowtimeDetail;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ShowtimeAdapter extends RecyclerView.Adapter<ShowtimeAdapter.ViewHolder> {

    public interface OnBookClickListener {
        void onBookClick(ShowtimeDetail showtime);
    }

    private final List<ShowtimeDetail> showtimes;
    private final OnBookClickListener listener;

    public ShowtimeAdapter(List<ShowtimeDetail> showtimes, OnBookClickListener listener) {
        this.showtimes = showtimes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_showtime, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShowtimeDetail sd = showtimes.get(position);
        holder.tvMovieTitle.setText(sd.movieTitle);
        holder.tvTheaterName.setText(sd.theaterName);
        holder.tvDateTime.setText(sd.showDate + "  |  " + sd.showTime);
        String priceStr = NumberFormat.getNumberInstance(new Locale("vi", "VN")).format(sd.price) + " VNĐ";
        holder.tvPrice.setText(priceStr);
        holder.btnBook.setOnClickListener(v -> listener.onBookClick(sd));
    }

    @Override
    public int getItemCount() {
        return showtimes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovieTitle, tvTheaterName, tvDateTime, tvPrice;
        Button btnBook;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.tvShowtimeMovie);
            tvTheaterName = itemView.findViewById(R.id.tvShowtimeTheater);
            tvDateTime = itemView.findViewById(R.id.tvShowtimeDateTime);
            tvPrice = itemView.findViewById(R.id.tvShowtimePrice);
            btnBook = itemView.findViewById(R.id.btnBookTicket);
        }
    }
}
