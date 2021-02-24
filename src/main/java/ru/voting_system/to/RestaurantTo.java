package ru.voting_system.to;

import java.beans.ConstructorProperties;
import java.util.Map;
import java.util.Objects;

public class RestaurantTo {

    private final Integer id;

    private final String name;

    private final Map<String, Integer> menu;

    private final Integer rating;

    @ConstructorProperties({"id", "name", "dishes", "rating"})
    public RestaurantTo(Integer id, String name, Map<String, Integer> dishes, Integer rating) {
        this.id = id;
        this.name = name;
        this.menu = dishes;
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getMenu() {
        return menu;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantTo that = (RestaurantTo) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(menu, that.menu) &&
                Objects.equals(rating, that.rating);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, menu, rating);
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", menu=" + menu +
                ", rating=" + rating +
                '}';
    }
}
