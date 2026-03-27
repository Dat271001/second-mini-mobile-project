package com.example.second_mini_mobile_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appth.R;
import com.example.appth.data.entity.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private final List<Movie> movies;

    public MovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.tvTitle.setText(movie.title);
        holder.tvGenreDuration.setText(movie.genre + "  •  " + movie.duration + " phút");
        holder.tvDescription.setText(movie.description);
        holder.tvReleaseDate.setText("Khởi chiếu: " + movie.releaseDate);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvGenreDuration, tvDescription, tvReleaseDate;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvMovieTitle);
            tvGenreDuration = itemView.findViewById(R.id.tvGenreDuration);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate);
        }
    }
}
