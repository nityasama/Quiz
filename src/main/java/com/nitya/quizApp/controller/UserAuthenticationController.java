package com.nitya.quizApp.controller;

import com.nitya.quizApp.exceptions.NotFoundException;
import com.nitya.quizApp.model.*;
import com.nitya.quizApp.repository.UserRepository;
import com.nitya.quizApp.service.LoginService;
import com.nitya.quizApp.service.UserAuthenticationService;
import com.nitya.quizApp.utility.JwtHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("auth")
public class UserAuthenticationController {
    @Autowired
    private UserAuthenticationService userAuthenticationService;
    @Autowired
    LoginService loginService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    JwtHelper jwtHelper;

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return userAuthenticationService.signUp(signUpRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
        } catch (BadCredentialsException e) {
            loginService.addLoginAttempt(loginRequest.email(), false);
            throw e;
        }
        // Fetch the user from the database to get their roles
        User user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new NotFoundException("User not found"));

        String token = jwtHelper.generateToken(loginRequest.email(),user.getRoleList());
        loginService.addLoginAttempt(loginRequest.email(), true);
        return ResponseEntity.ok(new LoginResponse(loginRequest.email(), token));
    }
    @GetMapping(value = "/loginAttempts")
    public ResponseEntity<List<LoginAttemptResponse>> loginAttempts(@RequestHeader("Authorization") String token) {
        String email = jwtHelper.extractUsername(token.replace("Bearer ", ""));
        List<LoginAttempts> loginAttempts = loginService.findRecentLoginAttempts(email);
        return ResponseEntity.ok(convertToDTOs(loginAttempts));
    }

    private List<LoginAttemptResponse> convertToDTOs(List<LoginAttempts> loginAttempts) {
        return loginAttempts.stream()
                .map(LoginAttemptResponse::convertToDTO)
                .collect(Collectors.toList());
    }


}
