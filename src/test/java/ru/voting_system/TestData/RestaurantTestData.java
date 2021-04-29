package ru.voting_system.TestData;

import ru.voting_system.TestMatchers;
import ru.voting_system.model.Restaurant;
import ru.voting_system.to.RestaurantTo;

import java.util.List;

import static ru.voting_system.model.AbstractBaseEntity.START_SEQ;


public class RestaurantTestData {

    public static final int RESTAURANT_ID = START_SEQ + 2;

    public static final Restaurant RESTAURANT_1 = new Restaurant(RESTAURANT_ID,"TOKIO-CITY");
    public static final Restaurant RESTAURANT_2 = new Restaurant(RESTAURANT_ID + 1, "Italia");
    public static final Restaurant RESTAURANT_3 = new Restaurant(RESTAURANT_ID + 2,"KFC");
    public static final Restaurant RESTAURANT_4 = new Restaurant(RESTAURANT_ID + 3,"Beer House");

    public static final List<Restaurant> ALL_RESTS = List.of(RESTAURANT_4, RESTAURANT_2, RESTAURANT_3, RESTAURANT_1);


    public static Restaurant getNew(){
        return new Restaurant("NewRestaurant");
    }

    public static Restaurant getUpdated(){
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setName("UpdateName");
        return updated;
    }

   public static TestMatchers<Restaurant> RESTAURANT_MATCHERS = TestMatchers.useFieldComparator(Restaurant.class, "dishes", "votes");
   public static TestMatchers<RestaurantTo> RESTAURANT_TO_MATCHERS = TestMatchers.useEquals(RestaurantTo.class);

}
