package ru.voting_system.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import ru.voting_system.TestData.VoteTestData;
import ru.voting_system.model.Role;
import ru.voting_system.model.User;
import ru.voting_system.util.JpaUtil;
import ru.voting_system.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.voting_system.TestData.UserTestData.*;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    JpaUtil jpaUtil;

    @Autowired
    protected UserService service;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() throws Exception {
        Objects.requireNonNull(cacheManager.getCache("users")).clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    void create() {
        User newUser = getNew();
        User created = service.create(newUser);
        Integer newId = created.getId();
        newUser.setId(newId);
        USER_MATCHERS.assertMatch(created, newUser);
        USER_MATCHERS.assertMatch(service.get(newId), newUser);
    }

    @Test
    void duplicateMailCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.ROLE_USER)));
    }

    @Test
    void delete() {
        service.delete(USER_ID);
        assertThrows(NotFoundException.class, () ->
                service.delete(USER_ID));
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.delete(1));
    }

    @Test
    void get() {
        User user = service.get(ADMIN_ID);
        USER_MATCHERS.assertMatch(user, ADMIN);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.get(1));
    }

    @Test
    void getByEmail() {
        User user = service.getByEmail("admin@gmail.com");
        USER_MATCHERS.assertMatch(user, ADMIN);
    }

    @Test
    void update() {
        User updated = getUpdated();
        service.update(updated);
        USER_MATCHERS.assertMatch(service.get(USER_ID), updated);
    }

    @Test
    void getAll() {
        List<User> all = service.getAll();
        USER_MATCHERS.assertMatch(all, ADMIN, USER);
    }

    @Test
    void getWithVotes() {
        User user = service.getWithVotes(USER_ID);
        USER_MATCHERS.assertMatch(user, USER);
        VoteTestData.VOTE_MATCHERS.assertMatch(user.getVotes(), VoteTestData.VOTES);
    }

    @Test
    void getWithVotesNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.getWithVotes(1));
    }

    @Test
    void createWithException() throws Exception {
        validateRootCause(() -> service.create(new User(USER_ID, "  ", "user@yandex.ru", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(USER_ID, "User", "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(ADMIN_ID, "Admin", "admin@yandex.ru", "  ", Role.ROLE_ADMIN)), ConstraintViolationException.class);
    }
}