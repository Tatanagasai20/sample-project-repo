package com.priacc.hrsystem.service;

import com.priacc.hrsystem.dto.AuthResponse;
import com.priacc.hrsystem.dto.LoginRequest;
import com.priacc.hrsystem.dto.SignupRequest;
import com.priacc.hrsystem.model.Role;
import com.priacc.hrsystem.model.User;
import com.priacc.hrsystem.repository.UserRepository;
import com.priacc.hrsystem.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public AuthResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        
        // Update last login time
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        return new AuthResponse(jwt, user.getId(), user.getEmail(), user.getRole().name());
    }

    public User registerUser(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Email is already taken");
        }

        // Create new user
        User user = new User(
                signupRequest.getFirstName(),
                signupRequest.getLastName(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword())
        );

        user.setRole(Role.valueOf(signupRequest.getRole()));
        
        return userRepository.save(user);
    }
}