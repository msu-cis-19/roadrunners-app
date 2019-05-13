package edu.msudenver.roadrunners;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import edu.msudenver.roadrunners.activities.InventoryEditActivity;
import edu.msudenver.roadrunners.activities.InventoryListActivity;
import edu.msudenver.roadrunners.inventory.InventoryViewModel;

/*Roadrunners Inventory Final Stage
* Team Roadrunners - CIS 4160
* Zachery Brady
* Dillon Bohlander
* Morbia Neal
*
* */



public class MainActivity extends AppCompatActivity {
    private static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

    static {
        currencyFormatter.setMaximumFractionDigits(2);
        currencyFormatter.setMinimumFractionDigits(2);
    }

    private InventoryViewModel viewModel;
    private Button btnAddItem;
    private Button btnInvList;
    private TextView assetCount;
    private TextView assetValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(InventoryViewModel.class);

        btnAddItem = findViewById(R.id.btnAddItem);
        btnInvList = findViewById(R.id.btnInvList);
        assetCount = findViewById(R.id.txtAssetCount);
        assetValue = findViewById(R.id.txtAssetValue);

        viewModel.getItemCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer value) {
                if (value != null) {
                    assetCount.setText("Item Count: " + String.valueOf(value));
                }

            }
        });

        viewModel.getInventoryValue().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double value) {
                System.out.println("Inventory value changed");
                if (value != null) {
                    assetValue.setText("Total Value: " + currencyFormatter.format(value));
                } else {
                    System.out.println("Value is null");
                    assetValue.setText("Total Value: $0.00");
                }
            }
        });

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        "Take the user to create item screen", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, InventoryEditActivity.class));

            }
        });

        btnInvList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InventoryListActivity.class);
                startActivity(intent);
            }
        });
    }
}
