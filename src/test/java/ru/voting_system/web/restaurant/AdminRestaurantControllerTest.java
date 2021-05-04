package ru.voting_system.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.voting_system.TestData.DishTestData;
import ru.voting_system.TestData.RestaurantTestData;
import ru.voting_system.model.Dish;
import ru.voting_system.model.Restaurant;
import ru.voting_system.service.DishService;
import ru.voting_system.service.RestaurantService;
import ru.voting_system.util.exception.ErrorType;
import ru.voting_system.util.exception.NotFoundException;
import ru.voting_system.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.voting_system.TestData.DishTestData.*;
import static ru.voting_system.TestData.RestaurantTestData.*;
import static ru.voting_system.TestData.UserTestData.ADMIN;
import static ru.voting_system.TestUtil.readFromJson;

class AdminRestaurantControllerTest extends AbstractControllerTest {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    DishService dishService;

    public AdminRestaurantControllerTest() {
        super(AdminRestaurantController.REST_URL);
    }

    private static final String DISHES_URL = AdminRestaurantController.DISHES_URL + '/';

    @Test
    void getAll() throws Exception {
        perform(doGet().basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHERS.contentJson(RESTAURANT_4, RESTAURANT_2, RESTAURANT_3, RESTAURANT_1));
    }

    @Test
    void get() throws Exception {
                perform(doGet(RESTAURANT_ID).basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHERS.contentJson(RESTAURANT_1));
    }

    @Test
    void create() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        ResultActions action = perform(doPost().jsonBody(newRestaurant).basicAuth(ADMIN))
                .andExpect(status().isCreated());

        Restaurant created = readFromJson(action, Restaurant.class);
        Integer newId = created.getId();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHERS.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHERS.assertMatch(restaurantService.get(newId), newRestaurant);
    }

    @Test
    void createInvalid() throws Exception {
        Restaurant invalid = new Restaurant(null, null);
        perform(doPost().jsonBody(invalid).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value(ErrorType.VALIDATION_ERROR.name()))
                .andDo(print());
    }

    @Test
    void updateInvalid() throws Exception {
        Restaurant invalid = new Restaurant(RESTAURANT_ID, null);
        perform(doPut(RESTAURANT_ID).jsonBody(invalid).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value(ErrorType.VALIDATION_ERROR.name()))
                .andDo(print());
    }

    @Test
    void delete() throws Exception {
        perform(doDelete(RESTAURANT_ID).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> restaurantService.get(RESTAURANT_ID));

    }


    @Test
    void getDish() throws Exception {
                perform(doGet(DISHES_URL + DISH_1.getId(),RESTAURANT_2.getId()).basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHERS.contentJson(DISH_1));
    }

    @Test
    void getAllDishes() throws Exception {
        perform(doGet(DISHES_URL, RESTAURANT_2.getId()).basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHERS.contentJson(DISH_1, DISH_2, DISH_3));
    }

    @Test
    void createDish() throws Exception {
        Dish newDish = DishTestData.getNew();
        ResultActions action = perform(doPost(DISHES_URL, RESTAURANT_2.getId()).jsonBody(newDish).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isCreated());

        Dish created = readFromJson(action, Dish.class);
        int newId = created.getId();
        newDish.setId(newId);
        DISH_MATCHERS.assertMatch(created, newDish);
        DISH_MATCHERS.assertMatch(dishService.get(newId, RESTAURANT_2.getId()), newDish);
    }

    @Test
    void updateDish() throws Exception {
        Dish updated = DishTestData.getUpdated();
        perform(doPut(DISHES_URL + DISH_1.getId(), RESTAURANT_2.getId()).jsonBody(updated).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isNoContent());
        DISH_MATCHERS.assertMatch(dishService.get(DISH_1.getId(), RESTAURANT_2.getId()), updated);
    }

    @Test
    void deleteDish() throws Exception {
        perform(doDelete(DISHES_URL + DISH_1.getId(),RESTAURANT_2.getId()).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> dishService.get(DISH_1.getId(), RESTAURANT_2.getId()));
    }

}