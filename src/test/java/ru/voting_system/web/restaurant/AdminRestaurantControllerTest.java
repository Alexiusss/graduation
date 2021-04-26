package ru.voting_system.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.voting_system.TestData.DishTestData;
import ru.voting_system.TestData.RestaurantTestData;
import ru.voting_system.model.Dish;
import ru.voting_system.model.Restaurant;
import ru.voting_system.service.DishService;
import ru.voting_system.service.RestaurantService;
import ru.voting_system.util.exception.NotFoundException;
import ru.voting_system.web.AbstractControllerTest;
import ru.voting_system.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting_system.TestData.DishTestData.assertMatch;
import static ru.voting_system.TestData.DishTestData.contentJson;
import static ru.voting_system.TestData.DishTestData.*;
import static ru.voting_system.TestData.RestaurantTestData.assertMatch;
import static ru.voting_system.TestData.RestaurantTestData.contentJson;
import static ru.voting_system.TestData.RestaurantTestData.*;
import static ru.voting_system.TestUtil.readFromJson;

class AdminRestaurantControllerTest extends AbstractControllerTest {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    DishService dishService;

    static final String REST_URL = AdminRestaurantController.REST_URL + "/";

    private static final String DISHES_URL = AdminRestaurantController.REST_URL + AdminRestaurantController.DISHES_URL + '/';

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RESTAURANT_4, RESTAURANT_2, RESTAURANT_3, RESTAURANT_1));
    }

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RESTAURANT_1));
    }

    @Test
    void create() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated());

        Restaurant created = readFromJson(action, Restaurant.class);
        Integer newId = created.getId();
        newRestaurant.setId(newId);
        assertMatch(created, newRestaurant);
        assertMatch(restaurantService.get(newId), newRestaurant);
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> restaurantService.get(RESTAURANT_ID));

    }


    @Test
    void getDish() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(DISHES_URL + DISH_1.getId(), RESTAURANT_2.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DISH_1));
    }

    @Test
    void getAllDishes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(DISHES_URL, RESTAURANT_2.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DISH_1, DISH_2, DISH_3));
    }

    @Test
    void createDish() throws Exception {
        Dish newDish = DishTestData.getNew();
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(DISHES_URL, RESTAURANT_2.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isCreated());

        Dish created = readFromJson(action, Dish.class);
        int newId = created.getId();
        newDish.setId(newId);
        assertMatch(created, newDish);
        assertMatch(dishService.get(newId, RESTAURANT_2.getId()), newDish);
    }

    @Test
    void updateDish() throws Exception {
        Dish updated = DishTestData.getUpdated();
        mockMvc.perform(MockMvcRequestBuilders.put(DISHES_URL + DISH_1.getId(), RESTAURANT_2.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(dishService.get(DISH_1.getId(), RESTAURANT_2.getId()), updated);
    }

    @Test
    void deleteDish() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(DISHES_URL + DISH_1.getId(), RESTAURANT_2.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> dishService.get(DISH_1.getId(), RESTAURANT_2.getId()));
    }

}