package edu.msudenver.roadrunners.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import edu.msudenver.roadrunners.inventory.InventoryItem;

@Database(entities = {InventoryItem.class}, version = 1)
public abstract class InventoryDB extends RoomDatabase {

    private static final String DBNAME = "item_db";
    private static volatile InventoryDB INSTANCE;

    public static InventoryDB getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (InventoryDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            InventoryDB.class, DBNAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract InventoryItemDAO itemDAO();
}
