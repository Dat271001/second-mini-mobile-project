package com.example.appth;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appth.data.AppDatabase;
import com.example.appth.data.entity.Ticket;
import com.example.appth.model.ShowtimeDetail;
import com.example.appth.utils.SessionManager;

import java.text.NumberFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookTicketActivity extends AppCompatActivity {

    public static final String EXTRA_SHOWTIME_ID = "showtimeId";

    private int showtimeId;
    private ShowtimeDetail showtimeDetail;
    private Set<String> bookedSeats = new HashSet<>();
    private String selectedSeat = null;
    private Button lastSelectedButton = null;

    private TextView tvMovieTitle, tvTheaterName, tvDateTime, tvPrice, tvSelectedSeat;
    private GridLayout seatGrid;
    private Button btnConfirm;
    private ProgressBar progressBar;

    private SessionManager sessionManager;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final ActivityResultLauncher<Intent> loginLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    loadData();
                } else {
                    finish();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Đặt vé");
        }

        sessionManager = new SessionManager(this);
        showtimeId = getIntent().getIntExtra(EXTRA_SHOWTIME_ID, -1);

        tvMovieTitle = findViewById(R.id.tvBookMovieTitle);
        tvTheaterName = findViewById(R.id.tvBookTheater);
        tvDateTime = findViewById(R.id.tvBookDateTime);
        tvPrice = findViewById(R.id.tvBookPrice);
        tvSelectedSeat = findViewById(R.id.tvSelectedSeat);
        seatGrid = findViewById(R.id.seatGrid);
        btnConfirm = findViewById(R.id.btnConfirmBooking);
        progressBar = findViewById(R.id.progressBar);

        btnConfirm.setOnClickListener(v -> confirmBooking());

        // Kiểm tra đăng nhập - nếu chưa đăng nhập thì mở màn hình login
        if (!sessionManager.isLoggedIn()) {
            loginLauncher.launch(new Intent(this, LoginActivity.class));
        } else {
            loadData();
        }
    }

    private void loadData() {
        if (showtimeId == -1) {
            Toast.makeText(this, "Lỗi: Không tìm thấy lịch chiếu.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        seatGrid.setVisibility(View.GONE);
        btnConfirm.setVisibility(View.GONE);

        AppDatabase db = AppDatabase.getInstance(this);
        executor.execute(() -> {
            showtimeDetail = db.showtimeDao().getShowtimeDetailById(showtimeId);
            List<String> booked = db.ticketDao().getBookedSeats(showtimeId);
            bookedSeats.clear();
            bookedSeats.addAll(booked);

            runOnUiThread(() -> {
                progressBar.setVisibility(View.GONE);
                if (showtimeDetail == null) {
                    Toast.makeText(this, "Lỗi: Không tìm thấy lịch chiếu.", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                displayShowtimeInfo();
                buildSeatGrid();
                seatGrid.setVisibility(View.VISIBLE);
                btnConfirm.setVisibility(View.VISIBLE);
            });
        });
    }

    private void displayShowtimeInfo() {
        tvMovieTitle.setText(showtimeDetail.movieTitle);
        tvTheaterName.setText(showtimeDetail.theaterName);
        tvDateTime.setText(showtimeDetail.showDate + "  |  " + showtimeDetail.showTime);
        String priceStr = NumberFormat.getNumberInstance(new Locale("vi", "VN")).format(showtimeDetail.price) + " VNĐ";
        tvPrice.setText("Giá vé: " + priceStr);
        tvSelectedSeat.setText("Chưa chọn ghế");
    }

    private void buildSeatGrid() {
        seatGrid.removeAllViews();
        String[] rows = {"A", "B", "C", "D"};
        int cols = 5;
        seatGrid.setColumnCount(cols);
        seatGrid.setRowCount(rows.length);

        for (int r = 0; r < rows.length; r++) {
            for (int c = 1; c <= cols; c++) {
                String seatId = rows[r] + c;
                Button btn = new Button(this);
                btn.setText(seatId);
                btn.setTextSize(11);
                btn.setPadding(4, 4, 4, 4);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.rowSpec = GridLayout.spec(r, 1f);
                params.columnSpec = GridLayout.spec(c - 1, 1f);
                params.setMargins(6, 6, 6, 6);
                params.width = 0;
                params.height = dpToPx(52);
                btn.setLayoutParams(params);

                if (bookedSeats.contains(seatId)) {
                    // Ghế đã đặt - màu xám, không thể chọn
                    btn.setBackgroundColor(Color.parseColor("#9E9E9E"));
                    btn.setTextColor(Color.WHITE);
                    btn.setEnabled(false);
                } else {
                    // Ghế còn trống - màu xanh lá
                    btn.setBackgroundColor(Color.parseColor("#4CAF50"));
                    btn.setTextColor(Color.WHITE);
                    final String seat = seatId;
                    btn.setOnClickListener(v -> selectSeat(seat, btn));
                }
                seatGrid.addView(btn);
            }
        }
    }

    private void selectSeat(String seatId, Button btn) {
        // Bỏ chọn ghế cũ
        if (lastSelectedButton != null) {
            lastSelectedButton.setBackgroundColor(Color.parseColor("#4CAF50"));
        }
        // Chọn ghế mới
        selectedSeat = seatId;
        lastSelectedButton = btn;
        btn.setBackgroundColor(Color.parseColor("#2196F3"));
        tvSelectedSeat.setText("Ghế đã chọn: " + seatId);
    }

    private void confirmBooking() {
        if (selectedSeat == null) {
            Toast.makeText(this, "Vui lòng chọn một ghế.", Toast.LENGTH_SHORT).show();
            return;
        }

        btnConfirm.setEnabled(false);
        AppDatabase db = AppDatabase.getInstance(this);
        executor.execute(() -> {
            // Kiểm tra lại ghế có bị đặt chưa (tránh race condition)
            int booked = db.ticketDao().isSeatBooked(showtimeId, selectedSeat);
            if (booked > 0) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Ghế " + selectedSeat + " vừa được đặt bởi người khác. Vui lòng chọn ghế khác.", Toast.LENGTH_LONG).show();
                    btnConfirm.setEnabled(true);
                    loadData(); // Tải lại sơ đồ ghế
                });
                return;
            }

            Ticket ticket = new Ticket();
            ticket.showtimeId = showtimeId;
            ticket.userId = sessionManager.getUserId();
            ticket.seatNumber = selectedSeat;
            ticket.bookingTime = System.currentTimeMillis();
            ticket.price = showtimeDetail.price;
            long ticketId = db.ticketDao().insert(ticket);

            runOnUiThread(() -> showSuccessDialog(ticketId));
        });
    }

    private void showSuccessDialog(long ticketId) {
        String priceStr = NumberFormat.getNumberInstance(new Locale("vi", "VN")).format(showtimeDetail.price) + " VNĐ";
        String message = "Đặt vé thành công!\n\n"
                + "Phim: " + showtimeDetail.movieTitle + "\n"
                + "Rạp: " + showtimeDetail.theaterName + "\n"
                + "Suất chiếu: " + showtimeDetail.showDate + " " + showtimeDetail.showTime + "\n"
                + "Ghế: " + selectedSeat + "\n"
                + "Giá: " + priceStr + "\n"
                + "Mã vé: #" + ticketId;

        new AlertDialog.Builder(this)
                .setTitle("Xác nhận đặt vé")
                .setMessage(message)
                .setPositiveButton("Xem vé của tôi", (dialog, which) -> {
                    Intent intent = new Intent(this, MyTicketsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Đặt thêm vé", (dialog, which) -> {
                    finish();
                })
                .setCancelable(false)
                .show();
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
