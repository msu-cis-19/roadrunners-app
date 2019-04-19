package edu.msudenver.roadrunners.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import edu.msudenver.roadrunners.inventory.InventoryItem;

@Dao
public interface InventoryItemDAO {

    @Insert
    void insertItems(InventoryItem... items);

    @Query("SELECT * FROM items ORDER BY name ASC")
    LiveData<List<InventoryItem>> loadAllItems();

    @Query("SELECT * FROM items WHERE id = (:id)")
    LiveData<InventoryItem> getItem(int id);

    @Update
    void updateItem(InventoryItem item);

    @Delete
    void deleteItem(InventoryItem item);

}
