package com.example.demo.services;

import com.example.demo.entity.User;
import com.example.demo.entity.UserAuthority;
import com.google.common.collect.ImmutableSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    private final List<User> USERS = Arrays.asList(
            new User(1, "linda", password(), "Linda", "Smith",
                    ImmutableSet.of(new UserAuthority("ADMIN"))),
            new User(2, "anna", password(), "Anna", "Smith",
                    ImmutableSet.of(new UserAuthority("USER"))),
            new User(3, "tom", password(), "Tom", "Smith",
                    ImmutableSet.of(new UserAuthority("USER")))
    );

    public User getByUsername(String username) {
        return USERS.stream().filter(user -> user.getUserName().equals(username)).findFirst().orElse(null);
    }

    public User getById(Integer id) {
        return USERS.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    private String password() {
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        return passwordEncoder.encode("password");
    }

    public List<User> getAll(){
        return USERS;
    }
}
