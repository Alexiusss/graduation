package ru.voting_system.service;

import com.sun.istack.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.voting_system.model.Dish;
import ru.voting_system.repository.DishRepository;
import ru.voting_system.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.voting_system.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    public DishService(DishRepository dishRepository, RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Dish create(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        if (!dish.isNew() && get(dish.getId(), restaurantId) == null) {
            return null;
        }
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        return dishRepository.save(dish);
    }

    public void delete(int id, int restaurantId) {
        checkNotFoundWithId(dishRepository.delete(id, restaurantId) != 0, id);
    }

    public Dish get(int id, int restaurantId) {
        return checkNotFoundWithId(dishRepository.findById(id).filter(dish -> dish.getRestaurant().getId() == restaurantId).orElse(null), id);
    }

    public List<Dish> getAll(int restaurantId) {
        return dishRepository.getAll(restaurantId);
    }

    public void update(Dish dish, int restaurantId) {
        checkNotFoundWithId(create(dish, restaurantId), dish.getId());
    }

    public List<Dish> getByDate(@Nullable LocalDate date, int restaurantId) {
        return dishRepository.getByDate(date, restaurantId);
    }
}
