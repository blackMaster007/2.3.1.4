package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    void add(User user);
    List<User> listUsers();
    void remove(User user);
    User save(User newUser);
    User showUser(Long id);
    User findUserByEmail(String email);
}
