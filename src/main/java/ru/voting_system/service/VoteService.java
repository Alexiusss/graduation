package ru.voting_system.service;

import org.springframework.stereotype.Service;
import ru.voting_system.model.Restaurant;
import ru.voting_system.model.User;
import ru.voting_system.model.Vote;
import ru.voting_system.repository.RestaurantRepository;
import ru.voting_system.repository.UserRepository;
import ru.voting_system.repository.VoteRepository;
import ru.voting_system.util.exception.VoteTimeLimitException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.voting_system.util.ValidationUtil.checkNotFoundWithId;


@Service
public class VoteService {
    public static final LocalTime TIME_LIMIT = LocalTime.of(11, 0);

    VoteRepository voteRepository;
    RestaurantRepository restaurantRepository;
    UserRepository userRepository;

    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public Vote vote(int restaurantId, int userId) throws Exception {

        LocalDate today = LocalDate.now();

        final Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        final User user = userRepository.findById(userId).orElse(null);

        Vote vote;

        vote = voteRepository.findByDateAndUserId(today, userId);

        if (vote == null) {
            vote = new Vote(today, user, restaurant);
            return voteRepository.save(vote);
        }

        if (LocalTime.now().isBefore(TIME_LIMIT)) {
            vote.setRestaurant(restaurant);
        } else {
            throw new VoteTimeLimitException(String.format("It`s too late, you can revote before %s", TIME_LIMIT));
        }
        return vote;
    }

    public List<Vote> getAllByUserIdWithRestaurants(int userId) {
        return voteRepository.getAllByUserIdWithRestaurants(userId);
    }

    public Vote getByUserIdAndDate(int userId, LocalDate date) {
        return checkNotFoundWithId(voteRepository.findByDateAndUserId(date, userId), userId);
    }

    public List<Vote> getAllByDateWithRestaurants(LocalDate date) {
        return voteRepository.getAllByDateWithRestaurants(date);
    }


}