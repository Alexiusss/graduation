package ru.voting_system.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.voting_system.TestData.UserTestData;
import ru.voting_system.model.Vote;
import ru.voting_system.service.VoteService;
import ru.voting_system.web.AbstractControllerTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting_system.TestData.UserTestData.ADMIN;
import static ru.voting_system.TestData.VoteTestData.*;
import static ru.voting_system.TestUtil.readFromJson;
import static ru.voting_system.service.VoteService.TIME_LIMIT;
import static ru.voting_system.util.exception.ErrorType.VALIDATION_ERROR;
import static ru.voting_system.web.ExceptionInfoHandler.USER_VOTE_TIME_DUPLICATE;


class VoteControllerTest extends AbstractControllerTest {

    @Autowired
    VoteService voteService;

    public VoteControllerTest() {
        super(VoteController.REST_URL);
    }

    @Test
    void getAllWithRestaurants() throws Exception {
        perform(doGet().basicAuth(UserTestData.USER))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHERS.contentJson(VOTES));
    }

    @Test
    void getByDate() throws Exception {
        perform(doGet("filter?date=" + VOTE_2.getDate()).basicAuth(UserTestData.USER))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void createWithLocation() throws Exception {
        Vote newVote = getNew();
        ResultActions action = perform(doPost().basicAuth(UserTestData.USER).unwrap()
                .param("restaurantId", String.valueOf(newVote.getRestaurant().getId())))
                .andExpect(status().isCreated())
                .andDo(print());

        Vote created = readFromJson(action, Vote.class);
        Integer newId = created.getId();
        newVote.setId(newId);
        VOTE_MATCHERS.assertMatch(created, newVote);
        VOTE_MATCHERS.assertMatch(voteService.getByUserIdAndDate(UserTestData.USER_ID, LocalDate.now()), newVote);
    }

    @Test
    void update() throws Exception {
        Vote updated = getUpdated();
        ResultActions action = perform(doPost().basicAuth(ADMIN).unwrap()
                .param("restaurantId", String.valueOf(updated.getRestaurant().getId())))
                .andDo(print());

        if (LocalTime.now().isBefore(TIME_LIMIT)) {
            action.andExpect(status().isCreated());
            VOTE_MATCHERS.assertMatch(voteService.getByUserIdAndDate(UserTestData.ADMIN_ID, LocalDate.now()), updated);
        } else {
            action.andExpect(status().isUnprocessableEntity())
                    .andExpect(errorType(VALIDATION_ERROR))
                    .andExpect(detailMessage(USER_VOTE_TIME_DUPLICATE))
                    .andDo(print());
        }
    }
}