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
    private InventoryRepository repo;

    public InventoryViewModel(Application application) {
        super(application);
        repo = new InventoryRepository(application);
    }

    public InventoryRepository getRepo() {
        return repo;
    }

    public LiveData<List<InventoryItem>> getItems() {
        return repo.getItems();
    }

    public LiveData<InventoryItem> getItem(int id) {
        return repo.getItem(id);
    }


}
