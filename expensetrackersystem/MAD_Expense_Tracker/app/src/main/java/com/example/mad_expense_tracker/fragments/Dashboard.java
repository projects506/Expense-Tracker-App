package com.example.mad_expense_tracker.fragments;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad_expense_tracker.AddExpenseActivity;
import com.example.mad_expense_tracker.CircularProgressApp;
import com.example.mad_expense_tracker.DatabaseHandlerExpense;
import com.example.mad_expense_tracker.R;
import com.example.mad_expense_tracker.adapter.expenseAdapter2;
import com.example.mad_expense_tracker.model.expenseModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Dashboard extends Fragment {

    private CircularProgressApp circularProgressBar;
    private RecyclerView expensesListRecyclerView;
    private TextView sortByLabel;
    private Button addExpenseButton;
    private expenseAdapter2 expenseAdapter;
    private List<expenseModel> expenseList;
    private DatabaseHandlerExpense databaseHandler;
    private boolean isAscendingOrder = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page, container, false);

        circularProgressBar = view.findViewById(R.id.circular_progress_bar);
        expensesListRecyclerView = view.findViewById(R.id.expenses_list);
        sortByLabel = view.findViewById(R.id.sort_by_label);
        addExpenseButton = view.findViewById(R.id.add_expense_button);

        databaseHandler = new DatabaseHandlerExpense(getContext());
        expenseList = fetchExpensesFromDatabase();
        expenseAdapter = new expenseAdapter2(getContext(), expenseList, databaseHandler);

        expensesListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        expensesListRecyclerView.setAdapter(expenseAdapter);

        sortByLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortExpenses();            }
        });

        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddExpenseActivity.class);
                startActivity(intent);
            }
        });

        updateCircularProgressBar();

        return view;
    }

    private List<expenseModel> fetchExpensesFromDatabase() {
        // Fetch expenses from the database using databaseHandler
        return databaseHandler.getAllIncome();
    }

    private void updateCircularProgressBar() {
        // Calculate progress value based on expenses data
        int progressValue = calculateProgressValue();
        circularProgressBar.setProgress(progressValue);
    }

    private int calculateProgressValue() {
        // Implement logic to calculate progress value based on expenses data
        return 0; // Replace with your actual logic
    }
    private void sortExpenses() {
        if (isAscendingOrder) {
            // Sort expenses in ascending order by amount
            Collections.sort(expenseList, new Comparator<expenseModel>() {
                @Override
                public int compare(expenseModel model1, expenseModel model2) {
                    return Double.compare(Double.parseDouble(model1.getAmount()), Double.parseDouble(model2.getAmount()));
                }
            });
        } else {
            // Sort expenses in descending order by amount
            Collections.sort(expenseList, new Comparator<expenseModel>() {
                @Override
                public int compare(expenseModel model1, expenseModel model2) {
                    return Double.compare(Double.parseDouble(model2.getAmount()), Double.parseDouble(model1.getAmount()));
                }
            });
        }

        isAscendingOrder = !isAscendingOrder; // Toggle sorting order
        expenseAdapter.notifyDataSetChanged();
    }
}
