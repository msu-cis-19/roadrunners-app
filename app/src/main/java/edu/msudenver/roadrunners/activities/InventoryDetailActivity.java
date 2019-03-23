package edu.msudenver.roadrunners.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.NumberFormat;

import edu.msudenver.roadrunners.R;
import edu.msudenver.roadrunners.inventory.InventoryItem;
import edu.msudenver.roadrunners.inventory.InventoryViewModel;

public class InventoryDetailActivity extends AppCompatActivity {
    private InventoryViewModel viewModel;
    private int itemId;
    private TextView txtTitle, txtCost, txtQty, txtShelf, txtBox, txtModel, txtBrand, txtSupplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_detail);
        Toolbar toolbar = findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);

        final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        currencyFormatter.setMinimumFractionDigits(1);
        currencyFormatter.setMaximumFractionDigits(2);

        txtTitle = findViewById(R.id.txtViewTitle);
        txtCost = findViewById(R.id.txtViewCost);
        txtQty = findViewById(R.id.txtViewQty);
        txtShelf = findViewById(R.id.txtViewShelf);
        txtBox = findViewById(R.id.txtViewBox);
        txtModel = findViewById(R.id.txtViewModel);
        txtBrand = findViewById(R.id.txtViewBrand);
        txtSupplier = findViewById(R.id.txtViewSupplier);

        viewModel = ViewModelProviders.of(this).get(InventoryViewModel.class);

        int itemId = getIntent().getIntExtra("itemId", -1);
        System.out.println("Getting item details for item id: " + itemId);

        LiveData<InventoryItem> liveItem = viewModel.getItem(itemId);
        liveItem.observe(this, new Observer<InventoryItem>() {
            @Override
            public void onChanged(@Nullable InventoryItem item) {
                if (item != null) {
                    System.out.println("Item name is: " + item.getName());
                    setItemId(item.getId());
                    txtTitle.setText(item.getName());
                    txtCost.setText(currencyFormatter.format(item.getCost()));
                    txtQty.setText(String.valueOf(item.getQuantity()));
                    txtShelf.setText(item.getShelf());
                    txtBox.setText(item.getBox());
                    txtModel.setText(item.getModel());
                    txtBrand.setText(item.getBrand());
                    txtSupplier.setText(item.getSupplier());
                }
            }
        });
    }

    private void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.actionEditItem:
                Intent intent = new Intent(this, InventoryEditActivity.class);
                intent.putExtra("itemId", itemId);
                System.out.println("Editing item");
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
