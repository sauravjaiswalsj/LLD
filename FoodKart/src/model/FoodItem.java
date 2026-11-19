package model;

public class FoodItem {
    private String name;
    private double price;
    private int quantity;

    public FoodItem(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean reduceQuantity(int quantityToReduce) {
        if (quantityToReduce <= 0 || quantityToReduce > this.quantity) {
            return false;
        }
        this.quantity -= quantityToReduce;
        return true;
    }

    public void addQuantity(int quantityToAdd) {
        if (quantityToAdd > 0) {
            this.quantity += quantityToAdd;
        }
    }
}
