package com.example.demo.repos;

import com.example.demo.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepo extends JpaRepository<ApplicationUser, Integer> {

    ApplicationUser getByUsername(String username);

}
