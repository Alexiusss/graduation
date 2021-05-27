package ru.voting_system.model;

import lombok.*;
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
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"restaurant"})
public class Dish extends AbstractNamedEntity {

    @Column(name = "date", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    LocalDate date = LocalDate.now();

    @Column(name = "price", nullable = false)
    @Positive
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = CASCADE)
    @JsonBackReference("dishes")
    private Restaurant restaurant;


    public Dish(String name, @NotNull LocalDate date, @NotNull @Size(min = 1) Integer price, Restaurant restaurant) {
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

}
