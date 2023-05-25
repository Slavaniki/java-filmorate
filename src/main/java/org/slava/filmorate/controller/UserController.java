package org.slava.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.slava.filmorate.exceptions.ResourceNotFoundException;
import org.slava.filmorate.exceptions.ValidationException;
import org.slava.filmorate.model.User;
import org.slava.filmorate.service.UserService;
import org.slava.filmorate.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;
    private Validator validator = new Validator();

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> findAll() {
        return userService.findAll();
    }

    @RequestMapping("/{id}")
    @GetMapping
    public User findById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @RequestMapping("/{id}/friends")
    @GetMapping
    public List<User> findAllFriends(@PathVariable Integer id) {
        return userService.getAllFriends(id);
    }

    @RequestMapping("/{id}/friends/common/{otherId}")
    @GetMapping
    public List<User> findAllCommonFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        return userService.findAllCommonFriends(id, otherId);
    }

    @PostMapping
    public User create(@RequestBody User user) throws ValidationException, ResourceNotFoundException {
        validator.checkUser(user);
        return userService.create(user);
    }

    @PutMapping
    public User update(@RequestBody User user) throws ValidationException, ResourceNotFoundException {
        validator.checkUser(user);
        return userService.update(user);
    }

    @RequestMapping(value = "/{id}/friends/{friendId}", method = RequestMethod.PUT)
    public void addFriend(@PathVariable Integer id, @PathVariable Integer friendId) throws ValidationException {
        userService.addFriend(id,friendId);
    }

    @RequestMapping(value = "/{id}/friends/{friendId}", method = RequestMethod.DELETE)
    public void deleteFriend(@PathVariable Integer id, @PathVariable Integer friendId) throws ValidationException {
        userService.deleteFriend(id,friendId);
    }
}