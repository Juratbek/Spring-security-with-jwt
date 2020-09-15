package com.example.demo.services;

import com.example.demo.entity.ApplicationUser;
import com.example.demo.repos.ApplicationUserRepo;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService {

    private final ApplicationUserRepo repo;

    public ApplicationUserService(ApplicationUserRepo repo) {
        this.repo = repo;
    }

    public ApplicationUser getByUsername(String username) {
        return repo.getByUsername(username);
    }
}
