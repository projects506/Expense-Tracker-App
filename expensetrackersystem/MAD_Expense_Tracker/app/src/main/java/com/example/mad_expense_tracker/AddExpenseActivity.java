package com.example.mad_expense_tracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText etAmount, etType, etNote;
    private Button btnSave;
    private DatabaseHandlerExpense databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_add_item);

        etAmount = findViewById(R.id.et_incomeAmount);
        etType = findViewById(R.id.et_incomeType);
        etNote = findViewById(R.id.et_incomeNote);
        btnSave = findViewById(R.id.btn_save);

        databaseHandler = new DatabaseHandlerExpense(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = etAmount.getText().toString();
                String type = etType.getText().toString();
                String note = etNote.getText().toString();

                if (amount.isEmpty()) {
                    etAmount.setError("Amount is required");
                    return;
                }

                if (type.isEmpty()) {
                    etType.setError("Type is required");
                    return;
                }


                long date = System.currentTimeMillis();
                databaseHandler.addData(amount, type, note, String.valueOf(date));

                Toast.makeText(AddExpenseActivity.this, "Expense added successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}