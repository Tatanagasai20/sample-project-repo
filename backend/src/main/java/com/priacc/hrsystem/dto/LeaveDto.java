package com.priacc.hrsystem.dto;

import com.priacc.hrsystem.model.Leave;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveDto {
    
    private Long id;
    
    @NotNull(message = "Employee ID is required")
    private Long employeeId;
    
    private String employeeName; // For display purposes only
    
    @NotNull(message = "Leave type is required")
    private Leave.LeaveType leaveType;
    
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    
    @NotNull(message = "End date is required")
    private LocalDate endDate;
    
    private Integer numberOfDays;
    
    @Size(max = 500, message = "Reason must be less than 500 characters")
    private String reason;
    
    private Leave.LeaveStatus status;
    
    private String comments;
    
    private Long approvedById;
    
    private String approvedByName; // For display purposes only
    
    private LocalDateTime approvedAt;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private String departmentName; // For display purposes only
    
    // Static method to convert Leave entity to LeaveDto
    public static LeaveDto fromEntity(Leave leave) {
        LeaveDto dto = new LeaveDto();
        dto.setId(leave.getId());
        
        if (leave.getEmployee() != null) {
            dto.setEmployeeId(leave.getEmployee().getId());
            dto.setEmployeeName(leave.getEmployee().getFirstName() + " " + leave.getEmployee().getLastName());
            
            if (leave.getEmployee().getDepartment() != null) {
                dto.setDepartmentName(leave.getEmployee().getDepartment().getName());
            }
        }
        
        dto.setLeaveType(leave.getLeaveType());
        dto.setStartDate(leave.getStartDate());
        dto.setEndDate(leave.getEndDate());
        dto.setNumberOfDays(leave.getNumberOfDays());
        dto.setReason(leave.getReason());
        dto.setStatus(leave.getStatus());
        dto.setComments(leave.getComments());
        
        if (leave.getApprovedBy() != null) {
            dto.setApprovedById(leave.getApprovedBy().getId());
            dto.setApprovedByName(leave.getApprovedBy().getFirstName() + " " + leave.getApprovedBy().getLastName());
        }
        
        dto.setApprovedAt(leave.getApprovedAt());
        dto.setCreatedAt(leave.getCreatedAt());
        dto.setUpdatedAt(leave.getUpdatedAt());
        
        return dto;
    }
}