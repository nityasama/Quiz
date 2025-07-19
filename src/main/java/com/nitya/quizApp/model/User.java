package com.nitya.quizApp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "\"user\"")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String role;

    public User() {}

    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public List<String> getRoleList() {
        // Remove curly braces and split by comma
        if (role == null || role.length() < 2) return Collections.emptyList();
        String clean = role.substring(1, role.length() - 1); // Remove { and }
        if (clean.isEmpty()) return Collections.emptyList();
        return Arrays.asList(clean.split(","));
    }

}
