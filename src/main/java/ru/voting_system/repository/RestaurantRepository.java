package ru.voting_system.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.voting_system.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

       //  https://stackoverflow.com/a/5865605
    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "LEFT OUTER JOIN FETCH r.votes v " +
            "LEFT OUTER JOIN FETCH r.dishes d " +
            "WHERE d.date=:date ORDER BY r.name")
    List<Restaurant> getAllWithMenuAndVotes(@Param("date") LocalDate date);

    //  https://stackoverflow.com/a/24450905
    @EntityGraph(attributePaths = {"votes", "dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r WHERE r.id=:id")
    Restaurant getByIdWithMenuAndVotes(@Param("id") int id);

}
