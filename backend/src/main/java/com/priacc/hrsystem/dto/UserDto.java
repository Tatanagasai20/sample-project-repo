package com.priacc.hrsystem.dto;

import com.priacc.hrsystem.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    
    private Long id;
    
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;
    
    @NotBlank(message = "Email is required")
    @Size(max = 100, message = "Email must be less than 100 characters")
    @Email(message = "Email should be valid")
    private String email;
    
    private String password;
    
    private Role role;
    
    private String avatarUrl;
    
    private boolean active;
    
    private Long employeeId;
    
    // Constructor without password for security
    public UserDto(Long id, String firstName, String lastName, String email, Role role, String avatarUrl, boolean active, Long employeeId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.avatarUrl = avatarUrl;
        this.active = active;
        this.employeeId = employeeId;
    }
    
    // Static method to convert User entity to UserDto
    public static UserDto fromEntity(com.priacc.hrsystem.model.User user) {
        return new UserDto(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getRole(),
            user.getAvatarUrl(),
            user.isActive(),
            user.getEmployee() != null ? user.getEmployee().getId() : null
        );
    }
}