package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
public class AdminController {
    private RoleService roleService;
    private UserService userService;

    @Autowired
    public AdminController(RoleService roleService, UserService userService ) {
        this.roleService = roleService;
        this.userService = userService;

    }

    @GetMapping("/admin")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "people";
    }
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping("/admin/addUser")
    public String formAddUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("role", roleService.getAll());
        return "addUser";
    }

    @PostMapping("/admin/addUser")
    public String addUser(@ModelAttribute("userU") User user) {
        userService.save(user);
        return "redirect:/admin";
    }
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping("/admin/user")
    public String userById(@RequestParam(name = "id") Long id, Model model){
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "user";
    }
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping("/admin/update")
    public String updateUser(@RequestParam(name = "id") Long id, Model model){
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("allRoles", roleService.getAll());
        return "update";
    }

    @PutMapping("/admin/update")
    public String update(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin";
    }
    //------------------------------------------------------------------------------------------------------------------
    @DeleteMapping("/admin/delete_user")
    public String deleteUserById(@RequestParam(name = "id") Long id) {
        userService.getDelete(id);
        return "redirect:/admin";
    }
}
