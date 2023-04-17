package org.slava.filmorate.validation;

import lombok.extern.slf4j.Slf4j;
import org.slava.filmorate.exceptions.ValidationException;
import org.slava.filmorate.model.Film;
import org.slava.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
public class Validator {
    public void checkUser(User user) throws ValidationException {
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

    public void checkFilm(Film film) throws ValidationException {
        if (film.getName() == null || film.getName().isBlank()) {
            log.error("Ошибка валидации: название пустое");
            throw new ValidationException();
        }
        if (film.getDescription() == null || film.getDescription().isBlank() || film.getDescription().length() > 200) {
            log.error("Ошибка валидации: описание превысило лимит в 200 символов или пустое");
            throw new ValidationException();
        }
        if (film.getReleaseDate() == null
                || film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            log.error("Ошибка валидации: дата релиза ранее 28.12.1895 или пустая");
            throw new ValidationException();
        }
        if (film.getDuration() <= 0) {
            log.error("Ошибка валидации: длительность фильма равна нулю или отрицательная");
            throw new ValidationException();
        }
    }
}
