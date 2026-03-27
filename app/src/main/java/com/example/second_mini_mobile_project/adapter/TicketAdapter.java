package com.example.second_mini_mobile_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appth.R;
import com.example.appth.model.TicketDetail;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    private final List<TicketDetail> tickets;

    public TicketAdapter(List<TicketDetail> tickets) {
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TicketDetail ticket = tickets.get(position);
        holder.tvMovieTitle.setText(ticket.movieTitle);
        holder.tvTheaterName.setText(ticket.theaterName);
        holder.tvDateTime.setText(ticket.showDate + "  |  " + ticket.showTime);
        holder.tvSeatNumber.setText("Ghế: " + ticket.seatNumber);
        String priceStr = NumberFormat.getNumberInstance(new Locale("vi", "VN")).format(ticket.price) + " VNĐ";
        holder.tvPrice.setText(priceStr);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        holder.tvBookingTime.setText("Đặt lúc: " + sdf.format(new Date(ticket.bookingTime)));
        holder.tvTicketId.setText("#" + ticket.ticketId);
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovieTitle, tvTheaterName, tvDateTime, tvSeatNumber, tvPrice, tvBookingTime, tvTicketId;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.tvTicketMovie);
            tvTheaterName = itemView.findViewById(R.id.tvTicketTheater);
            tvDateTime = itemView.findViewById(R.id.tvTicketDateTime);
            tvSeatNumber = itemView.findViewById(R.id.tvTicketSeat);
            tvPrice = itemView.findViewById(R.id.tvTicketPrice);
            tvBookingTime = itemView.findViewById(R.id.tvTicketBookingTime);
            tvTicketId = itemView.findViewById(R.id.tvTicketId);
        }
    }
}
