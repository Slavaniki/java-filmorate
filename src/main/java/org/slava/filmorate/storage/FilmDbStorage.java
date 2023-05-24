package org.slava.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.slava.filmorate.exceptions.ValidationException;
import org.slava.filmorate.model.Film;
import org.slava.filmorate.model.Genre;
import org.slava.filmorate.model.Rating;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Component
@Slf4j
@Primary
@Qualifier("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Film create(Film film) throws ValidationException {
        String sqlQuery = "insert into film(NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATING_ID) " +
                "values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId());
        return film;
    }

    @Override
    public Film update(Film film) throws ValidationException {
        return null;
    }

    @Override
    public Collection<Film> findAll() {
        String sql = "select * from film";
        Collection<Film> films = jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
        return films;
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        int id = rs.getInt("film_id");
        String name = rs.getString("name");

        String desc = null;
        LocalDate rd = null;
        int dur = 0;
/*        Set<Integer> likes = rs.("likes");
        String sql = "select * from film";
        Collection<String> films = jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));*/
        List<Genre> genres = null;
        Rating rate = null;

        Film film = Film.builder().id(id).name(name).build();

        return film;
    }

    @Override
    public Film findById(Integer id) {
        return null;
    }
}
