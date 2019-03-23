package edu.msudenver.roadrunners.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Random;

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
                InventoryItem item = new InventoryItem();
                item.setName("My new Item" + new Random().nextInt(100));
                item.setQuantity(3);
                item.setBrand("DeWalt");
                item.setCost(23.99);
                viewModel.getRepo().asyncInsertItems(item);

            }
        });

//        InventoryItem test1 = new InventoryItem();
//        test1.setName("Test Item");
//        test1.setId(new Random().nextInt());
//
//        InventoryItem test2 = new InventoryItem();
//        test2.setName("Test Item 2");
//        test2.setId(new Random().nextInt());
//
//        viewModel.getRepo().asyncInsertItems(test1);

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
