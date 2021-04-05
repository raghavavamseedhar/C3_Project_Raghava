import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService {
    private static List<Restaurant> restaurants = new ArrayList<>();

    public RestaurantService() {

        restaurants.add(new Restaurant("Amelie's cafe",
                "Hyderabad",
                LocalTime.parse("10:00:00.000"),
                LocalTime.parse("18:00:00.000")));

    }

    public Restaurant findRestaurantByName(String restaurantName) throws restaurantNotFoundException {

        for(Restaurant restaurant : restaurants) {
            if(restaurant.getName().equals(restaurantName)) {
                return restaurant;
            }
            else throw new restaurantNotFoundException("Error : Restaurant could not be found");
        }
        return null;

    }


    public Restaurant addRestaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        Restaurant newRestaurant = new Restaurant(name, location, openingTime, closingTime);
        restaurants.add(newRestaurant);
        return newRestaurant;
    }

    public Restaurant removeRestaurant(String restaurantName) throws restaurantNotFoundException {
        Restaurant restaurantToBeRemoved = findRestaurantByName(restaurantName);
        restaurants.remove(restaurantToBeRemoved);
        return restaurantToBeRemoved;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }
}
