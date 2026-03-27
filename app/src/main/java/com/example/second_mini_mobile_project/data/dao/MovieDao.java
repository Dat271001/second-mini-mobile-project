package com.example.second_mini_mobile_project.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.appth.data.entity.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert
    void insertAll(Movie... movies);

    @Query("SELECT * FROM movies ORDER BY title")
    List<Movie> getAllMovies();

    @Query("SELECT COUNT(*) FROM movies")
    int getMovieCount();
}
