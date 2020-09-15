package com.example.demo.controllers;

import com.example.demo.entity.ApplicationUser;
import com.example.demo.entity.ApplicationUserPermission;
import com.example.demo.entity.ApplicationUserRole;
import com.example.demo.entity.MvUser;
import com.example.demo.repos.ApplicationUserRepo;
import com.example.demo.security.JwtProvider;
import com.example.demo.services.ApplicationUserService;
import com.google.common.collect.Sets;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class UserController {

    private final ApplicationUserService service;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final ApplicationUserRepo repo;
    private final PasswordEncoder passwordEncoder;

    public UserController(ApplicationUserService service, JwtProvider jwtProvider, AuthenticationManager authenticationManager, ApplicationUserRepo repo, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public String getToken(@RequestBody MvUser mvUser) {
//        init();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(mvUser.getUserName(), mvUser.getPassword()));
        ApplicationUser user = service.getByUsername(mvUser.getUserName());
        return jwtProvider.createToken(user);
    }

    public void init() {
        repo.save(new com.example.demo.entity.ApplicationUser("linda", "Linda",
                passwordEncoder.encode("password"), "Smith",
                new ApplicationUserRole("ROLE_ADMIN",
                        Sets.newHashSet(
                                new ApplicationUserPermission("admin:write"),
                                new ApplicationUserPermission("admin:read")
                        ))));
        repo.save(new com.example.demo.entity.ApplicationUser("tom", "Tom",
                passwordEncoder.encode("password"), "Smith",
                new ApplicationUserRole("ROLE_USER",
                        Sets.newHashSet(
                                new ApplicationUserPermission("user:write"),
                                new ApplicationUserPermission("user:read")
                        ))));
    }

}
