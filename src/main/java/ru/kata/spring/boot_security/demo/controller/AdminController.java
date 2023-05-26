package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.*;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;



    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }




    @PostMapping("/new")
    public String newUser(@ModelAttribute("user") User user,
                          @RequestParam("roles") ArrayList<Long> roles) {

        Set<Role> roleSet = new HashSet<>((Collection<? extends Role>)
                roleService.getRolesById(roles));
        user.setRoles(roleSet);
        userService.save(user);
        return "redirect:/admin/";
    }

    @GetMapping("/new")
    public ModelAndView newUser() {
        User user = new User();
        ModelAndView mav = new ModelAndView("admin/users");
        mav.addObject("user", user);

        List<Role> roles = roleService.findAll();

        mav.addObject("allRoles", roles);

        return mav;
    }


    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam("roles") ArrayList<Long> roles) {

        Set<Role> roleSet = new HashSet<>(roleService.getRolesById(roles));
        user.setRoles(roleSet);
        userService.save(user);
        return "redirect:/admin/";
    }

    @GetMapping("/")
    public String showUsers(Model model,
                            @ModelAttribute("user") User user) {
        model.addAttribute("users", userService.listUsers());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        return "/admin/users";
    }
    @DeleteMapping("/{id}")
    public String delete(@ModelAttribute("user") User user) {
        userService.remove(user);
        return "redirect:/admin/";
    }

    @GetMapping("/user")
    public String showUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        return "/admin/user";
    }

}
