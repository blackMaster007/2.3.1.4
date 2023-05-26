package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;


@Service
public class UserServiceImp implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserDetailsImp userDetailsImp;

    @Autowired
    public UserServiceImp(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsImp userDetailsImp) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDetailsImp = userDetailsImp;
    }



    @Override
    @Transactional
    public void add(User user) {
        userRepository.save(user);
    }


    @Override
    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void remove(User user) {
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public User save(User user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setLastName(user.getLastName());
        newUser.setAge(user.getAge());
        newUser.setEmail(user.getEmail());
        newUser.setRoles(user.getRoles());
        if (user.getPassword() == null) {
            newUser.setPassword(findUserByEmail(user.getEmail()).getPassword());
        } else user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User showUser(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
