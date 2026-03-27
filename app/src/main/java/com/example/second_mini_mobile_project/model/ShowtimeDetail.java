package com.example.second_mini_mobile_project.model;

/**
 * POJO kết hợp Showtime + Movie + Theater (dùng cho JOIN query trong Room)
 */
public class ShowtimeDetail {
    public int showtimeId;
    public int movieId;
    public int theaterId;
    public String showDate;
    public String showTime;
    public double price;
    public String movieTitle;
    public String genre;
    public int duration;
    public String theaterName;
    public String theaterAddress;
}
