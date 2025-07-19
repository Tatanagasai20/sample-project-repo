package com.priacc.hrsystem.service;

import com.priacc.hrsystem.dto.UserDto;
import com.priacc.hrsystem.exception.ResourceNotFoundException;
import com.priacc.hrsystem.model.User;
import com.priacc.hrsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    public User createUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email is already taken");
        }

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        user.setActive(true);

        return userRepository.save(user);
    }

    public User updateUser(Long id, UserDto userDto) {
        User user = getUserById(id);

        // Check if email is being changed and if it's already taken
        if (!user.getEmail().equals(userDto.getEmail()) && userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email is already taken");
        }

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        
        // Only update password if it's provided
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        
        if (userDto.getRole() != null) {
            user.setRole(userDto.getRole());
        }
        
        if (userDto.getAvatarUrl() != null) {
            user.setAvatarUrl(userDto.getAvatarUrl());
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    public User updateUserStatus(Long id, boolean active) {
        User user = getUserById(id);
        user.setActive(active);
        return userRepository.save(user);
    }

    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        User user = getUserById(userId);
        
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        
        return false;
    }
}