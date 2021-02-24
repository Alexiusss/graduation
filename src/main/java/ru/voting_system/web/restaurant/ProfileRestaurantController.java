package ru.voting_system.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.voting_system.service.DishService;
import ru.voting_system.service.RestaurantService;
import ru.voting_system.to.RestaurantTo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.voting_system.util.RestaurantUtil.createTo;

@RestController
@RequestMapping(value = ProfileRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestaurantController {

    static final String REST_URL = "/profile/restaurants";

    protected final Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    RestaurantService restaurantService;

    @Autowired
    DishService dishService;


    @GetMapping("/menu")
    public List<RestaurantTo> getAllWithMenu(){
        log.info("get list of restaurants with menu");
        List<RestaurantTo> restaurantTos = new ArrayList<>();
        LocalDate date = LocalDate.of(2020, 9, 11);
        //LocalDate date = LocalDate.now();
        restaurantService.getAllWithVotes()
                .forEach(restaurant -> restaurantTos.add(createTo(restaurant,  dishService.getByDate(date, restaurant.getId()))));
        return restaurantTos;
    }

    @GetMapping("/{id}/menu")
    public RestaurantTo getWithMenu (@PathVariable int id){
        log.info("get {} with menu", id);
        LocalDate date = LocalDate.of(2020, 9, 12);
       // LocalDate date = LocalDate.now();
        return createTo(restaurantService.getByIdWithVotes(id), dishService.getByDate(date, id));
    }


}
