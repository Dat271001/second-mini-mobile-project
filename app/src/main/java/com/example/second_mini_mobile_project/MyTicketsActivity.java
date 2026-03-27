package com.example.appth;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appth.adapter.TicketAdapter;
import com.example.appth.data.AppDatabase;
import com.example.appth.model.TicketDetail;
import com.example.appth.utils.SessionManager;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyTicketsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView tvEmpty;
    private SessionManager sessionManager;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tickets);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Vé của tôi");
        }

        sessionManager = new SessionManager(this);
        recyclerView = findViewById(R.id.recyclerTickets);
        progressBar = findViewById(R.id.progressBar);
        tvEmpty = findViewById(R.id.tvEmptyTickets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadTickets();
    }

    private void loadTickets() {
        progressBar.setVisibility(View.VISIBLE);
        tvEmpty.setVisibility(View.GONE);
        AppDatabase db = AppDatabase.getInstance(this);
        int userId = sessionManager.getUserId();

        executor.execute(() -> {
            List<TicketDetail> tickets = db.ticketDao().getTicketDetailsByUser(userId);
            runOnUiThread(() -> {
                progressBar.setVisibility(View.GONE);
                if (tickets.isEmpty()) {
                    tvEmpty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    tvEmpty.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(new TicketAdapter(tickets));
                }
            });
        });
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
