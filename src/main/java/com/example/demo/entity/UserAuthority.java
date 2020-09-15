package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthority {

    @Id
    private String authority;

}
