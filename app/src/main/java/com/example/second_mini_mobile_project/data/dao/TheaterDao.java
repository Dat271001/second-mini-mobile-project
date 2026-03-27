package com.example.second_mini_mobile_project.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.appth.data.entity.Theater;

import java.util.List;

@Dao
public interface TheaterDao {
    @Insert
    void insertAll(Theater... theaters);

    @Query("SELECT * FROM theaters ORDER BY name")
    List<Theater> getAllTheaters();

    @Query("SELECT COUNT(*) FROM theaters")
    int getTheaterCount();
}
