package ru.voting_system.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.voting_system.model.Restaurant;
import ru.voting_system.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    public void delete() {
        service.delete(RESTAURANT_ID);
        assertThrows(NotFoundException.class, () ->
                service.get(RESTAURANT_ID));
    }

    @Test
    public void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.delete(RESTAURANT_ID + 10));
    }

    @Test
    public void get() {
        Restaurant restaurant = service.get(RESTAURANT_ID);
        assertMatch(restaurant, RESTAURANT_1);
    }

    @Test
    public void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.get(1));
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

    @Test
    public void createWithException() throws Exception {
        validateRootCause(() -> service.create(new Restaurant(null, " ")), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Restaurant(null, "r")), ConstraintViolationException.class);
    }
}