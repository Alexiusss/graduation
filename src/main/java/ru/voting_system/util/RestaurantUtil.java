package ru.voting_system.util;

import lombok.experimental.UtilityClass;
import ru.voting_system.model.Restaurant;
import ru.voting_system.to.RestaurantTo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class RestaurantUtil {

    public static RestaurantTo createTos(Restaurant restaurant) {
        Map<String, Integer> menu = new HashMap<>();
        restaurant.getDishes()
                .forEach(dish -> menu.putIfAbsent(dish.getName(), dish.getPrice()));
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), menu, restaurant.getVotes().size());
    }

    public static RestaurantTo createTo(Restaurant restaurant) {
        Map<String, Integer> menu = new HashMap<>();
        restaurant.getDishes().stream()
                .filter(dish -> dish.getDate().equals(LocalDate.now()))
                .forEach(dish -> menu.putIfAbsent(dish.getName(), dish.getPrice()));
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), menu, restaurant.getVotes().size());
    }
}
