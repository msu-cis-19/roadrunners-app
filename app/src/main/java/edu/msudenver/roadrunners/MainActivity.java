package edu.msudenver.roadrunners;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import edu.msudenver.roadrunners.activities.InventoryListActivity;
import edu.msudenver.roadrunners.activities.SearchableActivity;

/*Roadrunners Inventory Stage-3 Development*/
public class MainActivity extends AppCompatActivity {

    private Button btnAddItem;
    private Button btnSearch;
    private Button btnInvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddItem = findViewById(R.id.btnAddItem);
        btnSearch = findViewById(R.id.btnSearch);
        btnInvList = findViewById(R.id.btnInvList);

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        "Take the user to create item screen", Toast.LENGTH_SHORT).show();

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        "Pop up a search dialog", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SearchableActivity.class);
                startActivity(intent);
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
