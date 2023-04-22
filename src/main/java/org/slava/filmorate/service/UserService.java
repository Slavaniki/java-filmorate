package org.slava.filmorate.service;

import org.slava.filmorate.exceptions.ValidationException;
import org.slava.filmorate.model.User;
import org.slava.filmorate.storage.UserStorage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(User user, User friend) throws ValidationException {
        user.setFriend(friend.getId());
        friend.setFriend(user.getId());
        userStorage.update(user);
        userStorage.update(friend);
    }

    public void deleteFriend(User user, User friend) throws ValidationException {
        user.deleteFriend(friend.getId());
        friend.deleteFriend(user.getId());
        userStorage.update(user);
        userStorage.update(friend);
    }

    public List<User> getAllFriends(User user) {
        List<User> users = new ArrayList<>();
        user.getFriends().forEach(id -> users.add(userStorage.findUserById(id)));
        return users;
    }

    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public User create(User user) throws ValidationException {
        return userStorage.create(user);
    }

    public User update(User user) throws ValidationException {
        return userStorage.update(user);
    }
}
