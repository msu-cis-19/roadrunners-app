package edu.msudenver.roadrunners.inventory;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;


/*
 * The InventoryViewModel maintains the data and state for the Inventory Views to query
 * */

public class InventoryViewModel extends AndroidViewModel {
    private static final List<InventoryItem> items = new ArrayList<>();

    static {
        InventoryItem item1 = new InventoryItem(1, "20V Hammer Drill", 5, "A6", "Box C", "DCD771C2", "DeWalt", "Home Depot");
        InventoryItem item2 = new InventoryItem(2, "Aviation Snip Set", 13, "B4", "Box D", "M123R", "Wiss", "Home Depot");

        items.add(item1);
        items.add(item2);
    }

    public InventoryViewModel(Application application) {
        super(application);
    }

    public List<InventoryItem> getItems() {
        return items;
    }

    public void addItem(InventoryItem item) {
        items.add(item);
    }

    public InventoryItem getItem(int id) {
        for (InventoryItem item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
