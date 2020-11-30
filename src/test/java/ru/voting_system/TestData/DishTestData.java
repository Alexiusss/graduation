package ru.voting_system.TestData;

import ru.voting_system.model.Dish;

import java.time.Month;
import java.util.List;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.voting_system.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {
    public static final int DISH1_ID = START_SEQ + 11;
    public static final int RESTAURANT_ID = START_SEQ + 3;

    public static final Dish DISH_1 = new Dish(DISH1_ID, "Italia_dish1",of(2020, Month.SEPTEMBER, 12), 200);
    public static final Dish DISH_2 = new Dish(DISH1_ID+1, "Italia_dish2",of(2020, Month.SEPTEMBER, 12), 300);
    public static final Dish DISH_3 = new Dish(DISH1_ID + 2, "Italia_dish3",of(2020, Month.SEPTEMBER, 12), 450);

    public static final List<Dish> DISHES = List.of(DISH_1, DISH_2, DISH_3);


    public static Dish getNew(){
        return new Dish(null, "NewDeal", of(2020, Month.SEPTEMBER, 11), 100);
    }

    public static Dish getUpdated(){
        return new Dish(DISH1_ID, "UpdatedDish", DISH_1.getDate(), 300);
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
}
