package ru.voting_system.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.voting_system.model.Dish;
import ru.voting_system.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;

import static ru.voting_system.TestData.DishTestData.*;

public class DishServiceTest extends AbstractServiceTest{

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

    @Test(expected = NotFoundException.class)
    public void delete() {
        service.delete(DISH1_ID, RESTAURANT_ID);
        service.get(DISH1_ID, RESTAURANT_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(1, 11);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotOwn() throws Exception {
        service.delete(DISH1_ID, RESTAURANT_ID+1);
    }

    @Test
    public void get() {
        Dish actual = service.get(DISH1_ID, RESTAURANT_ID);
        assertMatch(actual, DISH_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(1, 11);
    }

    @Test(expected = NotFoundException.class)
    public void getNotOwn() throws Exception {
        service.get(DISH1_ID, RESTAURANT_ID+1);
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

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        service.update(DISH_1, RESTAURANT_ID+1);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotOwn() throws Exception {
        service.get(DISH1_ID, RESTAURANT_ID+1);
    }

    @Test
    public void getByDate() {
        assertMatch(service.getByDate(LocalDate.of(2020, Month.SEPTEMBER, 12), RESTAURANT_ID), DISH_3, DISH_2, DISH_1);
    }
}