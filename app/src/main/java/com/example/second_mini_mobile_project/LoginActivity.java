package com.example.appth;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appth.data.AppDatabase;
import com.example.appth.data.entity.User;
import com.example.appth.utils.SessionManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private TextView tvError;
    private SessionManager sessionManager;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tvError = findViewById(R.id.tvLoginError);
        Button btnLogin = findViewById(R.id.btnDoLogin);
        Button btnBack = findViewById(R.id.btnBack);

        btnLogin.setOnClickListener(v -> doLogin());
        btnBack.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        // Xử lý nút Back (thay thế onBackPressed() deprecated)
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private void doLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            tvError.setText("Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.");
            tvError.setVisibility(View.VISIBLE);
            return;
        }

        tvError.setVisibility(View.GONE);
        AppDatabase db = AppDatabase.getInstance(this);

        executor.execute(() -> {
            User user = db.userDao().login(username, password);
            runOnUiThread(() -> {
                if (user != null) {
                    sessionManager.createLoginSession(user.userId, user.username, user.fullName);
                    setResult(RESULT_OK);
                    finish();
                } else {
                    tvError.setText("Tên đăng nhập hoặc mật khẩu không đúng.");
                    tvError.setVisibility(View.VISIBLE);
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
