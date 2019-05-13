package edu.msudenver.roadrunners.inventory;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import edu.msudenver.roadrunners.db.InventoryRepository;


/*
 * The InventoryViewModel maintains the data and state for the Inventory (Activity) Views to query
 * */

public class InventoryViewModel extends AndroidViewModel {
    private final InventoryRepository repository;

    public InventoryViewModel(Application application) {
        super(application);
        repository = new InventoryRepository(application);

    }

    public LiveData<List<InventoryItem>> getItems() {
        return repository.getItems();
    }

    public LiveData<InventoryItem> getItem(int id) {
        return repository.getItem(id);
    }

    public LiveData<Integer> getItemCount() {
        return repository.getItemCount();
    }

    public LiveData<Double> getInventoryValue() {
        return repository.getInventoryValue();
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

}
