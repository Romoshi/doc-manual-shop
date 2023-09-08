package com.romoshi.bot.controllers;

import com.romoshi.bot.models.User;
import com.romoshi.bot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllProducts() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User addProduct(@RequestBody User user) {
        return userService.saveUser(user);
    }
}
