package com.nitya.quizApp.service;


import com.nitya.quizApp.model.LoginAttempts;
import com.nitya.quizApp.repository.LoginAttemptsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class LoginService {
    @Autowired
    LoginAttemptsRepository loginAttemptRepository;
    public void addLoginAttempt(String email, boolean success) {
        LoginAttempts loginAttempt = new LoginAttempts(email, success, LocalDateTime.now());
        loginAttemptRepository.save(loginAttempt);
    }

    public List<LoginAttempts> findRecentLoginAttempts(String email) {

        return loginAttemptRepository.findTopByEmailOrderByCreatedAtDesc(email,10);
    }

}
