package com.priacc.hrsystem.controller;

import com.priacc.hrsystem.dto.UserDto;
import com.priacc.hrsystem.model.User;
import com.priacc.hrsystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Users", description = "User management API")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
    @Operation(summary = "Get all users", description = "Retrieve all users (Admin and HR only)")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDto> userDtos = users.stream()
                .map(UserDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/paged")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
    @Operation(summary = "Get all users with pagination", description = "Retrieve all users with pagination (Admin and HR only)")
    public ResponseEntity<Page<UserDto>> getAllUsers(Pageable pageable) {
        Page<User> users = userService.getAllUsers(pageable);
        Page<UserDto> userDtos = users.map(UserDto::fromEntity);
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN') or @userSecurity.isCurrentUser(#id)")
    @Operation(summary = "Get user by ID", description = "Retrieve a user by ID (Admin, HR, or the user themselves)")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(UserDto.fromEntity(user));
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
    @Operation(summary = "Get user by email", description = "Retrieve a user by email (Admin and HR only)")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(UserDto.fromEntity(user));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
    @Operation(summary = "Create a new user", description = "Create a new user (Admin and HR only)")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        User user = userService.createUser(userDto);
        return new ResponseEntity<>(UserDto.fromEntity(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN') or @userSecurity.isCurrentUser(#id)")
    @Operation(summary = "Update a user", description = "Update a user (Admin, HR, or the user themselves)")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        User user = userService.updateUser(id, userDto);
        return ResponseEntity.ok(UserDto.fromEntity(user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a user", description = "Delete a user (Admin only)")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
    @Operation(summary = "Update user status", description = "Activate or deactivate a user (Admin and HR only)")
    public ResponseEntity<UserDto> updateUserStatus(@PathVariable Long id, @RequestParam boolean active) {
        User user = userService.updateUserStatus(id, active);
        return ResponseEntity.ok(UserDto.fromEntity(user));
    }

    @PostMapping("/{id}/change-password")
    @PreAuthorize("@userSecurity.isCurrentUser(#id)")
    @Operation(summary = "Change user password", description = "Change a user's password (only the user themselves)")
    public ResponseEntity<?> changePassword(
            @PathVariable Long id,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        boolean success = userService.changePassword(id, oldPassword, newPassword);
        if (success) {
            return ResponseEntity.ok().body("Password changed successfully");
        } else {
            return ResponseEntity.badRequest().body("Incorrect old password");
        }
    }
}