package com.example.second_mini_mobile_project.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "showtimes",
    foreignKeys = {
        @ForeignKey(
            entity = Movie.class,
            parentColumns = "movieId",
            childColumns = "movieId",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = Theater.class,
            parentColumns = "theaterId",
            childColumns = "theaterId",
            onDelete = ForeignKey.CASCADE
        )
    },
    indices = {
        @Index("movieId"),
        @Index("theaterId")
    }
)
public class Showtime {
    @PrimaryKey(autoGenerate = true)
    public int showtimeId;
    public int movieId;
    public int theaterId;
    public String showDate;  // "2026-04-01"
    public String showTime;  // "14:30"
    public double price;
}
