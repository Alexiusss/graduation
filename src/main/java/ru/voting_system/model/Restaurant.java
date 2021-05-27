package ru.voting_system.model;

import javax.persistence.*;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurant_unique_name_idx")})
public class Restaurant extends AbstractNamedEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @JsonManagedReference("dishes")
    private Set<Dish> dishes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @JsonManagedReference("restaurant_votes")
    private Set<Vote> votes;

    public Restaurant() {
    }

    public Restaurant(String name) {
        super(null, name);
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.getId(), restaurant.getName());
    }

    public Set<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "dishes=" + dishes +
                ", votes=" + votes +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
