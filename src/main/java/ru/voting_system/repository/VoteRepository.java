package ru.voting_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.voting_system.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.user.id=:userId ORDER BY v.date DESC")
    List<Vote> getAllByUserIdWithRestaurants(@Param("userId") int userId);

    Vote findByDateAndUserId(LocalDate date, int userId);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.date=:date ORDER BY v.id")
    List<Vote> getAllByDateWithRestaurants(@Param("date") LocalDate date);
}
