package edu.msudenver.roadrunners.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import edu.msudenver.roadrunners.R;
import edu.msudenver.roadrunners.inventory.InventoryItem;
import edu.msudenver.roadrunners.inventory.InventoryViewModel;

public class InventoryEditActivity extends AppCompatActivity {
    private static final String TAG = InventoryEditActivity.class.getName();
    private static final int REQUEST_SNAPSHOT = 1;

    private InventoryViewModel viewModel;
    private int itemId;
    private Bitmap imgBitmap;
    private ImageView imgDetailItem;
    private EditText txtEditName, txtEditCost, txtEditQty, txtEditShelf, txtEditBox, txtEditModel,
            txtEditBrand, txtEditSupplier;
    private Button btnDelete;

    private View.OnClickListener deleteItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(InventoryEditActivity.this)
                    .setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                            viewModel.getItem(itemId).observe(InventoryEditActivity.this, new Observer<InventoryItem>() {
                                @Override
                                public void onChanged(@Nullable InventoryItem inventoryItem) {
                                    if (inventoryItem != null) {
                                        dialog.dismiss();
                                        viewModel.deleteItem(inventoryItem);
                                        finish();
                                    }
                                }
                            });
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        itemId = getIntent().getIntExtra("itemId", -1);
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

        btnDelete = findViewById(R.id.btnDeleteItem);
        if (itemId != -1) {
            btnDelete.setOnClickListener(deleteItemListener);
        } else {
            btnDelete.setVisibility(View.INVISIBLE);
        }

        Log.d(TAG, "Editing item: " + itemId);
        LiveData<InventoryItem> item = viewModel.getItem(itemId);
        item.observe(this, new Observer<InventoryItem>() {
            @Override
            public void onChanged(@Nullable InventoryItem inventoryItem) {
                if (inventoryItem != null) {
                    txtEditName.setText(inventoryItem.getName());
                    txtEditCost.setText(String.valueOf(inventoryItem.getCost()));
                    txtEditQty.setText(String.valueOf(inventoryItem.getQuantity()));
                    txtEditShelf.setText(inventoryItem.getShelf());
                    txtEditBox.setText(inventoryItem.getBox());
                    txtEditModel.setText(inventoryItem.getModel());
                    txtEditBrand.setText(inventoryItem.getBrand());
                    txtEditSupplier.setText(inventoryItem.getSupplier());
                    imgBitmap = inventoryItem.getThumbnailBitmap();
                    imgDetailItem.setImageBitmap(imgBitmap);
                } else {
                    Log.d(TAG, "Inventory item is null");
                }
            }
        });

        imgDetailItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_SNAPSHOT);
                }
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
        if (item.getItemId() == R.id.actionSaveItem) {
            saveItem();
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void saveItem() {
        if (txtEditName.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.warnNameRequired), Toast.LENGTH_LONG).show();
            return;
        }

        if (itemId == -1) {
            Log.d(TAG, "Inserting new item");
            viewModel.addItem(extractItem());
        } else {
            Log.d(TAG, "Updating existing item");
            viewModel.updateItem(extractItem());
        }
    }

    private InventoryItem extractItem() {
        InventoryItem item = new InventoryItem();
        item.setName(txtEditName.getText().toString());
        item.setShelf(txtEditShelf.getText().toString());
        item.setModel(txtEditModel.getText().toString());
        item.setBrand(txtEditBrand.getText().toString());
        item.setSupplier(txtEditSupplier.getText().toString());
        item.setBox(txtEditBox.getText().toString());
        item.setThumbnailBitmap(imgBitmap);

        if (txtEditQty.getText().length() > 0) {
            item.setQuantity(Integer.parseInt(txtEditQty.getText().toString()));
        }
        if (txtEditCost.getText().length() > 0) {
            item.setCost(Double.parseDouble(txtEditCost.getText().toString()));
        }

        if (itemId != -1) {
            item.setId(itemId);
        }

        return item;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SNAPSHOT && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap) extras.get("data");
            if (image != null) {
                imgBitmap = image;
                imgDetailItem.setImageBitmap(image);
            } else {
                System.out.println("Image data was null");
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imgBitmap = null;
    }
}
