package ru.voting_system.TestData;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.voting_system.model.Dish;

import java.time.Month;
import java.util.List;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.voting_system.TestUtil.readFromJsonMvcResult;
import static ru.voting_system.TestUtil.readListFromJsonMvcResult;
import static ru.voting_system.model.AbstractBaseEntity.START_SEQ;
import static ru.voting_system.TestData.RestaurantTestData.*;

public class DishTestData {
    public static final int DISH1_ID = START_SEQ + 11;
    public static final int RESTAURANT_IDD = START_SEQ + 3;

    public static final Dish DISH_1 = new Dish(DISH1_ID, "Italia_dish1",of(2020, Month.SEPTEMBER, 12), 200, RESTAURANT_2);
    public static final Dish DISH_2 = new Dish(DISH1_ID+1, "Italia_dish2",of(2020, Month.SEPTEMBER, 12), 300, RESTAURANT_2);
    public static final Dish DISH_3 = new Dish(DISH1_ID + 2, "Italia_dish3",of(2020, Month.SEPTEMBER, 12), 450, RESTAURANT_2);

    public static final List<Dish> DISHES = List.of(DISH_1, DISH_2, DISH_3);


    public static Dish getNew(){
        return new Dish(null, "NewDeal", of(2020, Month.SEPTEMBER, 11), 100, RESTAURANT_2);
    }

    public static Dish getUpdated(){
        return new Dish(DISH1_ID, "UpdatedDish", DISH_1.getDate(), 300, RESTAURANT_2);
    }

    public static void assertMatch(Dish actual, Dish excepted){
        assertThat(actual).isEqualToIgnoringGivenFields(excepted,"restaurant");
    }

    public static void assertMatch(Iterable<Dish> actual, Dish... expected){
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected){
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Dish... expected){
        return result -> assertMatch(readListFromJsonMvcResult(result, Dish.class), List.of(expected));
    }

    public static ResultMatcher contentJson(Dish expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, Dish.class), expected);
    }
}
