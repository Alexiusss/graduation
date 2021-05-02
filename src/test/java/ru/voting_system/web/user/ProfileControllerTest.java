package ru.voting_system.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.voting_system.model.User;
import ru.voting_system.service.UserService;
import ru.voting_system.to.UserTo;
import ru.voting_system.util.UserUtil;
import ru.voting_system.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting_system.TestData.UserTestData.*;
import static ru.voting_system.web.user.ProfileController.REST_URL;
import static ru.voting_system.TestUtil.readFromJson;

class ProfileControllerTest extends AbstractControllerTest {

    @Autowired
    UserService userService;

    ProfileControllerTest() {
        super(REST_URL);
    }

    @Test
    void get() throws Exception {
        perform(doGet().basicAuth(USER))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHERS.contentJson(USER));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(doGet())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception {
        perform(doDelete().basicAuth(USER))
                .andExpect(status().isNoContent());

        USER_MATCHERS.assertMatch(userService.getAll(), ADMIN);
    }

    @Test
    void update() throws Exception {
        UserTo updatedTo = new UserTo(null, "newName", "newemail@ya.ru", "newPassword");
        perform(doPut().jsonBody(updatedTo).basicAuth(USER))
                .andDo(print())
                .andExpect(status().isNoContent());

        USER_MATCHERS.assertMatch(userService.get(USER_ID), UserUtil.updateFromTo(new User(USER), updatedTo));
    }

    @Test
    void register() throws Exception {
        UserTo newTo = new UserTo(null, "newName", "newmail@ya.ru", "newPassword");
        User newUser = UserUtil.createNewFromTo(newTo);
        ResultActions action = perform(doPost("/register").jsonBody(newTo))
                .andDo(print())
                .andExpect(status().isCreated());

        User created = readFromJson(action, User.class);
        Integer newId = created.getId();
        newUser.setId(newId);
        USER_MATCHERS.assertMatch(created, newUser);
        USER_MATCHERS.assertMatch(userService.get(newId), newUser);
    }
}