package com.example.second_mini_mobile_project.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "theaters")
public class Theater {
    @PrimaryKey(autoGenerate = true)
    public int theaterId;
    public String name;
    public String address;
    public int totalSeats;
}
