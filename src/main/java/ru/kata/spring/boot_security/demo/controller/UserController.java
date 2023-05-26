package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserDetailsImp;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class UserController {
    private final UserDetailsImp userDetailsImp;

    @Autowired
    public UserController(UserDetailsImp userDetailsImp) {
        this.userDetailsImp = userDetailsImp;
    }

    @GetMapping(value = "/{id}")
    public String showUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);

        return "/user/user";
    }



}
