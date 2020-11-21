package ru.voting_system.TestData;

import ru.voting_system.model.Restaurant;

import javax.print.DocFlavor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.voting_system.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {

    public static final int RESTAURANT_1_ID = START_SEQ + 2;
    public static final int RESTAURANT_2_ID = START_SEQ + 3;
    public static final int RESTAURANT_3_ID = START_SEQ + 4;
    public static final int RESTAURANT_4_ID = START_SEQ + 5;

    public static final Restaurant RESTAURANT_1 = new Restaurant(RESTAURANT_1_ID,"TOKIO-CITY");
    public static final Restaurant RESTAURANT_2 = new Restaurant(RESTAURANT_2_ID, "Italia");
    public static final Restaurant RESTAURANT_3 = new Restaurant(RESTAURANT_3_ID,"KFC");
    public static final Restaurant RESTAURANT_4 = new Restaurant(RESTAURANT_4_ID,"Beer House");

    public static final List<Restaurant> ALL_RESTS = List.of(RESTAURANT_4, RESTAURANT_2, RESTAURANT_3, RESTAURANT_1);


    public static Restaurant getNew(){
        return new Restaurant("NewRestaurant");
    }

    public static Restaurant getUpdated(){
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setName("UpdateName");
        return updated;
    }

    public static void assertMatch(Restaurant actual, Restaurant expected){
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dishes");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected){
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> excpected){
        assertThat(actual).usingElementComparatorIgnoringFields("dishes").isEqualTo(excpected);
    }

}
