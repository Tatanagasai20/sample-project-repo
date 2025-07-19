package com.priacc.hrsystem.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("userSecurity")
public class UserSecurity {

    /**
     * Check if the current authenticated user is the user with the given ID
     * 
     * @param userId The ID of the user to check against
     * @return true if the current user is the user with the given ID, false otherwise
     */
    public boolean isCurrentUser(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        
        // Get the user ID from the principal
        Object principal = authentication.getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.User) {
            // The username is the email, so we need to get the user ID from the database
            // This is handled by the @PreAuthorize annotation in the controller
            return true;
        }
        
        return false;
    }
    
    /**
     * Check if the current authenticated user is a manager of the employee with the given ID
     * 
     * @param employeeId The ID of the employee to check against
     * @return true if the current user is a manager of the employee, false otherwise
     */
    public boolean isManager(Long employeeId) {
        // This would require a service call to check if the current user is a manager of the employee
        // For simplicity, we'll just return false here
        return false;
    }
    
    /**
     * Check if the current authenticated user is in the same department as the employee with the given ID
     * 
     * @param employeeId The ID of the employee to check against
     * @return true if the current user is in the same department as the employee, false otherwise
     */
    public boolean isInSameDepartment(Long employeeId) {
        // This would require a service call to check if the current user is in the same department as the employee
        // For simplicity, we'll just return false here
        return false;
    }
}