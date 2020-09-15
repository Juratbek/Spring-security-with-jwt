package com.example.demo.controllers;

import com.example.demo.entity.User;
import com.example.demo.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("management")
public class ManagementController {

    private final UserService service;

    public ManagementController(UserService service) {
        this.service = service;
    }

    @GetMapping("users")
    public List<User> getAll() {
        return service.getAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin:write')")
    public String managementPost(){
        return "management post";
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('admin:write')")
    public String managementPut(){
        return "management put";
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('admin:write')")
    public String managementDelete(){
        return "management delete";
    }

}
