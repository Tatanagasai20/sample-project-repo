package com.priacc.hrsystem.dto;

import com.priacc.hrsystem.model.Position;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionDto {
    
    private Long id;
    
    @NotBlank(message = "Position title is required")
    @Size(min = 2, max = 100, message = "Position title must be between 2 and 100 characters")
    private String title;
    
    private String description;
    
    private boolean isManagementPosition;
    
    private Long departmentId;
    
    private String departmentName; // For display purposes only
    
    private int employeeCount; // For display purposes only
    
    // Static method to convert Position entity to PositionDto
    public static PositionDto fromEntity(Position position) {
        PositionDto dto = new PositionDto();
        dto.setId(position.getId());
        dto.setTitle(position.getTitle());
        dto.setDescription(position.getDescription());
        dto.setManagementPosition(position.getIsManagementPosition());
        
        if (position.getDepartment() != null) {
            dto.setDepartmentId(position.getDepartment().getId());
            dto.setDepartmentName(position.getDepartment().getName());
        }
        
        dto.setEmployeeCount(position.getEmployees() != null ? position.getEmployees().size() : 0);
        
        return dto;
    }
}