package com.example.mad_expense_tracker;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mad_expense_tracker.fragments.Dashboard;
import com.example.mad_expense_tracker.fragments.SearchFragment;

import com.example.mad_expense_tracker.fragments.profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);

    }

    Dashboard dashboardFragment = new Dashboard();
    SearchFragment expenseFragment = new SearchFragment();
    profile profileFragement =new profile();

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.profile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, profileFragement).commit();
            return true;
        } else if (item.getItemId() == R.id.dashboard) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, dashboardFragment).commit();
            return true;
        } else if (item.getItemId() == R.id.expense) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, expenseFragment).commit();
            return true;
        }
        return false; // Default case (no matching item ID)
    }

    }

