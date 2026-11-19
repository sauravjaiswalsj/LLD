package model;

public class Order {
    private final int orderId;
    private final User user;
    private final Resturant restaurant;
    private final int quantity;
    private final String serviceArea;

    public Order(int orderId, User user, Resturant restaurant, int quantity, String serviceArea) {
        this.orderId = orderId;
        this.user = user;
        this.restaurant = restaurant;
        this.quantity = quantity;
        this.serviceArea = serviceArea;
    }

    public int getOrderId() {
        return orderId;
    }

    public User getUser() {
        return user;
    }

    public Resturant getRestaurant() {
        return restaurant;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getServiceArea() {
        return serviceArea;
    }
}
