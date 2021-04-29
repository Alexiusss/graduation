package ru.voting_system.web.json;

import org.junit.jupiter.api.Test;
import ru.voting_system.model.Vote;

import java.util.List;

import static ru.voting_system.TestData.VoteTestData.*;

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
}