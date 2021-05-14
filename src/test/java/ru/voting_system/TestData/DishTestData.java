package ru.voting_system.TestData;

import ru.voting_system.TestMatchers;
import ru.voting_system.model.Dish;

import java.util.List;

import static java.time.LocalDate.now;
import static ru.voting_system.TestData.RestaurantTestData.RESTAURANT_2;
import static ru.voting_system.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {
    public static final int DISH1_ID = START_SEQ + 11;
    public static final int RESTAURANT_IDD = START_SEQ + 3;

    public static final Dish DISH_1 = new Dish(DISH1_ID, "Italia_dish1",now(), 200, RESTAURANT_2);
    public static final Dish DISH_2 = new Dish(DISH1_ID+1, "Italia_dish2",now(), 300, RESTAURANT_2);
    public static final Dish DISH_3 = new Dish(DISH1_ID + 2, "Italia_dish3",now(), 450, RESTAURANT_2);

    public static final List<Dish> DISHES = List.of(DISH_1, DISH_2, DISH_3);


    public static Dish getNewDish(){
        return new Dish(null, "NewDish", now(), 100, RESTAURANT_2);
    }

    public static Dish getUpdatedDish(){
        return new Dish(DISH1_ID, "UpdatedDish", DISH_1.getDate(), 300, RESTAURANT_2);
    }

   public static TestMatchers<Dish> DISH_MATCHERS = TestMatchers.useFieldComparator(Dish.class, "restaurant");
}
