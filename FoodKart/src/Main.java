import service.ResturantImpl;
import service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.addUser("Alice", "female", "9999999999", "110001");

        ResturantImpl restaurantService = new ResturantImpl();
        restaurantService.registerRestaurant("Biryani House", "110001/560001", "Biryani", 189.0, 10);

        System.out.println("Order placed: " + restaurantService.placeOrder("Biryani House", "110001", 3));
        System.out.println("Average rating before rating: " + restaurantService.showRestaurant("rating").get(0).getAverageRating());

        System.out.println("Rating added: " + restaurantService.rateRestaurant("Biryani House", 5, "Excellent"));
        System.out.println("Average rating after rating: " + restaurantService.showRestaurant("rating").get(0).getAverageRating());
    }
}