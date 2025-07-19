package com.priacc.hrsystem.controller;

import com.priacc.hrsystem.dto.LeaveDto;
import com.priacc.hrsystem.model.Leave;
import com.priacc.hrsystem.service.LeaveService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/leaves")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Leaves", description = "Leave management API")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
    @Operation(summary = "Get all leaves", description = "Retrieve all leaves (Admin, HR, and Managers only)")
    public ResponseEntity<List<LeaveDto>> getAllLeaves() {
        List<Leave> leaves = leaveService.getAllLeaves();
        List<LeaveDto> leaveDtos = leaves.stream()
                .map(LeaveDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(leaveDtos);
    }

    @GetMapping("/paged")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
    @Operation(summary = "Get all leaves with pagination", description = "Retrieve all leaves with pagination (Admin, HR, and Managers only)")
    public ResponseEntity<Page<LeaveDto>> getAllLeaves(Pageable pageable) {
        Page<Leave> leaves = leaveService.getAllLeaves(pageable);
        Page<LeaveDto> leaveDtos = leaves.map(LeaveDto::fromEntity);
        return ResponseEntity.ok(leaveDtos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER') or @userSecurity.isCurrentUser(#id) or @userSecurity.isManager(#id)")
    @Operation(summary = "Get leave by ID", description = "Retrieve a leave by ID (Admin, HR, Managers, the employee themselves, or their manager)")
    public ResponseEntity<LeaveDto> getLeaveById(@PathVariable Long id) {
        Leave leave = leaveService.getLeaveById(id);
        return ResponseEntity.ok(LeaveDto.fromEntity(leave));
    }

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER') or @userSecurity.isCurrentUser(#employeeId) or @userSecurity.isManager(#employeeId)")
    @Operation(summary = "Get leaves by employee", description = "Retrieve all leaves for an employee (Admin, HR, Managers, the employee themselves, or their manager)")
    public ResponseEntity<List<LeaveDto>> getLeavesByEmployee(@PathVariable Long employeeId) {
        List<Leave> leaves = leaveService.getLeavesByEmployee(employeeId);
        List<LeaveDto> leaveDtos = leaves.stream()
                .map(LeaveDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(leaveDtos);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
    @Operation(summary = "Get leaves by status", description = "Retrieve all leaves with a specific status (Admin, HR, and Managers only)")
    public ResponseEntity<List<LeaveDto>> getLeavesByStatus(@PathVariable String status) {
        List<Leave> leaves = leaveService.getLeavesByStatus(status);
        List<LeaveDto> leaveDtos = leaves.stream()
                .map(LeaveDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(leaveDtos);
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
    @Operation(summary = "Get leaves by date range", description = "Retrieve all leaves within a date range (Admin, HR, and Managers only)")
    public ResponseEntity<List<LeaveDto>> getLeavesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Leave> leaves = leaveService.getLeavesByDateRange(startDate, endDate);
        List<LeaveDto> leaveDtos = leaves.stream()
                .map(LeaveDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(leaveDtos);
    }

    @GetMapping("/department/{departmentId}/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
    @Operation(summary = "Get leaves by department and status", description = "Retrieve all leaves for a department with a specific status (Admin, HR, and Managers only)")
    public ResponseEntity<List<LeaveDto>> getLeavesByDepartmentAndStatus(
            @PathVariable Long departmentId,
            @PathVariable String status) {
        List<Leave> leaves = leaveService.getLeavesByDepartmentAndStatus(departmentId, status);
        List<LeaveDto> leaveDtos = leaves.stream()
                .map(LeaveDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(leaveDtos);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create a new leave request", description = "Create a new leave request (All authenticated users)")
    public ResponseEntity<LeaveDto> createLeave(@Valid @RequestBody LeaveDto leaveDto) {
        Leave leave = leaveService.createLeave(leaveDto);
        return new ResponseEntity<>(LeaveDto.fromEntity(leave), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN') or @userSecurity.isCurrentUser(#id)")
    @Operation(summary = "Update a leave request", description = "Update a leave request (Admin, HR, or the employee themselves)")
    public ResponseEntity<LeaveDto> updateLeave(@PathVariable Long id, @Valid @RequestBody LeaveDto leaveDto) {
        Leave leave = leaveService.updateLeave(id, leaveDto);
        return ResponseEntity.ok(LeaveDto.fromEntity(leave));
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
    @Operation(summary = "Approve a leave request", description = "Approve a leave request (Admin, HR, and Managers only)")
    public ResponseEntity<LeaveDto> approveLeave(
            @PathVariable Long id,
            @RequestParam Long approverId,
            @RequestParam(required = false) String comments) {
        Leave leave = leaveService.approveLeave(id, approverId, comments);
        return ResponseEntity.ok(LeaveDto.fromEntity(leave));
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
    @Operation(summary = "Reject a leave request", description = "Reject a leave request (Admin, HR, and Managers only)")
    public ResponseEntity<LeaveDto> rejectLeave(
            @PathVariable Long id,
            @RequestParam Long approverId,
            @RequestParam(required = false) String comments) {
        Leave leave = leaveService.rejectLeave(id, approverId, comments);
        return ResponseEntity.ok(LeaveDto.fromEntity(leave));
    }

    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN') or @userSecurity.isCurrentUser(#id)")
    @Operation(summary = "Cancel a leave request", description = "Cancel a leave request (Admin, HR, or the employee themselves)")
    public ResponseEntity<LeaveDto> cancelLeave(@PathVariable Long id) {
        Leave leave = leaveService.cancelLeave(id);
        return ResponseEntity.ok(LeaveDto.fromEntity(leave));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a leave request", description = "Delete a leave request (Admin only)")
    public ResponseEntity<Void> deleteLeave(@PathVariable Long id) {
        leaveService.deleteLeave(id);
        return ResponseEntity.noContent().build();
    }
}