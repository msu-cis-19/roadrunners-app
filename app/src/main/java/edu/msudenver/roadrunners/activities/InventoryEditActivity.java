package edu.msudenver.roadrunners.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import edu.msudenver.roadrunners.R;
import edu.msudenver.roadrunners.inventory.InventoryItem;
import edu.msudenver.roadrunners.inventory.InventoryViewModel;

public class InventoryEditActivity extends AppCompatActivity {
    private InventoryViewModel viewModel;
    private ImageView imgDetailItem;
    private EditText txtEditName, txtEditCost, txtEditQty, txtEditShelf, txtEditBox, txtEditModel,
            txtEditBrand, txtEditSupplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewModel = ViewModelProviders.of(this).get(InventoryViewModel.class);

        imgDetailItem = findViewById(R.id.imgDetailItem);
        txtEditName = findViewById(R.id.txtEditName);
        txtEditCost = findViewById(R.id.txtEditCost);
        txtEditQty = findViewById(R.id.txtEditQty);
        txtEditShelf = findViewById(R.id.txtEditShelf);
        txtEditBox = findViewById(R.id.txtEditBox);
        txtEditModel = findViewById(R.id.txtEditModel);
        txtEditBrand = findViewById(R.id.txtEditBrand);
        txtEditSupplier = findViewById(R.id.txtEditSupplier);

        final int itemId = getIntent().getIntExtra("itemId", -1);
        System.out.println("Editing item: " + itemId);
        InventoryItem item = viewModel.getItem(itemId);
        if (item != null) {
            txtEditName.setText(item.getName());
            txtEditCost.setText(String.valueOf(item.getCost()));
            txtEditQty.setText(String.valueOf(item.getQuantity()));
            txtEditShelf.setText(item.getShelf());
            txtEditBox.setText(item.getBox());
            txtEditModel.setText(item.getModel());
            txtEditBrand.setText(item.getBrand());
            txtEditSupplier.setText(item.getSupplier());
        }

        imgDetailItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InventoryEditActivity.this, "Start ACTION_IMAGE_CAPTURE intent", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_create_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionSaveItem:
                saveItem();
                break;
            case R.id.actionEditDone:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void saveItem() {
        Toast.makeText(this, "Saving item to persistent storage", Toast.LENGTH_LONG).show();
    }
}
