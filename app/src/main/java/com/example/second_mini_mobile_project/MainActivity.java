package com.example.second_mini_mobile_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.second_mini_mobile_project.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private TextView tvWelcome;
    private Button btnLogin, btnLogout;
    private View btnMyTickets;
    private LinearLayout layoutLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogout = findViewById(R.id.btnLogout);
        btnMyTickets = findViewById(R.id.btnMyTickets);
        layoutLoggedIn = findViewById(R.id.layoutLoggedIn);

        findViewById(R.id.btnMovies).setOnClickListener(v ->
                startActivity(new Intent(this, MoviesActivity.class)));

        findViewById(R.id.btnTheaters).setOnClickListener(v ->
                startActivity(new Intent(this, TheatersActivity.class)));

        findViewById(R.id.btnShowtimes).setOnClickListener(v ->
                startActivity(new Intent(this, ShowtimesActivity.class)));

        btnMyTickets.setOnClickListener(v ->
                startActivity(new Intent(this, MyTicketsActivity.class)));

        btnLogin.setOnClickListener(v ->
                startActivity(new Intent(this, LoginActivity.class)));

        btnLogout.setOnClickListener(v -> {
            sessionManager.logout();
            updateLoginUI();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLoginUI();
    }

    private void updateLoginUI() {
        if (sessionManager.isLoggedIn()) {
            tvWelcome.setText("Xin chào, " + sessionManager.getFullName() + "!");
            tvWelcome.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.GONE);
            layoutLoggedIn.setVisibility(View.VISIBLE);
        } else {
            tvWelcome.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
            layoutLoggedIn.setVisibility(View.GONE);
        }
    }
}
