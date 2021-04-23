package ru.voting_system.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.voting_system.model.Vote;

import java.time.LocalDate;
import java.time.Month;

import static ru.voting_system.TestData.DishTestData.RESTAURANT_IDD;
import static ru.voting_system.TestData.VoteTestData.*;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    protected VoteService service;

    @Autowired
    protected RestaurantService restaurantService;

    @Autowired
    protected UserService userService;

    @Test
    void vote() throws Exception {
        Vote newVote = getNew();
        Vote created = service.vote(RESTAURANT_IDD, USER_ID);
        Integer newID = created.getId();
        newVote.setId(newID);
        assertMatch(newVote, created);
        assertMatch(service.voteRepository.findById(newID).orElse(null), newVote);
    }

    @Test
    void revote() throws Exception {
        Vote updated = getUpdated();
        Vote created = service.vote(updated.getRestaurant().getId(), updated.getUser().getId());
        assertMatch(updated, created);
    }

    @Test
    void getAllByUserIdWithRestaurants() {
        assertMatch(service.getAllByUserIdWithRestaurants(USER_ID), VOTES);
    }

    @Test
    void getByUserIdAndDate() {
        assertMatch(service.getByUserIdAndDate(USER_ID, LocalDate.of(2020, Month.SEPTEMBER, 12)), VOTE_1);
    }

}