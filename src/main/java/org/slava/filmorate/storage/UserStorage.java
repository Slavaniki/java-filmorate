package org.slava.filmorate.storage;

import org.slava.filmorate.exceptions.ValidationException;
import org.slava.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    public Collection<User> findAll();

    public User create(User user) throws ValidationException;

    public User update(User user) throws ValidationException;

    public User findUserById(Integer id);

    boolean checkUserExist(Integer id) throws Exception;
}
