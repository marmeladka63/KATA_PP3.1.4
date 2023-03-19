package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@RequestMapping("/admin")
@Controller
public class AdminController {


        private final UserService userService;
        private final RoleService roleService;

        @Autowired
        public AdminController(UserService userService, RoleService roleService) {
            this.userService = userService;
            this.roleService = roleService;
        }


        @GetMapping()
        public String getUserTab(Model model, @AuthenticationPrincipal User user) {
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("user", user);
            model.addAttribute("newUser", new User());
            model.addAttribute("roles", roleService.getAllRole());

            return "users";
        }


        @PostMapping()
        public String createUser(@ModelAttribute("user") User user) {
            userService.saveUser(user);
            return "redirect:/admin";

        }

        @PatchMapping("/{id}/edit")
        public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") long id) {
            userService.update(id, user);
            return "redirect:/admin";
        }

        @DeleteMapping("/{id}")
        public String deleteUser(@PathVariable("id") long id) {
            userService.delete(id);
            return "redirect:/admin";

        }


    }

