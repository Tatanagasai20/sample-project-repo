package com.priacc.hrsystem.dto;

import com.priacc.hrsystem.model.Address;
import com.priacc.hrsystem.model.BankDetails;
import com.priacc.hrsystem.model.Employee;
import com.priacc.hrsystem.model.Salary;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    
    private Long id;
    
    @NotBlank(message = "First name is required")
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    private String lastName;
    
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;
    
    private String gender;
    
    private String maritalStatus;
    
    private String nationality;
    
    @Email(message = "Email should be valid")
    private String email;
    
    private String phone;
    
    private String mobilePhone;
    
    private Address address;
    
    private Long departmentId;
    
    private String departmentName; // For display purposes only
    
    private Long positionId;
    
    private String positionTitle; // For display purposes only
    
    private Long managerId;
    
    private String managerName; // For display purposes only
    
    private String employeeType;
    
    @NotNull(message = "Join date is required")
    private LocalDate joinDate;
    
    private String status;
    
    private Salary salary;
    
    private BankDetails bankDetails;
    
    private Long userId;
    
    // Static method to convert Employee entity to EmployeeDto
    public static EmployeeDto fromEntity(Employee employee) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setDateOfBirth(employee.getDateOfBirth());
        dto.setGender(employee.getGender());
        dto.setMaritalStatus(employee.getMaritalStatus());
        dto.setNationality(employee.getNationality());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());
        dto.setMobilePhone(employee.getMobilePhone());
        dto.setAddress(employee.getAddress());
        
        if (employee.getDepartment() != null) {
            dto.setDepartmentId(employee.getDepartment().getId());
            dto.setDepartmentName(employee.getDepartment().getName());
        }
        
        if (employee.getPosition() != null) {
            dto.setPositionId(employee.getPosition().getId());
            dto.setPositionTitle(employee.getPosition().getTitle());
        }
        
        if (employee.getManager() != null) {
            dto.setManagerId(employee.getManager().getId());
            dto.setManagerName(employee.getManager().getFirstName() + " " + employee.getManager().getLastName());
        }
        
        dto.setEmployeeType(employee.getEmployeeType());
        dto.setJoinDate(employee.getJoinDate());
        dto.setStatus(employee.getStatus());
        dto.setSalary(employee.getSalary());
        dto.setBankDetails(employee.getBankDetails());
        
        if (employee.getUser() != null) {
            dto.setUserId(employee.getUser().getId());
        }
        
        return dto;
    }
}