package com.nitya.quizApp.service;

import com.nitya.quizApp.exceptions.DuplicateException;
import com.nitya.quizApp.model.SignUpRequest;
import com.nitya.quizApp.model.User;
import com.nitya.quizApp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAuthenticationService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<User> signUp(SignUpRequest signUpRequest) {
        String email = signUpRequest.getEmail();
        //check if there is existing User with the email
        if(userRepository.existsByEmail(email)) {
            throw new DuplicateException(String.format("User with the email address '%s' already exists.", email));
        }
        String hashedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        // Convert List<String> to "{teacher,admin}" format
        String rolesString = "{" + String.join(",", signUpRequest.getRole()) + "}";
        User user = new User(signUpRequest.getName(),signUpRequest.getEmail(),hashedPassword,rolesString);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}
