package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UserDao;

import java.util.List;
import java.util.Set;


@Service
public class UserServiceImp implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }



    @Override
    @Transactional
    public boolean add(User user) {
        return userDao.add(user);
    }


    @Override
    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Override
    @Transactional
    public void remove(Long id) {
        userDao.remove(id);
    }

    @Override
    @Transactional
    public User save(User newUser) {
        return userDao.save(newUser);
    }

    @Override
    public User showUser(Long id) {
        return userDao.showUser(id);
    }

    @Override
    public Role getRole(String name) {
        return userDao.getRole(name);
    }

    @Override
    public Set<Role> getRoles() {
        return userDao.getRoles();
    }
}
