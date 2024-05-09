package com.example.mad_expense_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {
    private static final int SPLASH_DURATION = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity (with bottom navigation)
                Intent intent = new Intent(Splash.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Finish the splash activity
            }
        }, SPLASH_DURATION);
    }
}