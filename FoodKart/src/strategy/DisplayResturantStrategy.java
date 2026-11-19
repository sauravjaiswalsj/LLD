package strategy;

import model.Resturant;

import java.util.List;

public interface DisplayResturantStrategy {
    List<Resturant> displayResturant(Resturant resturant);

    List<Resturant> displayResturant(List<Resturant> restaurants);
}
