package com.example.second_mini_mobile_project.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.appth.data.dao.MovieDao;
import com.example.appth.data.dao.ShowtimeDao;
import com.example.appth.data.dao.TheaterDao;
import com.example.appth.data.dao.TicketDao;
import com.example.appth.data.dao.UserDao;
import com.example.appth.data.entity.Movie;
import com.example.appth.data.entity.Showtime;
import com.example.appth.data.entity.Theater;
import com.example.appth.data.entity.Ticket;
import com.example.appth.data.entity.User;

import java.util.concurrent.Executors;

@Database(
    entities = {User.class, Movie.class, Theater.class, Showtime.class, Ticket.class},
    version = 1,
    exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract MovieDao movieDao();
    public abstract TheaterDao theaterDao();
    public abstract ShowtimeDao showtimeDao();
    public abstract TicketDao ticketDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "movie_ticket_db"
                    ).addCallback(dbCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback dbCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executors.newSingleThreadExecutor().execute(() -> seedDatabase(INSTANCE));
        }
    };

    private static void seedDatabase(AppDatabase db) {
        // ---- Users ----
        User admin = new User();
        admin.username = "admin";
        admin.password = "admin";
        admin.fullName = "Quản trị viên";
        admin.email = "admin@cinema.com";

        User user1 = new User();
        user1.username = "user1";
        user1.password = "123456";
        user1.fullName = "Nguyễn Văn A";
        user1.email = "usera@email.com";

        db.userDao().insert(admin);
        db.userDao().insert(user1);

        // ---- Movies ----
        Movie m1 = new Movie();
        m1.title = "Avengers: Secret Wars";
        m1.genre = "Hành động";
        m1.description = "Các siêu anh hùng hợp sức chống lại mối đe dọa đa vũ trụ nguy hiểm nhất từ trước đến nay.";
        m1.duration = 150;
        m1.releaseDate = "2026-03-15";

        Movie m2 = new Movie();
        m2.title = "The Lion King 2026";
        m2.genre = "Hoạt hình";
        m2.description = "Hành trình trưởng thành đầy cảm xúc của Simba trong thế giới hoang dã.";
        m2.duration = 120;
        m2.releaseDate = "2026-02-10";

        Movie m3 = new Movie();
        m3.title = "Inception Reborn";
        m3.genre = "Khoa học viễn tưởng";
        m3.description = "Cuộc hành trình vào sâu thẳm tâm trí con người để giải mã bí ẩn lớn nhất.";
        m3.duration = 148;
        m3.releaseDate = "2026-01-20";

        Movie m4 = new Movie();
        m4.title = "Fast & Furious 12";
        m4.genre = "Hành động";
        m4.description = "Dom Toretto và gia đình đối mặt với kẻ thù mới nguy hiểm và bí ẩn nhất.";
        m4.duration = 135;
        m4.releaseDate = "2026-03-01";

        Movie m5 = new Movie();
        m5.title = "Moana 3";
        m5.genre = "Hoạt hình";
        m5.description = "Moana tiếp tục hành trình khám phá những vùng biển huyền bí mới cùng Maui.";
        m5.duration = 110;
        m5.releaseDate = "2026-02-28";

        db.movieDao().insertAll(m1, m2, m3, m4, m5);

        // ---- Theaters ----
        Theater t1 = new Theater();
        t1.name = "CGV Vincom Center";
        t1.address = "72 Lê Thánh Tôn, Bến Nghé, Q.1, TP.HCM";
        t1.totalSeats = 200;

        Theater t2 = new Theater();
        t2.name = "Lotte Cinema Gò Vấp";
        t2.address = "242 Nguyễn Văn Nghi, P.7, Gò Vấp, TP.HCM";
        t2.totalSeats = 180;

        Theater t3 = new Theater();
        t3.name = "BHD Star Bitexco";
        t3.address = "2 Hải Triều, Bến Nghé, Q.1, TP.HCM";
        t3.totalSeats = 160;

        db.theaterDao().insertAll(t1, t2, t3);

        // ---- Showtimes (movieId và theaterId theo thứ tự insert = 1,2,3...) ----
        Showtime s1 = new Showtime(); s1.movieId = 1; s1.theaterId = 1; s1.showDate = "2026-04-01"; s1.showTime = "09:00"; s1.price = 120000;
        Showtime s2 = new Showtime(); s2.movieId = 1; s2.theaterId = 2; s2.showDate = "2026-04-01"; s2.showTime = "14:30"; s2.price = 110000;
        Showtime s3 = new Showtime(); s3.movieId = 2; s3.theaterId = 1; s3.showDate = "2026-04-01"; s3.showTime = "11:00"; s3.price = 100000;
        Showtime s4 = new Showtime(); s4.movieId = 2; s4.theaterId = 3; s4.showDate = "2026-04-02"; s4.showTime = "16:00"; s4.price = 100000;
        Showtime s5 = new Showtime(); s5.movieId = 3; s5.theaterId = 2; s5.showDate = "2026-04-02"; s5.showTime = "19:00"; s5.price = 120000;
        Showtime s6 = new Showtime(); s6.movieId = 3; s6.theaterId = 3; s6.showDate = "2026-04-03"; s6.showTime = "10:00"; s6.price = 115000;
        Showtime s7 = new Showtime(); s7.movieId = 4; s7.theaterId = 1; s7.showDate = "2026-04-03"; s7.showTime = "20:00"; s7.price = 130000;
        Showtime s8 = new Showtime(); s8.movieId = 4; s8.theaterId = 2; s8.showDate = "2026-04-04"; s8.showTime = "15:00"; s8.price = 125000;
        Showtime s9 = new Showtime(); s9.movieId = 5; s9.theaterId = 3; s9.showDate = "2026-04-04"; s9.showTime = "11:00"; s9.price = 95000;
        Showtime s10 = new Showtime(); s10.movieId = 5; s10.theaterId = 1; s10.showDate = "2026-04-05"; s10.showTime = "13:30"; s10.price = 95000;

        db.showtimeDao().insertAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10);
    }
}
