package ru.voting_system.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.beans.ConstructorProperties;
import java.util.Map;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 100)
    String name;

    Map<String, Integer> menu;

    @Positive
    Integer rating;

    @ConstructorProperties({"id", "name", "dishes", "rating"})
    public RestaurantTo(Integer id, String name, Map<String, Integer> dishes, Integer rating) {
        super(id);
        this.name = name;
        this.menu = dishes;
        this.rating = rating;
    }
}
