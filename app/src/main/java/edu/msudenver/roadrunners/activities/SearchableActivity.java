package edu.msudenver.roadrunners.activities;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import java.util.List;

import edu.msudenver.roadrunners.R;
import edu.msudenver.roadrunners.inventory.InventoryItem;
import edu.msudenver.roadrunners.inventory.InventoryItemAdapter;
import edu.msudenver.roadrunners.inventory.InventoryViewModel;

public class SearchableActivity extends AppCompatActivity {
    private InventoryItemAdapter adapter;
    private RecyclerView recyclerView;
    private InventoryViewModel viewModel;
    private SearchView searchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        searchView = findViewById(R.id.searchViewWidget);
        viewModel = ViewModelProviders.of(this).get(InventoryViewModel.class);

        adapter = new InventoryItemAdapter();
        viewModel.getItems().observe(this, new Observer<List<InventoryItem>>() {
            @Override
            public void onChanged(@Nullable List<InventoryItem> inventoryItems) {
                adapter.setItemsList(inventoryItems);
            }
        });

        recyclerView = findViewById(R.id.rvSearchList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                doMySearch(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    private void doMySearch(String query) {
        Toast.makeText(this, "Will search for item: " + query, Toast.LENGTH_LONG).show();
    }
}
