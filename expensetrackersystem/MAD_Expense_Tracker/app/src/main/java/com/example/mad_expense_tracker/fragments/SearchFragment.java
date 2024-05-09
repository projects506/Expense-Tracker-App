package com.example.mad_expense_tracker.fragments;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad_expense_tracker.DatabaseHandlerExpense;
import com.example.mad_expense_tracker.R;
import com.example.mad_expense_tracker.adapter.expenseAdapter;
import com.example.mad_expense_tracker.model.expenseModel;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private EditText searchEditText;
    private RecyclerView searchResultsRecyclerView;
    private TextView noResultsTextView;
    private expenseAdapter expenseAdapter;
    private List<expenseModel> expenseList;
    private DatabaseHandlerExpense databaseHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchEditText = view.findViewById(R.id.search_edittext);
        searchResultsRecyclerView = view.findViewById(R.id.search_results_recyclerview);
        noResultsTextView = view.findViewById(R.id.no_results_textview);

        databaseHandler = new DatabaseHandlerExpense(getContext());
        expenseList = new ArrayList<>();
        expenseAdapter = new expenseAdapter(getContext(), expenseList);

        searchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchResultsRecyclerView.setAdapter(expenseAdapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchExpenses(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void searchExpenses(String query) {
        expenseList.clear();
        List<expenseModel> searchResults = databaseHandler.searchExpenses(query);
        expenseList.addAll(searchResults);
        expenseAdapter.notifyDataSetChanged();

        if (searchResults.isEmpty()) {
            noResultsTextView.setVisibility(View.VISIBLE);
        } else {
            noResultsTextView.setVisibility(View.GONE);
        }
    }
}