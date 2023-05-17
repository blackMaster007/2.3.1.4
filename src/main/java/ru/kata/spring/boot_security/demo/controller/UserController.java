package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserDetailsImp;



@Controller
public class UserController {

    private final UserDetailsImp userDetailsImp;

    @Autowired
    public UserController(UserDetailsImp userDetailsImp) {
        this.userDetailsImp = userDetailsImp;
    }



    @GetMapping(value = "/{username}")
    public String showUser(Model model, @PathVariable("username") String username) {
        model.addAttribute("user", userDetailsImp.loadUserByUsername(username));
        return "/user/user";
    }


}
