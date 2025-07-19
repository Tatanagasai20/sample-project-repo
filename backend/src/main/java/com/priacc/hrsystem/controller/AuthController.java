package com.priacc.hrsystem.controller;

import com.priacc.hrsystem.dto.AuthResponse;
import com.priacc.hrsystem.dto.LoginRequest;
import com.priacc.hrsystem.dto.SignupRequest;
import com.priacc.hrsystem.dto.UserDto;
import com.priacc.hrsystem.model.User;
import com.priacc.hrsystem.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication API for login and registration")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Authenticate user and generate JWT token")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/signup")
    @Operation(summary = "Register a new user")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        User user = authService.registerUser(signupRequest);
        return new ResponseEntity<>(UserDto.fromEntity(user), HttpStatus.CREATED);
    }
}