package ru.voting_system.util;

import ru.voting_system.model.Role;
import ru.voting_system.model.User;
import ru.voting_system.to.UserTo;

public class UserUtil {
    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), Role.ROLE_USER);
    }
}