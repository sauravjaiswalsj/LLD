package service;

import model.Resturant;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResturantImpl implements ResturantService {
    private final Map<String, Resturant> restaurantMap;

    public ResturantImpl() {
        this.restaurantMap = new HashMap<>();
    }

    @Override
    public void registerRestaurant(String restaurantName, String listOfPincodes, String foodName, double price, int quantity) {
        if (restaurantName == null || restaurantName.trim().isEmpty()) {
            throw new IllegalArgumentException("Restaurant name cannot be empty");
        }
        if (foodName == null || foodName.trim().isEmpty()) {
            throw new IllegalArgumentException("Food item name cannot be empty");
        }

        String normalizedName = restaurantName.trim();
        Resturant rest = new Resturant(normalizedName, listOfPincodes == null ? new String[0] : listOfPincodes.split("/"), foodName, price, quantity);
        restaurantMap.put(normalizedName, rest);
    }

    @Override
    public boolean updateQuantity(String restaurantName, int quantityToAdd) {
        Resturant restaurant = restaurantMap.get(restaurantName);
        if (restaurant == null || quantityToAdd < 0) {
            return false;
        }
        restaurant.getFootItem().addQuantity(quantityToAdd);
        return true;
    }

    @Override
    public List<Resturant> showRestaurant(String sortBy) {
        List<Resturant> restaurants = new ArrayList<>(restaurantMap.values());
        String sortKey = sortBy == null ? "name" : sortBy.trim().toLowerCase();

        if ("rating".equals(sortKey)) {
            restaurants.sort(Comparator.comparingDouble(Resturant::getAverageRating).reversed());
        } else {
            restaurants.sort(Comparator.comparing(Resturant::getResturantName));
        }
        return restaurants;
    }

    @Override
    public boolean placeOrder(String restaurantName, int quantity) {
        return placeOrder(restaurantName, null, quantity);
    }

    @Override
    public boolean placeOrder(String restaurantName, String pincode, int quantity) {
        Resturant restaurant = restaurantMap.get(restaurantName);
        if (restaurant == null || quantity <= 0) {
            return false;
        }
        if (pincode != null && !restaurant.servesArea(pincode)) {
            return false;
        }
        if (!restaurant.getFootItem().reduceQuantity(quantity)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean rateRestaurant(String restaurantName, int rating, String comment) {
        Resturant restaurant = restaurantMap.get(restaurantName);
        if (restaurant == null || rating < 1 || rating > 5) {
            return false;
        }
        restaurant.addRating(rating, comment);
        return true;
    }
}
