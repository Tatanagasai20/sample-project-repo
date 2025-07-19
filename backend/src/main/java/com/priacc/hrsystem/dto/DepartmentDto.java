package com.priacc.hrsystem.dto;

import com.priacc.hrsystem.model.Department;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {
    
    private Long id;
    
    @NotBlank(message = "Department name is required")
    @Size(min = 2, max = 100, message = "Department name must be between 2 and 100 characters")
    private String name;
    
    private String description;
    
    private Long departmentHeadId;
    
    private String departmentHeadName; // For display purposes only
    
    private int employeeCount; // For display purposes only
    
    private List<Long> positionIds; // For reference only
    
    // Static method to convert Department entity to DepartmentDto
    public static DepartmentDto fromEntity(Department department) {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setDescription(department.getDescription());
        
        if (department.getDepartmentHead() != null) {
            dto.setDepartmentHeadId(department.getDepartmentHead().getId());
            dto.setDepartmentHeadName(department.getDepartmentHead().getFirstName() + " " + 
                                     department.getDepartmentHead().getLastName());
        }
        
        dto.setEmployeeCount(department.getEmployees() != null ? department.getEmployees().size() : 0);
        
        if (department.getPositions() != null) {
            dto.setPositionIds(department.getPositions().stream()
                    .map(position -> position.getId())
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }
}