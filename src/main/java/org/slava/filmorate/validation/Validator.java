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
            throw new ValidationException("Электронная почта пустая или не содержит @");
        }
        if (user.getLogin() == null || user.getLogin().contains(" ") || user.getLogin().isBlank()) {
            log.error("Ошибка валидации: логин пустой или содержит пробелы");
            throw new ValidationException("Логин пустой или содержит пробелы");
        }
        if (user.getBirthday().isAfter(LocalDate.now()) || user.getBirthday() == null) {
            log.error("Ошибка валидации: дата рождения выставлена в будущем или пустая");
            throw new ValidationException("Дата рождения выставлена в будущем или пустая");
        }
    }

    public void checkFilm(Film film) throws ValidationException {
        if (film.getName() == null || film.getName().isBlank()) {
            log.error("Ошибка валидации: название фильма пустое");
            throw new ValidationException("Название фильма пустое");
        }
        if (film.getDescription() == null || film.getDescription().isBlank() || film.getDescription().length() > 200) {
            log.error("Ошибка валидации: описание превысило лимит в 200 символов или пустое");
            throw new ValidationException("Описание превысило лимит в 200 символов или пустое");
        }
        if (film.getReleaseDate() == null
                || film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            log.error("Ошибка валидации: дата релиза ранее 28.12.1895 или пустая");
            throw new ValidationException("Дата релиза ранее 28.12.1895 или пустая");
        }
        if (film.getDuration() <= 0) {
            log.error("Ошибка валидации: длительность фильма равна нулю или отрицательная");
            throw new ValidationException("Длительность фильма равна нулю или отрицательная");
        }
    }
}
