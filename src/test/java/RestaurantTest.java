import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.configuration.IMockitoConfiguration;
import org.mockito.internal.util.Supplier;
import org.mockito.junit.MockitoJUnitRunner;


import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class RestaurantTest {
    // Arrange: Creating a Restaurant object
    LocalTime openingTime = LocalTime.parse("12:30:00");
    LocalTime closingTime = LocalTime.parse("23:00:00");
    Restaurant restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);

    // Arrange : Mocking restaurant object
    Restaurant mockRestaurant= Mockito.spy(restaurant);


    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() throws restaurantNotFoundException {
        //WRITE UNIT TEST CASE HERE

        mockRestaurant.openingTime= LocalTime.parse("12:30:00");
        LocalTime oneHourAfterOpeningTime = mockRestaurant.openingTime.plusHours(1);
        Mockito.when(mockRestaurant.getCurrentTime()).thenReturn(oneHourAfterOpeningTime);
        assertTrue(mockRestaurant.isRestaurantOpen());

    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() throws restaurantNotFoundException {
        //WRITE UNIT TEST CASE HERE

        mockRestaurant.closingTime= LocalTime.parse("23:00:00");
        LocalTime oneHourAfterClosingTime = mockRestaurant.closingTime.plusHours(1);
        Mockito.when(mockRestaurant.getCurrentTime()).thenReturn(oneHourAfterClosingTime);
        assertFalse(mockRestaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void adding_Sweet_Corn_Soup_and_Veg_Lasagne_to_order_then_cost_should_be_388(){
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        int billAmount= restaurant.getItemTotal("Sweet corn soup","Vegetable lasagne");
        assertEquals(388,billAmount);
    }
}