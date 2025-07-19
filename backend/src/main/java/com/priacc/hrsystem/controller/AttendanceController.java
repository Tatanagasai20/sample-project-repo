package com.priacc.hrsystem.controller;

import com.priacc.hrsystem.dto.AttendanceDto;
import com.priacc.hrsystem.model.Attendance;
import com.priacc.hrsystem.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/attendance")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Attendance", description = "Attendance management API")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
    @Operation(summary = "Get all attendance records", description = "Retrieve all attendance records (Admin, HR, and Managers only)")
    public ResponseEntity<List<AttendanceDto>> getAllAttendance() {
        List<Attendance> attendances = attendanceService.getAllAttendance();
        List<AttendanceDto> attendanceDtos = attendances.stream()
                .map(AttendanceDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(attendanceDtos);
    }

    @GetMapping("/paged")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
    @Operation(summary = "Get all attendance records with pagination", description = "Retrieve all attendance records with pagination (Admin, HR, and Managers only)")
    public ResponseEntity<Page<AttendanceDto>> getAllAttendance(Pageable pageable) {
        Page<Attendance> attendances = attendanceService.getAllAttendance(pageable);
        Page<AttendanceDto> attendanceDtos = attendances.map(AttendanceDto::fromEntity);
        return ResponseEntity.ok(attendanceDtos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER') or @userSecurity.isCurrentUser(#id)")
    @Operation(summary = "Get attendance by ID", description = "Retrieve an attendance record by ID (Admin, HR, Managers, or the employee themselves)")
    public ResponseEntity<AttendanceDto> getAttendanceById(@PathVariable Long id) {
        Attendance attendance = attendanceService.getAttendanceById(id);
        return ResponseEntity.ok(AttendanceDto.fromEntity(attendance));
    }

    @GetMapping("/employee/{employeeId}/date/{date}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER') or @userSecurity.isCurrentUser(#employeeId)")
    @Operation(summary = "Get attendance by employee and date", description = "Retrieve an attendance record for an employee on a specific date (Admin, HR, Managers, or the employee themselves)")
    public ResponseEntity<AttendanceDto> getAttendanceByEmployeeAndDate(
            @PathVariable Long employeeId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Attendance attendance = attendanceService.getAttendanceByEmployeeAndDate(employeeId, date);
        return ResponseEntity.ok(AttendanceDto.fromEntity(attendance));
    }

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER') or @userSecurity.isCurrentUser(#employeeId)")
    @Operation(summary = "Get attendance by employee", description = "Retrieve all attendance records for an employee (Admin, HR, Managers, or the employee themselves)")
    public ResponseEntity<List<AttendanceDto>> getAttendanceByEmployee(@PathVariable Long employeeId) {
        List<Attendance> attendances = attendanceService.getAttendanceByEmployee(employeeId);
        List<AttendanceDto> attendanceDtos = attendances.stream()
                .map(AttendanceDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(attendanceDtos);
    }

    @GetMapping("/employee/{employeeId}/date-range")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER') or @userSecurity.isCurrentUser(#employeeId)")
    @Operation(summary = "Get attendance by employee and date range", description = "Retrieve all attendance records for an employee within a date range (Admin, HR, Managers, or the employee themselves)")
    public ResponseEntity<List<AttendanceDto>> getAttendanceByEmployeeAndDateRange(
            @PathVariable Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Attendance> attendances = attendanceService.getAttendanceByEmployeeAndDateRange(employeeId, startDate, endDate);
        List<AttendanceDto> attendanceDtos = attendances.stream()
                .map(AttendanceDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(attendanceDtos);
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
    @Operation(summary = "Get attendance by date range", description = "Retrieve all attendance records within a date range (Admin, HR, and Managers only)")
    public ResponseEntity<List<AttendanceDto>> getAttendanceByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Attendance> attendances = attendanceService.getAttendanceByDateRange(startDate, endDate);
        List<AttendanceDto> attendanceDtos = attendances.stream()
                .map(AttendanceDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(attendanceDtos);
    }

    @GetMapping("/department/{departmentId}/date/{date}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
    @Operation(summary = "Get attendance by department and date", description = "Retrieve all attendance records for a department on a specific date (Admin, HR, and Managers only)")
    public ResponseEntity<List<AttendanceDto>> getAttendanceByDepartmentAndDate(
            @PathVariable Long departmentId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Attendance> attendances = attendanceService.getAttendanceByDepartmentAndDate(departmentId, date);
        List<AttendanceDto> attendanceDtos = attendances.stream()
                .map(AttendanceDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(attendanceDtos);
    }

    @GetMapping("/department/{departmentId}/date-range")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
    @Operation(summary = "Get attendance by department and date range", description = "Retrieve all attendance records for a department within a date range (Admin, HR, and Managers only)")
    public ResponseEntity<List<AttendanceDto>> getAttendanceByDepartmentAndDateRange(
            @PathVariable Long departmentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Attendance> attendances = attendanceService.getAttendanceByDepartmentAndDateRange(departmentId, startDate, endDate);
        List<AttendanceDto> attendanceDtos = attendances.stream()
                .map(AttendanceDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(attendanceDtos);
    }

    @GetMapping("/count/employee/{employeeId}/status/{status}/date-range")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER') or @userSecurity.isCurrentUser(#employeeId)")
    @Operation(summary = "Count attendance by employee, status, and date range", description = "Count attendance records for an employee with a specific status within a date range (Admin, HR, Managers, or the employee themselves)")
    public ResponseEntity<Map<String, Long>> countAttendanceByEmployeeAndStatusAndDateRange(
            @PathVariable Long employeeId,
            @PathVariable String status,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Long count = attendanceService.countAttendanceByEmployeeAndStatusAndDateRange(employeeId, status, startDate, endDate);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @PostMapping("/check-in")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Check in", description = "Record a check-in (All authenticated users)")
    public ResponseEntity<AttendanceDto> checkIn(@Valid @RequestBody AttendanceDto attendanceDto) {
        Attendance attendance = attendanceService.checkIn(attendanceDto);
        return new ResponseEntity<>(AttendanceDto.fromEntity(attendance), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/check-out")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN') or @userSecurity.isCurrentUser(#id)")
    @Operation(summary = "Check out", description = "Record a check-out (Admin, HR, or the employee themselves)")
    public ResponseEntity<AttendanceDto> checkOut(@PathVariable Long id, @Valid @RequestBody AttendanceDto attendanceDto) {
        Attendance attendance = attendanceService.checkOut(id, attendanceDto);
        return ResponseEntity.ok(AttendanceDto.fromEntity(attendance));
    }

    @PutMapping("/{id}/start-break")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN') or @userSecurity.isCurrentUser(#id)")
    @Operation(summary = "Start break", description = "Record the start of a break (Admin, HR, or the employee themselves)")
    public ResponseEntity<AttendanceDto> startBreak(@PathVariable Long id) {
        Attendance attendance = attendanceService.startBreak(id);
        return ResponseEntity.ok(AttendanceDto.fromEntity(attendance));
    }

    @PutMapping("/{id}/end-break")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN') or @userSecurity.isCurrentUser(#id)")
    @Operation(summary = "End break", description = "Record the end of a break (Admin, HR, or the employee themselves)")
    public ResponseEntity<AttendanceDto> endBreak(@PathVariable Long id) {
        Attendance attendance = attendanceService.endBreak(id);
        return ResponseEntity.ok(AttendanceDto.fromEntity(attendance));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
    @Operation(summary = "Update attendance", description = "Update an attendance record (Admin and HR only)")
    public ResponseEntity<AttendanceDto> updateAttendance(@PathVariable Long id, @Valid @RequestBody AttendanceDto attendanceDto) {
        Attendance attendance = attendanceService.updateAttendance(id, attendanceDto);
        return ResponseEntity.ok(AttendanceDto.fromEntity(attendance));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete attendance", description = "Delete an attendance record (Admin only)")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }
}