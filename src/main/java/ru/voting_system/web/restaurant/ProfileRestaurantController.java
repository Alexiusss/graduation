package ru.voting_system.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.voting_system.model.Restaurant;
import ru.voting_system.service.RestaurantService;
import ru.voting_system.to.RestaurantTo;

import java.util.ArrayList;
import java.util.List;

import static ru.voting_system.util.RestaurantUtil.createTo;
import static ru.voting_system.util.RestaurantUtil.createTos;

@RestController
@RequestMapping(value = ProfileRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class ProfileRestaurantController {

    static final String REST_URL = "/profile/restaurants";

    @Autowired
    private RestaurantService restaurantService;


    @GetMapping("/menu")
    public List<RestaurantTo> getAllWithMenu(){
        log.info("get list of restaurants with menu");
        List<RestaurantTo> restaurantTos = new ArrayList<>();
        restaurantService.getAllWithVotes()
                .forEach(restaurant -> restaurantTos.add(createTos(restaurant)));
        return restaurantTos;
    }

    @GetMapping("/{id}/menu")
    public RestaurantTo getWithMenu (@PathVariable int id){
        log.info("get {} with menu", id);
        Restaurant restaurant = restaurantService.getByIdWithVotes(id);
        return createTo(restaurant);
    }


}
