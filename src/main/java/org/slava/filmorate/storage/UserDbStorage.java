package org.slava.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.slava.filmorate.exceptions.ValidationException;
import org.slava.filmorate.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Slf4j
@Primary
@Qualifier("userDbStorage")
public class UserDbStorage implements UserStorage {

    @Override
    public Collection<User> findAll() {
        return null;
    }

    @Override
    public User create(User user) throws ValidationException {
        return null;
    }

    @Override
    public User update(User user) throws ValidationException {
        return null;
    }

    @Override
    public User findUserById(Integer id) {
        return null;
    }
}
