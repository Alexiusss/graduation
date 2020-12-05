package ru.voting_system.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.voting_system.model.Vote;

import java.time.LocalDate;
import java.time.Month;

import static ru.voting_system.TestData.DishTestData.RESTAURANT_ID;
import static ru.voting_system.TestData.VoteTestData.*;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    protected VoteService service;

    @Autowired
    protected RestaurantService restaurantService;

    @Autowired
    protected UserService userService;

    @Test
    public void vote() throws Exception {
        Vote newVote = getNew();
        Vote created = service.vote(RESTAURANT_ID, USER_ID);
        Integer newID = created.getId();
        assertMatch(newVote, created);
        assertMatch(service.voteRepository.findById(newID).orElse(null), newVote);
    }

    @Test
    public void revote() throws Exception {
        Vote updated = getUpdated();
        Vote created = service.vote(updated.getRestaurant().getId(), updated.getUser().getId());
        assertMatch(updated, created);
    }

    @Test
    public void getAllByUserId() {
        assertMatch(service.getAllByUserId(USER_ID), VOTES);
    }

    @Test
    public void getByUserIdAndDate() {
        assertMatch(service.getByUserIdAndDate(USER_ID, LocalDate.of(2020, Month.SEPTEMBER, 12)), VOTE_1);
    }

}