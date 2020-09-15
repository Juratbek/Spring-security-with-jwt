package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUserRole {

    @Id
    private String role;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<ApplicationUserPermission> permissions;

}
