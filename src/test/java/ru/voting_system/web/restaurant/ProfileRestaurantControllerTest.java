package ru.voting_system.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.voting_system.TestData.UserTestData;
import ru.voting_system.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting_system.TestData.RestaurantTestData.RESTAURANT_2;

class ProfileRestaurantControllerTest extends AbstractControllerTest {

    public ProfileRestaurantControllerTest() {
        super(ProfileRestaurantController.REST_URL);
    }

    @Test
    void getAllWithMenu() throws Exception {
                perform(doGet("menu").basicAuth(UserTestData.USER))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        //TODO add expected list of TO's
    }

    @Test
    void getByIdWithMenu() throws Exception {
                perform(doGet(RESTAURANT_2.getId() + "/menu").basicAuth(UserTestData.USER))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        //TODO add expected list TO
                //.andExpect(contentJsonTo(createTo(RESTAURANT_2, DISHES)));
    }
}