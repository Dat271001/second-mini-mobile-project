package com.example.second_mini_mobile_project.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "tickets",
    foreignKeys = {
        @ForeignKey(
            entity = Showtime.class,
            parentColumns = "showtimeId",
            childColumns = "showtimeId",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = User.class,
            parentColumns = "userId",
            childColumns = "userId",
            onDelete = ForeignKey.CASCADE
        )
    },
    indices = {
        @Index("showtimeId"),
        @Index("userId")
    }
)
public class Ticket {
    @PrimaryKey(autoGenerate = true)
    public int ticketId;
    public int showtimeId;
    public int userId;
    public String seatNumber;
    public long bookingTime;
    public double price;
}
