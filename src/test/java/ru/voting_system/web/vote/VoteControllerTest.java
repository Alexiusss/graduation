package ru.voting_system.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.voting_system.model.Vote;
import ru.voting_system.service.VoteService;
import ru.voting_system.web.AbstractControllerTest;
import ru.voting_system.web.json.JsonUtil;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting_system.TestData.VoteTestData.*;
import static ru.voting_system.TestUtil.readFromJson;


class VoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteController.REST_URL + "/";

    @Autowired
    VoteService voteService;

    @Test
    void getAllWithRestaurants() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTES));
    }

    @Test
    void getByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "filter?date=" + VOTE_2.getDate()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void createWithLocation() throws Exception {
        Vote newVote = getNew();
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtil.writeValue(newVote)));

        Vote created = readFromJson(action, Vote.class);
        Integer newId = created.getId();
        newVote.setId(newId);
        assertMatch(created, newVote);
        assertMatch(voteService.getByUserIdAndDate(USER_ID, LocalDate.now()), newVote);
    }

    @Test
    void update() throws Exception {
        Vote updated = getUpdated();

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + (VOTE1_ID + 8))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        assertMatch(voteService.getByUserIdAndDate(ADMIN_ID, LocalDate.now()), updated);
    }
}