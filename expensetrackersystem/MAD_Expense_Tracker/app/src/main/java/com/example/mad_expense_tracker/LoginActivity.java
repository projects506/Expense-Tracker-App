package com.example.mad_expense_tracker; // Replace with your package name

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private EditText[] pinBoxes = new EditText[4];
    private Button loginButton;
    private TextView instructionText;
    private SharedPreferences sharedPreferences;
    private static final String PIN_KEY = "user_pin";
    private boolean isSettingUpPin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login); // Replace with your layout file

        pinBoxes[0] = findViewById(R.id.pin_box1);
        pinBoxes[1] = findViewById(R.id.pin_box2);
        pinBoxes[2] = findViewById(R.id.pin_box3);
        pinBoxes[3] = findViewById(R.id.pin_box4);
        loginButton = findViewById(R.id.login_button);
        instructionText = findViewById(R.id.pin_title);
        sharedPreferences = getSharedPreferences("app_data", MODE_PRIVATE);

        // Check if PIN is set for the first time
        String storedPin = sharedPreferences.getString(PIN_KEY, null);
        if (storedPin == null) {
            // First-time setup
            isSettingUpPin = true;
            instructionText.setText("Set your 4-digit PIN");
            setupInputListeners();
        } else {
            // Regular login
            instructionText.setText("Enter your PIN");
            setupInputListeners();
        }
    }

    private void setupInputListeners() {
        for (EditText pinBox : pinBoxes) {
            pinBox.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 1) {
                        pinBox.clearFocus();
                        int index = Arrays.asList(pinBoxes).indexOf(pinBox);
                        if (index < pinBoxes.length - 1) {
                            pinBoxes[index + 1].requestFocus();
                        } else {
                            if (isSettingUpPin) {
                                confirmPin();
                            } else {
                                attemptLogin();
                            }
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }
    }

    private void attemptLogin() {
        StringBuilder pinBuilder = new StringBuilder();
        for (EditText pinBox : pinBoxes) {
            pinBuilder.append(pinBox.getText().toString());
        }
        String enteredPin = pinBuilder.toString();

        String storedPin = sharedPreferences.getString(PIN_KEY, null);
        if (enteredPin.equals(storedPin)) {
            // Login successful
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Incorrect PIN
            Toast.makeText(LoginActivity.this, "Incorrect PIN", Toast.LENGTH_SHORT).show();
            clearPinBoxes();
    }}

    public void clearPinBoxes() {
            for (EditText pinBox : pinBoxes) {
                pinBox.setText("");
            }
            pinBoxes[0].requestFocus();
    }

    private void confirmPin() {
        StringBuilder pinBuilder = new StringBuilder();
        for (EditText pinBox : pinBoxes) {
            pinBuilder.append(pinBox.getText().toString());
        }
        String enteredPin = pinBuilder.toString();

        // Store the entered PIN temporarily
        sharedPreferences.edit().putString(PIN_KEY, enteredPin).apply();
        clearPinBoxes();
        instructionText.setText("Confirm your PIN");
        isSettingUpPin = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isSettingUpPin) {
            String tempPin = sharedPreferences.getString(PIN_KEY, null);
            if (tempPin != null) {
                String enteredPin = getEnteredPin();
                if (enteredPin.equals(tempPin)) {
                    // PIN confirmed, store it permanently
                    sharedPreferences.edit().putString(PIN_KEY, enteredPin).apply();
                    sharedPreferences.edit().remove("temp_pin").apply();
                    Toast.makeText(this, "PIN set successfully!", Toast.LENGTH_SHORT).show();
                    // Start MainActivity or proceed as needed
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // PIN mismatch
                    Toast.makeText(this, "PIN mismatch", Toast.LENGTH_SHORT).show();
                    clearPinBoxes();
                    sharedPreferences.edit().remove("temp_pin").apply();
                    isSettingUpPin = true;
                    instructionText.setText("Set your 4-digit PIN");
                }
            }
        }
    }

    private String getEnteredPin() {
        StringBuilder pinBuilder = new StringBuilder();
        for (EditText pinBox : pinBoxes) {
            pinBuilder.append(pinBox.getText().toString());
        }
        return pinBuilder.toString();
    }
}