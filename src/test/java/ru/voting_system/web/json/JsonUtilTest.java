package ru.voting_system.web.json;

import org.junit.jupiter.api.Test;
import ru.voting_system.TestData.UserTestData;
import ru.voting_system.model.User;
import ru.voting_system.model.Vote;

import java.util.List;

import static ru.voting_system.TestData.VoteTestData.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonUtilTest {

    @Test
    void readWriteValue()  {
        String json = JsonUtil.writeValue(VOTE_1);
        System.out.println(json);
        Vote vote = JsonUtil.readValue(json, Vote.class);
        VOTE_MATCHERS.assertMatch(vote, VOTE_1);
    }

    @Test
    void readWriteValues(){
        String json = JsonUtil.writeValue(VOTES);
        System.out.println(json);
        List<Vote> votes = JsonUtil.readValues(json, Vote.class);
        VOTE_MATCHERS.assertMatch(votes, VOTES);
    }

    @Test
    void testWriteOnlyAccess() throws Exception {
        String json = JsonUtil.writeValue(UserTestData.USER);
        System.out.println(json);
        assertThat(json, not(containsString("password")));
        String jsonWithPass = JsonUtil.writeAdditionProps(UserTestData.USER, "password", "newPass");
        System.out.println(jsonWithPass);
        User user = JsonUtil.readValue(jsonWithPass, User.class);
        assertEquals(user.getPassword(), "newPass");
    }
}