package controller;

import org.slava.filmorate.controller.FilmController;
import org.slava.filmorate.exceptions.ValidationException;
import org.slava.filmorate.model.Film;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmControllerTest {
    private FilmController filmController;

    @BeforeEach
    void setUp() {
        filmController = new FilmController();
    }

    @Test
    void create() throws ValidationException {
        Film film2 = Film.builder()
                .id(2)
                .name("Гарри Поттер")
                .description("варпиашмпршариироамигимргаргиагригриурсисаруисраиисрсаиимрмикассиавщирцщаргисщаргиусщг"
                        + "раицщгмаищгсицкщагцимщгркаисщгрцкигвниысщгцирущгсцщугнищсгуниущгрмицкщгрисщнгкищгцркимуг"
                        + "ицщгкрсищцкгцумиущгимщргрии")
                .releaseDate(LocalDate.of(1895,12,27))
                .duration(0)
                .build();
        Film film3 = Film.builder()
                .id(3)
                .name("Звёздные войны")
                .description("Вселенная «Звёздных войн» расширилась с выходом на Disney+ сериала «Книга Бобы Фетта» — "
                        + "спин-оффа суперуспешного «Мандалорца». Но события нового шоу не просто разворачиваются после"
                        + " «Мандалорца», а тож")
                .releaseDate(LocalDate.of(1895,12,28))
                .duration(1)
                .build();
        assertThrows(ValidationException.class,() -> filmController.create(film2));
        assertThrows(NullPointerException.class,() -> filmController.create(null));
        assertEquals(film3,filmController.create(film3));
    }

    @Test
    void update() throws ValidationException {
        Film film2 = Film.builder()
                .id(2)
                .name("  ")
                .description("варпиашмпршариироамигимргаргиагригриурсисаруисраиисрсаиимрмикассиавщирцщаргисщаргиусщг"
                        + "раицщгмаищгсицкщагцимщгркаисщгрцкигвниысщгцирущгсцщугнищсгуниущгрмицкщгрисщнгкищгцркимуг"
                        + "ицщгкрсищцкгцумиущгимщргрии")
                .releaseDate(LocalDate.of(1895,12,27))
                .duration(0)
                .build();
        assertThrows(ValidationException.class,() -> filmController.update(film2));
        film2.setName("Гарри Поттер");
        assertThrows(ValidationException.class,() -> filmController.update(film2));
        film2.setDescription(" ");
        assertThrows(ValidationException.class,() -> filmController.update(film2));
        film2.setDescription("kjfff");
        assertThrows(ValidationException.class,() -> filmController.update(film2));
        film2.setReleaseDate(LocalDate.of(1895,12,29));
        assertThrows(ValidationException.class,() -> filmController.update(film2));
        film2.setDuration(23);
        assertEquals(film2,filmController.update(film2));
        assertThrows(NullPointerException.class,() -> filmController.update(null));
        Film film3 = Film.builder()
                .id(3)
                .name("Звёздные войны")
                .description("Вселенная «Звёздных войн» расширилась с выходом на Disney+ сериала «Книга Бобы Фетта» — "
                        + "спин-оффа суперуспешного «Мандалорца». Но события нового шоу не просто разворачиваются после"
                        + " «Мандалорца», а тож")
                .releaseDate(LocalDate.of(1895,12,28))
                .duration(1)
                .build();
        assertEquals(film3,filmController.update(film3));
    }
}