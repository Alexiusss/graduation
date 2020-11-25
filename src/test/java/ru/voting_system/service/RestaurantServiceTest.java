package ru.voting_system.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.voting_system.model.Restaurant;
import ru.voting_system.util.exception.NotFoundException;

import java.util.List;

import static ru.voting_system.TestData.RestaurantTestData.*;

public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    RestaurantService service;

    @Test
    public void create() {
        Restaurant newRestaurant = getNew();
        Restaurant created = service.create(newRestaurant);
        Integer newId = created.getId();
        newRestaurant.setId(newId);
        assertMatch(created, newRestaurant);
        assertMatch(service.get(newId), newRestaurant);
    }

    @Test(expected = NotFoundException.class)
    public void delete() {
        service.delete(RESTAURANT_ID);
        service.get(RESTAURANT_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(1);
    }

    @Test
    public void get() {
        Restaurant restaurant = service.get(RESTAURANT_ID);
        assertMatch(restaurant, RESTAURANT_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(1);
    }

    @Test
    public void getByName() {
        Restaurant restaurant = service.getByName("TOKIO-CITY");
        assertMatch(restaurant, RESTAURANT_1);
    }

    @Test
    public void getAll() {
        List<Restaurant> all = service.getAll();
        assertMatch(all, ALL_RESTS);
    }

    @Test
    public void update() {
        Restaurant updated = getUpdated();
        service.update(updated);
        assertMatch(service.get(RESTAURANT_ID), updated);
    }
}