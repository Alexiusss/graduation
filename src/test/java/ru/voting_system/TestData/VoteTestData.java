package ru.voting_system.TestData;

import ru.voting_system.TestMatchers;
import ru.voting_system.model.Vote;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static ru.voting_system.TestData.RestaurantTestData.*;
import static ru.voting_system.TestData.UserTestData.ADMIN;
import static ru.voting_system.TestData.UserTestData.USER;
import static ru.voting_system.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static final int VOTE1_ID = START_SEQ + 23;
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ+1;

    public static final Vote VOTE_1 = new Vote(VOTE1_ID, LocalDate.of(2020, Month.SEPTEMBER, 12), USER, RESTAURANT_1);
    public static final Vote VOTE_2 = new Vote(VOTE1_ID + 3, LocalDate.of(2020, Month.SEPTEMBER, 13), USER, RESTAURANT_2);
    public static final Vote VOTE_3 = new Vote(VOTE1_ID + 4, LocalDate.of(2020, Month.SEPTEMBER, 11), USER, RESTAURANT_4);
    public static final Vote VOTE_4 = new Vote(VOTE1_ID + 6, LocalDate.of(2020, Month.SEPTEMBER, 14), USER, RESTAURANT_1);
    public static final Vote VOTE_5 = new Vote(VOTE1_ID + 7, LocalDate.of(2020, Month.SEPTEMBER, 15), USER, RESTAURANT_4);

    public static final List<Vote> VOTES = List.of(VOTE_5, VOTE_4, VOTE_2, VOTE_1, VOTE_3);

    public static Vote getNew() {
        return new Vote(null, LocalDate.now(), USER, RESTAURANT_2);
    }

    public static Vote getUpdated() {
        return new Vote(VOTE1_ID + 8, LocalDate.now(), ADMIN, RESTAURANT_1);
    }

   public static TestMatchers<Vote> VOTE_MATCHERS = TestMatchers.useFieldComparator(Vote.class, "user", "restaurant");


}
