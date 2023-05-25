package org.slava.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.slava.filmorate.model.Genres;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/genres")
@Slf4j
public class GenreController {

    private final JdbcTemplate jdbcTemplate;

    public GenreController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public Collection<Genres> findAll() {
        String sql = "select * from GENRE";
        Collection<Genres> genres = jdbcTemplate.query(sql, (rs, rowNum) -> makeGenres(rs));
        return genres;
    }

    private Genres makeGenres(ResultSet rs) throws SQLException {
        int id = rs.getInt("GENRE_ID");
        String name = rs.getString("NAME");
        return Genres.builder().id(id).name(name).build();
    }

    @RequestMapping("/{id}")
    @GetMapping
    public Genres findById(@PathVariable Integer id) {
        String sql = "select * from GENRE where GENRE_ID="+id;
        List<Genres> genres = jdbcTemplate.query(sql, (rs, rowNum) -> makeGenres(rs));
        if (genres.size()>0) {
            return genres.get(0);
        } else {
            throw new NoSuchElementException("genres not found");
        }
    }
}
