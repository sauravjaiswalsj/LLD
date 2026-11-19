package strategy;

import model.Resturant;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PriceStrategy implements DisplayResturantStrategy {
    @Override
    public List<Resturant> displayResturant(Resturant resturant) {
        List<Resturant> restaurants = new ArrayList<>();
        if (resturant != null) {
            restaurants.add(resturant);
        }
        return restaurants;
    }

    @Override
    public List<Resturant> displayResturant(List<Resturant> restaurants) {
        List<Resturant> sortedRestaurants = new ArrayList<>(restaurants);
        sortedRestaurants.sort(Comparator.comparingDouble(r -> r.getFootItem().getPrice()));
        return sortedRestaurants;
    }
}
