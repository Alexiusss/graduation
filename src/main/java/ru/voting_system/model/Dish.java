package ru.voting_system.model;

import org.hibernate.annotations.OnDelete;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "restaurant_id", "date"}, name = "dishes_unique_idx")})
public class Dish extends AbstractNamedEntity {

    @Column(name = "date", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    LocalDate date = LocalDate.now();

    @Column(name ="price", nullable = false)
    @Positive
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = CASCADE)
    @JsonBackReference("dishes")
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish( String name, @NotNull LocalDate date, @NotNull @Size(min = 1) Integer price, Restaurant restaurant) {
        super(null, name);
        this.date = date;
        this.price = price;
        this.restaurant = restaurant;
    }

    public Dish(Integer id, String name, @NotNull LocalDate date, @NotNull @Size(min = 1) Integer price, Restaurant restaurant) {
        super(id, name);
        this.date = date;
        this.price = price;
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "date=" + date +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
