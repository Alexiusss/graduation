package ru.voting_system.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.voting_system.TestData.RestaurantTestData;
import ru.voting_system.model.Dish;
import ru.voting_system.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;

import javax.validation.ConstraintViolationException;

import static ru.voting_system.TestData.DishTestData.*;
import static java.time.LocalDate.of;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DishServiceTest extends AbstractServiceTest {

    @Autowired
    DishService service;

    @Test
    public void create() {
        Dish newDish = getNew();
        Dish created = service.create(newDish, RESTAURANT_ID);
        Integer newId = created.getId();
        newDish.setId(newId);
        assertMatch(newDish, created);
        assertMatch(service.get(newId, RESTAURANT_ID), newDish);
    }

    @Test
    public void delete() {
        service.delete(DISH1_ID, RESTAURANT_ID);
        assertThrows(NotFoundException.class, () ->
                service.get(DISH1_ID, RESTAURANT_ID));
    }

    @Test
    public void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.get(1, RESTAURANT_ID));
    }

    @Test
    public void deleteNotOwn() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.get(DISH1_ID, RESTAURANT_ID + 1));
    }

    @Test
    public void get() {
        Dish actual = service.get(DISH1_ID, RESTAURANT_ID);
        assertMatch(actual, DISH_1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.get(1, RESTAURANT_ID));
    }

    @Test
    public void getNotOwn() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.get(DISH1_ID, RESTAURANT_ID + 1));
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(RESTAURANT_ID), DISHES);
    }

    @Test
    public void update() {
        Dish updated = getUpdated();
        service.update(updated, RESTAURANT_ID);
        assertMatch(service.get(DISH1_ID, RESTAURANT_ID), updated);
    }

    @Test
    public void updateNotFound() {
        NotFoundException e = assertThrows(NotFoundException.class, () -> service.update(DISH_1, RESTAURANT_ID + 1));
        assertEquals(e.getMessage(), "Not found entity with id=" + DISH1_ID);
    }

    @Test
    public void getByDate() {
        assertMatch(service.getByDate(LocalDate.of(2020, Month.SEPTEMBER, 12), RESTAURANT_ID), DISH_3, DISH_2, DISH_1);
    }

    @Test
    public void createWithException() throws Exception {
        validateRootCause(() -> service.create(new Dish(null, "  ", of(2021, Month.JANUARY, 27), 133, RestaurantTestData.RESTAURANT_2), RESTAURANT_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Dish(null, "New Dish", of(2021, Month.JANUARY, 27), 0, RestaurantTestData.RESTAURANT_2), RESTAURANT_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Dish(null, "New Dish", of(2021, Month.JANUARY, 27), -11, RestaurantTestData.RESTAURANT_2), RESTAURANT_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Dish(null, "New Dish", null, 155, null), RESTAURANT_ID), ConstraintViolationException.class);
    }
}