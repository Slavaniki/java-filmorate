package org.slava.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.slava.filmorate.exceptions.ResourceNotFoundException;
import org.slava.filmorate.exceptions.ValidationException;
import org.slava.filmorate.model.User;
import org.slava.filmorate.validation.Validator;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.HashMap;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Integer, User> users = new HashMap<>();
    private int id = 0;
    Validator validator = new Validator();

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User create(@RequestBody User user) throws ValidationException {
        validator.checkUser(user);
        id++;
        user.setId(id);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(),user);
        log.info("Пользователь успешно добавлен: " + user);
        return user;
    }

    @Override
    public User update(@RequestBody User user) throws ValidationException {
        validator.checkUser(user);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (users.containsKey(user.getId())) {
            User tmpUser = users.get(user.getId());
            tmpUser.setEmail(user.getEmail());
            tmpUser.setLogin(user.getLogin());
            tmpUser.setBirthday(user.getBirthday());
            tmpUser.setName(user.getName());
            users.replace(tmpUser.getId(), tmpUser);
            user = tmpUser;
        } else {
            throw new ResourceNotFoundException("Пользователя с таким id не существует");
        }
        log.info("Пользователь успешно обновлён: " + user);
        return user;
    }

    @Override
    public User findUserById(Integer id) {
        User user = users.get(id);
        if (user == null) {
            throw new ResourceNotFoundException("Пользователя с таким id не существует");
        } else {
            return user;
        }
    }

    @Override
    public boolean checkUserExist(Integer id) {
        return true;
    }
}