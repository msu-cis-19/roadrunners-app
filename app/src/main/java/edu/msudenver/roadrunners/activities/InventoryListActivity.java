package edu.msudenver.roadrunners.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import edu.msudenver.roadrunners.R;
import edu.msudenver.roadrunners.inventory.InventoryItem;
import edu.msudenver.roadrunners.inventory.InventoryItemAdapter;
import edu.msudenver.roadrunners.inventory.InventoryViewModel;

public class InventoryListActivity extends AppCompatActivity {
    private InventoryItemAdapter adapter;
    private RecyclerView recyclerView;
    private InventoryViewModel viewModel;
    private FloatingActionButton actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_list);

        viewModel = ViewModelProviders.of(this).get(InventoryViewModel.class);
        recyclerView = findViewById(R.id.rvInventoryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        actionButton = findViewById(R.id.fabAddItem);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InventoryListActivity.this, InventoryEditActivity.class));
            }
        });

        adapter = new InventoryItemAdapter();
        recyclerView.setAdapter(adapter);

        viewModel.getItems().observe(this, new Observer<List<InventoryItem>>() {
            @Override
            public void onChanged(@Nullable List<InventoryItem> inventoryItems) {
                adapter.setItemsList(inventoryItems);
            }
        });
    }
}
