package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    void add(User user);
    List<User> listUsers();
    void remove(Long id);
    User update(Long id, User newUser);
    User showUser(Long id);
}
