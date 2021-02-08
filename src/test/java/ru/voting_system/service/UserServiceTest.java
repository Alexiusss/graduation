package ru.voting_system.service;

import org.junit.Before;
import org.springframework.cache.CacheManager;
import ru.voting_system.TestData.VoteTestData;
import ru.voting_system.model.Role;
import ru.voting_system.model.User;
import ru.voting_system.util.JpaUtil;
import ru.voting_system.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import static ru.voting_system.TestData.UserTestData.*;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    JpaUtil jpaUtil;

    @Autowired
    protected UserService service;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("users").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    public void create() {
        User newUser = getNew();
        User created = service.create(newUser);
        Integer newId = created.getId();
        newUser.setId(newId);
        assertMatch(created, newUser);
        assertMatch(service.get(newId), newUser);
    }

    @Test
    public void delete() {
        thrown.expect(NotFoundException.class);
        service.delete(USER_ID);
        service.get(USER_ID);
    }

    @Test
    public void deletedNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(1);
    }

    @Test
    public void get() {
        User user = service.get(USER_ID);
        assertMatch(user, USER);
    }

    @Test
    public void getNotFound() {
        thrown.expect(NotFoundException.class);
        service.get(1);
    }

    @Test
    public void getByEmail() {
        User user = service.getByEmail("user@yandex.ru");
        assertMatch(user, USER);
    }

    @Test
    public void update() {
        User updated = getUpdated();
        service.update(updated);
        assertMatch(service.get(USER_ID), updated);
    }

    @Test
    public void getAll() {
        List<User> all = service.getAll();
        assertMatch(all, ADMIN, USER);
    }

    @Test
    public void getWithVotes(){
        User user = service.getWithVotes(USER_ID);
        assertMatch(user, USER);
        VoteTestData.assertMatch(user.getVotes(), VoteTestData.VOTES);
    }

    @Test
    public void getWithVotesNotFound(){
        thrown.expect(NotFoundException.class);
        service.getWithVotes(1);
    }

    @Test
    public void createWithException() throws Exception {
        validationRootCause(()->service.create(new User(USER_ID, "  ", "user@yandex.ru", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validationRootCause(()->service.create(new User(USER_ID, "User", "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validationRootCause(()->service.create(new User(ADMIN_ID, "Admin", "admin@yandex.ru", "  ", Role.ROLE_ADMIN)), ConstraintViolationException.class);
        validationRootCause(()->service.create(new User(ADMIN_ID, "Admin", "admin@yandex.ru", "pass", false, new Date(), Set.of())), ConstraintViolationException.class);
    }
}