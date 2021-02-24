package ru.voting_system.util;

import ru.voting_system.model.Dish;
import ru.voting_system.model.Restaurant;
import ru.voting_system.to.RestaurantTo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RestaurantUtil {

    public static RestaurantTo createTo (Restaurant restaurant, Collection<Dish> dishes) {
        Map<String, Integer> menu = new HashMap<>();
        dishes.forEach(dish -> menu.putIfAbsent(dish.getName(), dish.getPrice()));
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), menu, restaurant.getVotes().size());
    }
}
