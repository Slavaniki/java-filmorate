package org.slava.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.slava.filmorate.exceptions.ValidationException;
import org.slava.filmorate.model.User;
import org.slava.filmorate.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final HashMap<Integer,User> users = new HashMap<>();
    private int id = 0;
    Validator validator = new Validator();

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
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

    @PutMapping
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
            throw new ValidationException();
        }
        log.info("Пользователь успешно обновлён: " + user);
        return user;
    }
}