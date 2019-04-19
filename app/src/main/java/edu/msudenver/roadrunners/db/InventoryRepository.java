package edu.msudenver.roadrunners.db;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import edu.msudenver.roadrunners.inventory.InventoryItem;

public class InventoryRepository {
    private InventoryItemDAO itemDAO;
    private LiveData<List<InventoryItem>> items;

    public InventoryRepository(Application application) {
        InventoryDB db = InventoryDB.getInstance(application);
        itemDAO = db.itemDAO();
        items = itemDAO.loadAllItems();
    }

    public LiveData<List<InventoryItem>> getItems() {
        return items;
    }

    public LiveData<InventoryItem> getItem(int id) {
        return itemDAO.getItem(id);
    }

    public void asyncInsertItems(InventoryItem... items) {
        InsertAsync task = new InsertAsync(itemDAO);
        task.execute(items);
    }

    public void asyncUpdateItems(InventoryItem... items) {
        UpdateAsync task = new UpdateAsync(itemDAO);
        task.execute(items);
    }

    public void asyncDeleteItems(InventoryItem... items) {
        DeleteAsync task = new DeleteAsync(itemDAO);
        task.execute(items);
    }

    private static class UpdateAsync extends AsyncTask<InventoryItem, Void, Void> {

        private final InventoryItemDAO dao;

        UpdateAsync(InventoryItemDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(InventoryItem... inventoryItems) {

            for (InventoryItem item : inventoryItems) {
                dao.updateItem(item);
            }
            return null;
        }
    }

    private static class InsertAsync extends AsyncTask<InventoryItem, Void, Void> {
        private final InventoryItemDAO dao;

        InsertAsync(InventoryItemDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(InventoryItem... items) {
            dao.insertItems(items);

            return null;
        }
    }

    private static class DeleteAsync extends AsyncTask<InventoryItem, Void, Void> {
        private final InventoryItemDAO dao;

        DeleteAsync(InventoryItemDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(InventoryItem... inventoryItems) {
            for (InventoryItem item : inventoryItems) {
                if (item != null) {
                    Log.d("IR", "deleting inventory item: " + item.getName());
                    dao.deleteItem(item);
                }
            }
            return null;
        }
    }

}
