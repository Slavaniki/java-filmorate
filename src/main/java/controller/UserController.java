package controller;

import exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import model.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final HashMap<Integer,User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User create(@RequestBody User user) throws ValidationException {
        checkUser(user);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(),user);
        log.info("Пользователь успешно добавлен: " + user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) throws ValidationException {
        checkUser(user);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (users.containsKey(user.getId())) {
            User tmpUser = users.get(user.getId());
            tmpUser.setEmail(user.getEmail());
            tmpUser.setLogin(user.getLogin());
            tmpUser.setBirthday(user.getBirthday());
            users.replace(tmpUser.getId(), tmpUser);
            user = tmpUser;
        } else {
            users.put(user.getId(), user);
        }
        log.info("Пользователь успешно обновлён: " + user);
        return user;
    }

    private void checkUser(User user) throws ValidationException {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.error("Ошибка валидации: электронная почта пустая или не содержит @");
            throw new ValidationException();
        }
        if (user.getLogin() == null || user.getLogin().contains(" ") || user.getLogin().isBlank()) {
            log.error("Ошибка валидации: логин пустой или содержит пробелы");
            throw new ValidationException();
        }
        if (user.getBirthday().isAfter(LocalDate.now()) || user.getBirthday() == null) {
            log.error("Ошибка валидации: дата рождения выставлена в будущем или пустая");
            throw new ValidationException();
        }
    }
}