package com.example.second_mini_mobile_project.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int userId;
    public String username;
    public String password;
    public String fullName;
    public String email;
}
