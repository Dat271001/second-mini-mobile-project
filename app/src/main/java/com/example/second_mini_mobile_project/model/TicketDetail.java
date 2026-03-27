package com.example.second_mini_mobile_project.model;

/**
 * POJO kết hợp Ticket + Showtime + Movie + Theater (dùng cho JOIN query trong Room)
 */
public class TicketDetail {
    public int ticketId;
    public int showtimeId;
    public String seatNumber;
    public long bookingTime;
    public double price;
    public String showDate;
    public String showTime;
    public String movieTitle;
    public String theaterName;
}
