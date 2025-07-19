package com.priacc.hrsystem.dto;

import com.priacc.hrsystem.model.Attendance;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDto {
    
    private Long id;
    
    @NotNull(message = "Employee ID is required")
    private Long employeeId;
    
    private String employeeName; // For display purposes only
    
    private LocalDate date;
    
    private LocalTime checkInTime;
    
    private LocalTime checkOutTime;
    
    private LocalTime breakStartTime;
    
    private LocalTime breakEndTime;
    
    private Double workHours;
    
    private Attendance.AttendanceStatus status;
    
    private String notes;
    
    private String ipAddress;
    
    private String location;
    
    private String departmentName; // For display purposes only
    
    // Static method to convert Attendance entity to AttendanceDto
    public static AttendanceDto fromEntity(Attendance attendance) {
        AttendanceDto dto = new AttendanceDto();
        dto.setId(attendance.getId());
        
        if (attendance.getEmployee() != null) {
            dto.setEmployeeId(attendance.getEmployee().getId());
            dto.setEmployeeName(attendance.getEmployee().getFirstName() + " " + attendance.getEmployee().getLastName());
            
            if (attendance.getEmployee().getDepartment() != null) {
                dto.setDepartmentName(attendance.getEmployee().getDepartment().getName());
            }
        }
        
        dto.setDate(attendance.getDate());
        dto.setCheckInTime(attendance.getCheckInTime());
        dto.setCheckOutTime(attendance.getCheckOutTime());
        dto.setBreakStartTime(attendance.getBreakStartTime());
        dto.setBreakEndTime(attendance.getBreakEndTime());
        dto.setWorkHours(attendance.getWorkHours());
        dto.setStatus(attendance.getStatus());
        dto.setNotes(attendance.getNotes());
        dto.setIpAddress(attendance.getIpAddress());
        dto.setLocation(attendance.getLocation());
        
        return dto;
    }
}