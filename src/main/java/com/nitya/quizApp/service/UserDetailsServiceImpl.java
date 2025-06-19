package com.nitya.quizApp.service;

import com.nitya.quizApp.exceptions.NotFoundException;
import com.nitya.quizApp.model.User;
import com.nitya.quizApp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    public UserDetailsServiceImpl(UserRepository repository) {
        this.userRepository = repository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) {
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User does not exist, email: " + email));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }

}
