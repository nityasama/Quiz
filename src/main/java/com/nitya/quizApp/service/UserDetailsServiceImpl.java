package com.nitya.quizApp.service;

import com.nitya.quizApp.exceptions.NotFoundException;
import com.nitya.quizApp.model.User;
import com.nitya.quizApp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final UserRepository userRepository;
    public UserDetailsServiceImpl(UserRepository repository) {
        this.userRepository = repository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) {
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User does not exist, email: " + email));
        // Map roles to authorities
        logger.info("Loading user: {} with role :{}", email, user.getRole());
        List<GrantedAuthority> authorities = user.getRoleList().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }

}
