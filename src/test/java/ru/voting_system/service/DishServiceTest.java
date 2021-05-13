package ru.voting_system.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.voting_system.TestData.RestaurantTestData;
import ru.voting_system.model.Dish;
import ru.voting_system.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.Month;

import static java.time.LocalDate.now;
import static java.time.LocalDate.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.voting_system.TestData.DishTestData.*;

public class DishServiceTest extends AbstractServiceTest {

    @Autowired
    DishService service;

    @Test
    void create() {
        Dish newDish = getNew();
        Dish created = service.create(newDish, RESTAURANT_IDD);
        Integer newId = created.getId();
        newDish.setId(newId);
        DISH_MATCHERS.assertMatch(newDish, created);
        DISH_MATCHERS.assertMatch(service.get(newId, RESTAURANT_IDD), newDish);
    }

    @Test
    void delete() {
        service.delete(DISH1_ID, RESTAURANT_IDD);
        assertThrows(NotFoundException.class, () ->
                service.get(DISH1_ID, RESTAURANT_IDD));
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.get(1, RESTAURANT_IDD));
    }

    @Test
    void deleteNotOwn() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.get(DISH1_ID, RESTAURANT_IDD + 1));
    }

    @Test
    void get() {
        Dish actual = service.get(DISH1_ID, RESTAURANT_IDD);
        DISH_MATCHERS.assertMatch(actual, DISH_1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.get(1, RESTAURANT_IDD));
    }

    @Test
    void getNotOwn() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.get(DISH1_ID, RESTAURANT_IDD + 1));
    }

    @Test
    void getAll() {
        DISH_MATCHERS.assertMatch(service.getAll(RESTAURANT_IDD), DISHES);
    }

    @Test
    void update() {
        Dish updated = getUpdated();
        service.update(updated, RESTAURANT_IDD);
        DISH_MATCHERS.assertMatch(service.get(DISH1_ID, RESTAURANT_IDD), updated);
    }

    @Test
    void updateNotFound() {
        NotFoundException e = assertThrows(NotFoundException.class, () -> service.update(DISH_1, RESTAURANT_IDD + 1));
        assertEquals(e.getMessage(), "Not found entity with id=" + DISH1_ID);
    }

    @Test
    void getByDate() {
        DISH_MATCHERS.assertMatch(service.getByDate(now(), RESTAURANT_IDD), DISH_3, DISH_2, DISH_1);
    }

    @Test
    void createWithException() throws Exception {
        validateRootCause(() -> service.create(new Dish(null, "  ", of(2021, Month.JANUARY, 27), 133, RestaurantTestData.RESTAURANT_2), RESTAURANT_IDD), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Dish(null, "New Dish", of(2021, Month.JANUARY, 27), 0, RestaurantTestData.RESTAURANT_2), RESTAURANT_IDD), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Dish(null, "New Dish", of(2021, Month.JANUARY, 27), -11, RestaurantTestData.RESTAURANT_2), RESTAURANT_IDD), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Dish(null, "New Dish", null, 155, null), RESTAURANT_IDD), ConstraintViolationException.class);
    }
}