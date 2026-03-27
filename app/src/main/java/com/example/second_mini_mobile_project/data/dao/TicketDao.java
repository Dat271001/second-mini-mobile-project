package com.example.second_mini_mobile_project.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.appth.data.entity.Ticket;
import com.example.appth.model.TicketDetail;

import java.util.List;

@Dao
public interface TicketDao {
    @Insert
    long insert(Ticket ticket);

    @Query("SELECT seatNumber FROM tickets WHERE showtimeId = :showtimeId")
    List<String> getBookedSeats(int showtimeId);

    @Query("SELECT COUNT(*) FROM tickets WHERE showtimeId = :showtimeId AND seatNumber = :seatNumber")
    int isSeatBooked(int showtimeId, String seatNumber);

    @Query("SELECT tk.ticketId, tk.seatNumber, tk.bookingTime, tk.price, tk.showtimeId, " +
           "s.showDate, s.showTime, " +
           "m.title AS movieTitle, " +
           "t.name AS theaterName " +
           "FROM tickets tk " +
           "INNER JOIN showtimes s ON tk.showtimeId = s.showtimeId " +
           "INNER JOIN movies m ON s.movieId = m.movieId " +
           "INNER JOIN theaters t ON s.theaterId = t.theaterId " +
           "WHERE tk.userId = :userId " +
           "ORDER BY tk.bookingTime DESC")
    List<TicketDetail> getTicketDetailsByUser(int userId);
}
