package ru.voting_system.TestData;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.voting_system.model.Restaurant;
import ru.voting_system.to.RestaurantTo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.voting_system.TestUtil.readFromJsonMvcResult;
import static ru.voting_system.TestUtil.readListFromJsonMvcResult;
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

    public static void assertMatch(Restaurant actual, Restaurant expected){
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dishes", "votes");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected){
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected){
        assertThat(actual).usingElementComparatorIgnoringFields("dishes", "votes").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Restaurant... expected){
        return result -> assertMatch(readListFromJsonMvcResult(result, Restaurant.class), List.of(expected));
    }

    public static ResultMatcher contentJson(Restaurant expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, Restaurant.class), expected);
    }

    public static ResultMatcher contentJsonTo(RestaurantTo expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, RestaurantTo.class), expected);
    }

    private static void assertMatch(RestaurantTo actual, RestaurantTo expected) {
        assertThat(actual).isEqualTo(expected);
    }

}
