package ru.voting_system.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurant_unique_name_idx")})
public class Restaurant extends AbstractNamedEntity{

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<Dish> dishes;

    public Restaurant() {
    }

    public Restaurant( String name, Set<Dish> dishes) {
        super(null, name);
        this.dishes = dishes;
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

    @Override
    public String toString() {
        return "Restaurant{" +
                "dishes=" + dishes +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
