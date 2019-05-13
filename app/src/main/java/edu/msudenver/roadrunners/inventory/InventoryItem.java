package edu.msudenver.roadrunners.inventory;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

@Entity(tableName = "items")
public class InventoryItem {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private String name;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] thumbnail;

    private double cost;
    private int quantity;
    private String shelf;
    private String box;
    private String model;
    private String brand;
    private String supplier;


    public InventoryItem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Bitmap getThumbnailBitmap() {
        if(thumbnail != null)
            return BitmapFactory.decodeByteArray(thumbnail, 0, thumbnail.length);
        else
            return null;
    }

    public void setThumbnailBitmap(Bitmap thumbnail) {
        if(thumbnail != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            this.thumbnail = stream.toByteArray();
        }
    }
}
