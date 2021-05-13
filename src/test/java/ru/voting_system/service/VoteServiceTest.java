package ru.voting_system.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.voting_system.model.Vote;
import ru.voting_system.util.exception.VoteTimeLimitException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import static ru.voting_system.TestData.DishTestData.RESTAURANT_IDD;
import static ru.voting_system.TestData.VoteTestData.*;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    protected VoteService voteService;

    @Autowired
    protected RestaurantService restaurantService;

    @Autowired
    protected UserService userService;

    @Test
    void vote() throws Exception {
        Vote newVote = getNew();
        Vote created = voteService.vote(RESTAURANT_IDD, USER_ID);
        Integer newID = created.getId();
        newVote.setId(newID);
        System.out.println(newID);
        VOTE_MATCHERS.assertMatch(newVote, created);
        VOTE_MATCHERS.assertMatch(voteService.getByUserIdAndDate(USER_ID, LocalDate.now()), newVote);
    }

    @Test
    void revote() throws Exception {
        Vote updated = getUpdated();
        try {
            Vote created = voteService.vote(updated.getRestaurant().getId(), updated.getUser().getId());
            if (LocalTime.now().isBefore(LocalTime.of(11, 00))) {
                VOTE_MATCHERS.assertMatch(updated, created);
            }
        } catch (Exception e){
            assert e.getClass().equals(VoteTimeLimitException.class);
        }
    }

    @Test
    void getAllByUserIdWithRestaurants() {
        VOTE_MATCHERS.assertMatch(voteService.getAllByUserIdWithRestaurants(USER_ID), VOTES);
    }

    @Test
    void getByUserIdAndDate() {
        VOTE_MATCHERS.assertMatch(voteService.getByUserIdAndDate(USER_ID, LocalDate.of(2020, Month.SEPTEMBER, 12)), VOTE_1);
    }

}