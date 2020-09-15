package com.example.demo.controllers;

import com.example.demo.entity.User;
import com.example.demo.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String main() {
        return "this is main page";
    }

    @GetMapping("users")
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("users/{id}")
    public User getById(@PathVariable("id") Integer id) {
        return userService.getById(id);
    }
}
