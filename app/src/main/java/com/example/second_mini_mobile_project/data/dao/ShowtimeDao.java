package com.example.second_mini_mobile_project.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.appth.data.entity.Showtime;
import com.example.appth.model.ShowtimeDetail;

import java.util.List;

@Dao
public interface ShowtimeDao {
    @Insert
    void insertAll(Showtime... showtimes);

    @Query("SELECT COUNT(*) FROM showtimes")
    int getShowtimeCount();

    @Query("SELECT s.showtimeId, s.movieId, s.theaterId, s.showDate, s.showTime, s.price, " +
           "m.title AS movieTitle, m.genre, m.duration, " +
           "t.name AS theaterName, t.address AS theaterAddress " +
           "FROM showtimes s " +
           "INNER JOIN movies m ON s.movieId = m.movieId " +
           "INNER JOIN theaters t ON s.theaterId = t.theaterId " +
           "ORDER BY s.showDate, s.showTime")
    List<ShowtimeDetail> getAllShowtimeDetails();

    @Query("SELECT s.showtimeId, s.movieId, s.theaterId, s.showDate, s.showTime, s.price, " +
           "m.title AS movieTitle, m.genre, m.duration, " +
           "t.name AS theaterName, t.address AS theaterAddress " +
           "FROM showtimes s " +
           "INNER JOIN movies m ON s.movieId = m.movieId " +
           "INNER JOIN theaters t ON s.theaterId = t.theaterId " +
           "WHERE s.showtimeId = :showtimeId")
    ShowtimeDetail getShowtimeDetailById(int showtimeId);
}
