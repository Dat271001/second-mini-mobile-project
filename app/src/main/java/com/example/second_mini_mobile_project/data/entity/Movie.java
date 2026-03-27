package com.example.second_mini_mobile_project.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class Movie {
    @PrimaryKey(autoGenerate = true)
    public int movieId;
    public String title;
    public String genre;
    public String description;
    public int duration; // phút
    public String releaseDate;
}
