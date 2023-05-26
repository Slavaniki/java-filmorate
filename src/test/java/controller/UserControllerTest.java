package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slava.filmorate.controller.UserController;
import org.slava.filmorate.exceptions.ValidationException;
import org.slava.filmorate.model.User;
import org.slava.filmorate.service.UserService;
import org.slava.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserControllerTest {
    private UserController userController;
    private User user;
    private User user2;
    private User user3;
    private UserService userService;
    private InMemoryUserStorage userStorage;

    @BeforeEach
    void setUp() {
        userStorage = new InMemoryUserStorage();
        userService = new UserService(userStorage);
        userController = new UserController(userService);
        user = User.builder()
                .email("KirillFireBeavers@gmail.com")
                .login("9impulse")
                .name("")
                .birthday(LocalDate.now())
                .build();
        user2 = User.builder()
                .email("  ")
                .login("  ")
                .name("")
                .birthday(LocalDate.now().plusMonths(10))
                .build();
        user3 = User.builder()
                .email("KirillFireBeavers@gmail.com")
                .login("9impulse")
                .name("")
                .birthday(LocalDate.of(1999,10,31))
                .build();
    }

    @Test
    void create() throws ValidationException {
        assertThrows(ValidationException.class,() -> userController.create(user2));
        assertThrows(NullPointerException.class,() -> userController.create(null));
        assertEquals(user3,userController.create(user3));
    }

    @Test
    void update() throws Exception {
        User user2 = userController.create(user);
        user2.setBirthday(LocalDate.now().plusMonths(10));
        assertThrows(ValidationException.class,() -> userController.update(user2));
        user2.setLogin("Kos tia noi");
        assertThrows(ValidationException.class,() -> userController.update(user2));
        user2.setLogin("");
        assertThrows(ValidationException.class,() -> userController.update(user2));
        user2.setEmail("Skeleton");
        assertThrows(ValidationException.class,() -> userController.update(user2));
        user2.setEmail("  ");
        assertThrows(ValidationException.class,() -> userController.update(user2));
        assertThrows(NullPointerException.class,() -> userController.update(null));
        User user33 = userController.create(user3);
        assertEquals("9impulse",userController.update(user33).getLogin());
    }
}