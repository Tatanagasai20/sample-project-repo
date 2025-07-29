package com.priacc.hrsystem.service;

import com.priacc.hrsystem.dto.LeaveDto;
import com.priacc.hrsystem.exception.ResourceNotFoundException;
import com.priacc.hrsystem.model.Employee;
import com.priacc.hrsystem.model.Leave;
import com.priacc.hrsystem.model.User;
import com.priacc.hrsystem.repository.EmployeeRepository;
import com.priacc.hrsystem.repository.LeaveRepository;
import com.priacc.hrsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Leave> getAllLeaves() {
        return leaveRepository.findAll();
    }

    public Page<Leave> getAllLeaves(Pageable pageable) {
        return leaveRepository.findAll(pageable);
    }

    public Leave getLeaveById(Long id) {
        return leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not found with id: " + id));
    }

    public List<Leave> getLeavesByEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        
        return leaveRepository.findByEmployee(employee);
    }

    public Page<Leave> getLeavesByEmployee(Long employeeId, Pageable pageable) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        
        return leaveRepository.findByEmployee(employee, pageable);
    }

    public List<Leave> getLeavesByEmployeeAndStatus(Long employeeId, Leave.LeaveStatus status) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        
        return leaveRepository.findByEmployeeAndStatus(employee, status);
    }

    public List<Leave> getLeavesByStatus(Leave.LeaveStatus status) {
        return leaveRepository.findByStatus(status);
    }
    
    // Overloaded method for String status
    public List<Leave> getLeavesByStatus(String status) {
        Leave.LeaveStatus leaveStatus = Leave.LeaveStatus.valueOf(status.toUpperCase());
        return getLeavesByStatus(leaveStatus);
    }

    public List<Leave> getLeavesByDateRange(LocalDate startDate, LocalDate endDate) {
        return leaveRepository.findByDateRange(startDate, endDate);
    }

    public List<Leave> getLeavesByEmployeeAndDateRange(Long employeeId, LocalDate startDate, LocalDate endDate) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        
        return leaveRepository.findByEmployeeAndDateRange(employee, startDate, endDate);
    }

    public List<Leave> getLeavesByDepartmentAndStatus(Long departmentId, Leave.LeaveStatus status) {
        return leaveRepository.findByDepartmentAndStatus(departmentId, status);
    }
    
    // Overloaded method for String status
    public List<Leave> getLeavesByDepartmentAndStatus(Long departmentId, String status) {
        Leave.LeaveStatus leaveStatus = Leave.LeaveStatus.valueOf(status.toUpperCase());
        return getLeavesByDepartmentAndStatus(departmentId, leaveStatus);
    }

    @Transactional
    public Leave createLeave(LeaveDto leaveDto) {
        Employee employee = employeeRepository.findById(leaveDto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + leaveDto.getEmployeeId()));
        
        // Validate leave dates
        if (leaveDto.getStartDate().isAfter(leaveDto.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        
        // Check for overlapping leaves
        List<Leave> overlappingLeaves = leaveRepository.findByEmployeeAndDateRange(
                employee, 
                leaveDto.getStartDate(), 
                leaveDto.getEndDate()
        );
        
        if (!overlappingLeaves.isEmpty()) {
            throw new IllegalArgumentException("Employee already has leave scheduled during this period");
        }
        
        Leave leave = new Leave();
        leave.setEmployee(employee);
        leave.setLeaveType(leaveDto.getLeaveType());
        leave.setStartDate(leaveDto.getStartDate());
        leave.setEndDate(leaveDto.getEndDate());
        leave.setReason(leaveDto.getReason());
        leave.setStatus(Leave.LeaveStatus.PENDING); // Default status is PENDING
        leave.setCreatedAt(LocalDateTime.now());
        
        // Calculate number of days
        long days = ChronoUnit.DAYS.between(leaveDto.getStartDate(), leaveDto.getEndDate()) + 1;
        leave.setNumberOfDays((int) days);
        
        return leaveRepository.save(leave);
    }

    @Transactional
    public Leave updateLeave(Long id, LeaveDto leaveDto) {
        Leave leave = getLeaveById(id);
        
        // Only allow updates if leave is still pending
        if (leave.getStatus() != Leave.LeaveStatus.PENDING) {
            throw new IllegalStateException("Cannot update leave that is not in PENDING status");
        }
        
        // Validate leave dates
        if (leaveDto.getStartDate().isAfter(leaveDto.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        
        // Check for overlapping leaves (excluding this leave)
        List<Leave> overlappingLeaves = leaveRepository.findByEmployeeAndDateRange(
                leave.getEmployee(), 
                leaveDto.getStartDate(), 
                leaveDto.getEndDate()
        );
        
        overlappingLeaves.removeIf(l -> l.getId().equals(id)); // Remove this leave from the list
        
        if (!overlappingLeaves.isEmpty()) {
            throw new IllegalArgumentException("Employee already has leave scheduled during this period");
        }
        
        leave.setLeaveType(leaveDto.getLeaveType());
        leave.setStartDate(leaveDto.getStartDate());
        leave.setEndDate(leaveDto.getEndDate());
        leave.setReason(leaveDto.getReason());
        leave.setUpdatedAt(LocalDateTime.now());
        
        // Recalculate number of days
        long days = ChronoUnit.DAYS.between(leaveDto.getStartDate(), leaveDto.getEndDate()) + 1;
        leave.setNumberOfDays((int) days);
        
        return leaveRepository.save(leave);
    }

    @Transactional
    public Leave approveLeave(Long id, Long approverId, String comments) {
        Leave leave = getLeaveById(id);
        
        // Only allow approval if leave is pending
        if (leave.getStatus() != Leave.LeaveStatus.PENDING) {
            throw new IllegalStateException("Cannot approve leave that is not in PENDING status");
        }
        
        // Get approver by ID
        Employee approver = employeeRepository.findById(approverId)
                .orElseThrow(() -> new ResourceNotFoundException("Approver not found with id: " + approverId));
        
        leave.setStatus(Leave.LeaveStatus.APPROVED);
        leave.setApprovedBy(approver);
        leave.setApprovedAt(LocalDateTime.now());
        leave.setComments(comments);
        leave.setUpdatedAt(LocalDateTime.now());
        
        return leaveRepository.save(leave);
    }

    @Transactional
    public Leave rejectLeave(Long id, Long approverId, String comments) {
        Leave leave = getLeaveById(id);
        
        // Only allow rejection if leave is pending
        if (leave.getStatus() != Leave.LeaveStatus.PENDING) {
            throw new IllegalStateException("Cannot reject leave that is not in PENDING status");
        }
        
        // Get approver by ID
        Employee approver = employeeRepository.findById(approverId)
                .orElseThrow(() -> new ResourceNotFoundException("Approver not found with id: " + approverId));
        
        leave.setStatus(Leave.LeaveStatus.REJECTED);
        leave.setApprovedBy(approver);
        leave.setApprovedAt(LocalDateTime.now());
        leave.setComments(comments);
        leave.setUpdatedAt(LocalDateTime.now());
        
        return leaveRepository.save(leave);
    }

    @Transactional
    public Leave cancelLeave(Long id) {
        Leave leave = getLeaveById(id);
        
        // Only allow cancellation if leave is not already cancelled or taken
        if (leave.getStatus() == Leave.LeaveStatus.CANCELLED || leave.getStatus() == Leave.LeaveStatus.TAKEN) {
            throw new IllegalStateException("Cannot cancel leave that is already cancelled or taken");
        }
        
        leave.setStatus(Leave.LeaveStatus.CANCELLED);
        leave.setUpdatedAt(LocalDateTime.now());
        
        return leaveRepository.save(leave);
    }

    @Transactional
    public void deleteLeave(Long id) {
        Leave leave = getLeaveById(id);
        leaveRepository.delete(leave);
    }
}