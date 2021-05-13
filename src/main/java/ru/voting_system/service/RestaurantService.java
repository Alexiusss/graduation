package ru.voting_system.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.voting_system.model.Restaurant;
import ru.voting_system.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.voting_system.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {
    public static final Sort SORT_NAME = Sort.by(Sort.Direction.ASC, "name");
    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public List<Restaurant> getAll() {
        return repository.findAll(SORT_NAME);
    }

    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(repository.save(restaurant), restaurant.getId());
    }

    public List<Restaurant> getAllWithVotes() {
        return repository.getAllWithMenuAndVotes(LocalDate.now());
    }

    public Restaurant getByIdWithVotes(int id) {
        return repository.getByIdWithMenuAndVotes(id);
    }


}
