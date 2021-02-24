package ru.voting_system.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.voting_system.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting_system.TestData.RestaurantTestData.RESTAURANT_2;

class ProfileRestaurantControllerTest extends AbstractControllerTest {

    static final String REST_URL = ProfileRestaurantController.REST_URL + "/";

    @Test
    void getAllWithMenu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "menu"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        //TODO add expected list of TO's
    }

    @Test
    void getByIdWithMenu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_2.getId() + "/menu"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        //TODO add expected list TO
                //.andExpect(contentJsonTo(createTo(RESTAURANT_2, DISHES)));
    }
}