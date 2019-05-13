package edu.msudenver.roadrunners.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;

import edu.msudenver.roadrunners.R;
import edu.msudenver.roadrunners.inventory.InventoryItem;
import edu.msudenver.roadrunners.inventory.InventoryViewModel;

public class InventoryDetailActivity extends AppCompatActivity {
    private static final String TAG = InventoryDetailActivity.class.getName();
    private static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

    static {
        currencyFormatter.setMinimumFractionDigits(2);
        currencyFormatter.setMaximumFractionDigits(2);
    }

    private InventoryViewModel viewModel;
    private int itemId;
    private TextView txtTitle, txtCost, txtQty, txtShelf, txtBox, txtModel, txtBrand, txtSupplier;
    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_detail);
        Toolbar toolbar = findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);

        txtTitle = findViewById(R.id.txtViewTitle);
        txtCost = findViewById(R.id.txtViewCost);
        txtQty = findViewById(R.id.txtViewQty);
        txtShelf = findViewById(R.id.txtViewShelf);
        txtBox = findViewById(R.id.txtViewBox);
        txtModel = findViewById(R.id.txtViewModel);
        txtBrand = findViewById(R.id.txtViewBrand);
        txtSupplier = findViewById(R.id.txtViewSupplier);
        imgView = findViewById(R.id.detailImgView);

        viewModel = ViewModelProviders.of(this).get(InventoryViewModel.class);

        int itemId = getIntent().getIntExtra("itemId", -1);
        Log.i(TAG, "Retrieving item details for item (id): " + itemId);

        LiveData<InventoryItem> liveItem = viewModel.getItem(itemId);
        liveItem.observe(this, new Observer<InventoryItem>() {
            @Override
            public void onChanged(@Nullable InventoryItem item) {
                if (item != null) {
                    Log.d(TAG, "Item name is: " + item.getName());
                    setItemId(item.getId());
                    txtTitle.setText(item.getName());
                    txtCost.setText(currencyFormatter.format(item.getCost()));
                    txtQty.setText(String.valueOf(item.getQuantity()));
                    txtShelf.setText(item.getShelf());
                    txtBox.setText(item.getBox());
                    txtModel.setText(item.getModel());
                    txtBrand.setText(item.getBrand());
                    txtSupplier.setText(item.getSupplier());
                    Bitmap img = item.getThumbnailBitmap();
                    if(img != null)
                        imgView.setImageBitmap(item.getThumbnailBitmap());
                } else {
                    Log.d(TAG, "Item has been deleted, finishing activity");
                    finish();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actionEditItem) {
            Intent intent = new Intent(this, InventoryEditActivity.class);
            intent.putExtra("itemId", itemId);
            Log.d(TAG, "Editing item: " + itemId);
            startActivity(intent);
        }
        return true;
    }

    private void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
