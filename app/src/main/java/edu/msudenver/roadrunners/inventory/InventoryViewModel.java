package edu.msudenver.roadrunners.inventory;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import edu.msudenver.roadrunners.db.InventoryRepository;


/*
 * The InventoryViewModel maintains the data and state for the Inventory Views to query
 * */

public class InventoryViewModel extends AndroidViewModel {
    private final InventoryRepository repository;
//    private static final List<InventoryItem> items = new ArrayList<>();

//    static {
//        InventoryItem item1 = new InventoryItem(1, "20V Hammer Drill", 99.99, 5, "A6", "Box C", "DCD771C2", "DeWalt", "Home Depot");
//        InventoryItem item2 = new InventoryItem(2, "Aviation Snip Set", 23.95, 13, "B4", "Box D", "M123R", "Wiss", "Home Depot");
//
//        items.add(item1);
//        items.add(item2);
//    }

    public InventoryViewModel(Application application) {
        super(application);
        repository = new InventoryRepository(application);

    }

    public LiveData<List<InventoryItem>> getItems(){
        return repository.getItems();
    }

    public LiveData<InventoryItem> getItem(int id) {
        return repository.getItem(id);
    }

    public void addItem(InventoryItem item) {
        repository.asyncInsertItems(item);
    }

    public void updateItem(InventoryItem item) {
        repository.asyncUpdateItems(item);
    }

    public void deleteItem(InventoryItem item) {
        repository.asyncDeleteItems(item);
    }

    public LiveData<Integer> getItemCount() {
        return repository.getItemCount();
    }

    public LiveData<Double> getInventoryValue() {
        return repository.getInventoryValue();
    }

}
