package service;

import model.Resturant;

import java.util.List;

public interface ResturantService {
    void registerRestaurant(String restaurantName, String listOfPincodes, String foodName, double price,
                            int quantity);

    boolean updateQuantity(String restaurantName, int quantityToAdd);

    List<Resturant> showRestaurant(String sortBy);

    boolean placeOrder(String restaurantName, int quantity);

    boolean placeOrder(String restaurantName, String pincode, int quantity);

    boolean rateRestaurant(String restaurantName, int rating, String comment);
}
