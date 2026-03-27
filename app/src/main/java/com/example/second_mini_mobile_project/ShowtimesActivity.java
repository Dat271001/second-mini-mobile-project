package com.example.appth;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appth.adapter.ShowtimeAdapter;
import com.example.appth.data.AppDatabase;
import com.example.appth.model.ShowtimeDetail;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShowtimesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtimes);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Lịch chiếu phim");
        }

        recyclerView = findViewById(R.id.recyclerShowtimes);
        progressBar = findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadShowtimes();
    }

    private void loadShowtimes() {
        progressBar.setVisibility(View.VISIBLE);
        AppDatabase db = AppDatabase.getInstance(this);
        executor.execute(() -> {
            List<ShowtimeDetail> showtimes = db.showtimeDao().getAllShowtimeDetails();
            runOnUiThread(() -> {
                progressBar.setVisibility(View.GONE);
                ShowtimeAdapter adapter = new ShowtimeAdapter(showtimes, this::onBookClicked);
                recyclerView.setAdapter(adapter);
            });
        });
    }

    private void onBookClicked(ShowtimeDetail showtime) {
        Intent intent = new Intent(this, BookTicketActivity.class);
        intent.putExtra(BookTicketActivity.EXTRA_SHOWTIME_ID, showtime.showtimeId);
        startActivity(intent);
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
