package edu.msudenver.roadrunners.inventory;


public class InventoryItem {
    private Integer id;

    private String name;
    private double cost;
    private int quantity;
    private String shelf;
    private String box;
    private String model;
    private String brand;
    private String supplier;

    public InventoryItem() {
    }

    public InventoryItem(int id, String name, int quantity, String shelf, String box, String model,
                         String brand, String supplier) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.shelf = shelf;
        this.box = box;
        this.model = model;
        this.brand = brand;
        this.supplier = supplier;
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

}
