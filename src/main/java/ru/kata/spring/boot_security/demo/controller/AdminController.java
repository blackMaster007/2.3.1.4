package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;


    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }


//    @GetMapping("/new")
//    public String createNewUser(Model model) {
//        System.out.println("start");
//        model.addAttribute("user", new User());
//        System.out.println("finish");
//        return "/admin/new";
//    }

    @PostMapping("/new")
    public String newUser(@ModelAttribute("user") User user,
                          @RequestParam(value = "selectedRoles", required = false) String[] selectedRoles){
        if (selectedRoles != null) {
            Set<Role> roles = new HashSet<>();
            for (String elemRoles : selectedRoles) {
                roles.add(userService.getRole(elemRoles));
            }
            user.setRoles(roles);
        }
        userService.save(user);
        return "redirect:/admin/";
    }

    @GetMapping("/new")
    public ModelAndView newUser() {
        User user = new User();
        ModelAndView mav = new ModelAndView("admin/new");
        mav.addObject("user", user);

        List<Role> roles = (List<Role>) roleRepository.findAll();

        mav.addObject("allRoles", roles);

        return mav;
    }

    @GetMapping("{id}/edit")
    public ModelAndView editUser(@PathVariable(name = "id") Long id) {
        User user = userService.showUser(id);
        ModelAndView mav = new ModelAndView("admin/edit");
        mav.addObject("user", user);

        List<Role> roles = (List<Role>) roleRepository.findAll();

        mav.addObject("allRoles", roles);

        return mav;
    }

    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin/";
    }
//
//    @GetMapping("/{id}/edit")
//    public String edit(@RequestParam(value = "id") Long id, Model model) {
//        model.addAttribute("user", userService.showUser(id));
//        model.addAttribute("roles", userService.getRoles());
//        return "/admin/edit";
//    }

    @GetMapping("/")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "/admin/users";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.remove(id);
        return "redirect:/admin/";
    }

    @GetMapping("/user/{id}")
    public String showUser(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.showUser(id));
        return "/admin/user";
    }

}
