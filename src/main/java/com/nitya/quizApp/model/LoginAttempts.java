package com.nitya.quizApp.model;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;

@Entity(name = "LoginAttempts")
@Data
public class LoginAttempts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private  String email;
    private  boolean success;
    private  LocalDateTime createdAt;

    public LoginAttempts() {}
    public LoginAttempts(String email, boolean success, LocalDateTime createdAt) {
        this.email = email;
        this.success = success;
        this.createdAt = createdAt;
    }
}

