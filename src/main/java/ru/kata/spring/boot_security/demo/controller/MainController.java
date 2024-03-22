package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


import java.security.Principal;

@Controller
public class MainController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public MainController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping("/adminPanel")
    public String showAllUsers(Model model, Principal principal, @ModelAttribute("newUser") User user) {
        model.addAttribute("localUser", userService.findByUserUsername(principal.getName()));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("allRoles", roleService.getAll());
        return "adminPanel";
    }
    @PostMapping("/adminPanel/new")
    public String createUser(@ModelAttribute("newUser")User user) {
        userService.save(user);
        return "redirect:/adminPanel";
    }


    @PutMapping("/adminPanel/update")
    public String updateUser(@ModelAttribute("oneUser") User user) {
        userService.update(user);
        return "redirect:/adminPanel";
    }

    @DeleteMapping("/adminPanel/delete")
    public String deleteUser(@RequestParam(name = "id") Long id) {
        userService.getDelete(id);
        return "redirect:/adminPanel";
    }
}
