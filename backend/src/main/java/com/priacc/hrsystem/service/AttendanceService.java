package com.priacc.hrsystem.service;

import com.priacc.hrsystem.dto.AttendanceDto;
import com.priacc.hrsystem.exception.ResourceNotFoundException;
import com.priacc.hrsystem.model.Attendance;
import com.priacc.hrsystem.model.Employee;
import com.priacc.hrsystem.repository.AttendanceRepository;
import com.priacc.hrsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }
    
    // Missing method for controller
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }
    
    // Missing method for controller
    public Page<Attendance> getAllAttendance(Pageable pageable) {
        return attendanceRepository.findAll(pageable);
    }

    public Page<Attendance> getAllAttendances(Pageable pageable) {
        return attendanceRepository.findAll(pageable);
    }

    public Attendance getAttendanceById(Long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with id: " + id));
    }

    public List<Attendance> getAttendancesByEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        
        return attendanceRepository.findByEmployee(employee);
    }

    public Page<Attendance> getAttendancesByEmployee(Long employeeId, Pageable pageable) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        
        return attendanceRepository.findByEmployee(employee, pageable);
    }

    public List<Attendance> getAttendancesByEmployeeAndDateRange(Long employeeId, LocalDate startDate, LocalDate endDate) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        
        return attendanceRepository.findByEmployeeAndDateBetween(employee, startDate, endDate);
    }

    public List<Attendance> getAttendancesByDateRange(LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByDateBetween(startDate, endDate);
    }

    public List<Attendance> getAttendancesByDepartmentAndDate(Long departmentId, LocalDate date) {
        return attendanceRepository.findByDepartmentAndDate(departmentId, date);
    }

    public List<Attendance> getAttendancesByDepartmentAndDateRange(Long departmentId, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByDepartmentAndDateBetween(departmentId, startDate, endDate);
    }
    
    // Alternative method names for controller compatibility
    public List<Attendance> getAttendanceByEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        return attendanceRepository.findByEmployee(employee);
    }
    
    public List<Attendance> getAttendanceByEmployeeAndDateRange(Long employeeId, LocalDate startDate, LocalDate endDate) {
        return getAttendancesByEmployeeAndDateRange(employeeId, startDate, endDate);
    }
    
    public List<Attendance> getAttendanceByDateRange(LocalDate startDate, LocalDate endDate) {
        return getAttendancesByDateRange(startDate, endDate);
    }
    
    public List<Attendance> getAttendanceByDepartmentAndDate(Long departmentId, LocalDate date) {
        return getAttendancesByDepartmentAndDate(departmentId, date);
    }
    
    public List<Attendance> getAttendanceByDepartmentAndDateRange(Long departmentId, LocalDate startDate, LocalDate endDate) {
        return getAttendancesByDepartmentAndDateRange(departmentId, startDate, endDate);
    }
    
    public long countAttendanceByEmployeeAndStatusAndDateRange(Long employeeId, String status, LocalDate startDate, LocalDate endDate) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        return attendanceRepository.countByEmployeeAndStatusAndDateBetween(employee, Attendance.AttendanceStatus.valueOf(status.toUpperCase()), startDate, endDate);
    }
    
    public Attendance getAttendanceByEmployeeAndDate(Long employeeId, LocalDate date) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        return attendanceRepository.findByEmployeeAndDate(employee, date)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found for employee " + employeeId + " on date " + date));
    }

    @Transactional
    public Attendance checkIn(AttendanceDto attendanceDto) {
        Employee employee = employeeRepository.findById(attendanceDto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + attendanceDto.getEmployeeId()));
        
        LocalDate today = LocalDate.now();
        
        // Check if attendance record already exists for today
        Optional<Attendance> existingAttendance = attendanceRepository.findByEmployeeAndDate(employee, today);
        
        if (existingAttendance.isPresent()) {
            Attendance attendance = existingAttendance.get();
            
            // If already checked in, throw exception
            if (attendance.getCheckInTime() != null) {
                throw new IllegalStateException("Employee has already checked in today");
            }
            
            // Update existing record with check-in information
            attendance.setCheckInTime(LocalTime.now());
            attendance.setStatus(Attendance.AttendanceStatus.PRESENT);
            attendance.setIpAddress(attendanceDto.getIpAddress());
            attendance.setLocation(attendanceDto.getLocation());
            
            return attendanceRepository.save(attendance);
        } else {
            // Create new attendance record
            Attendance attendance = new Attendance();
            attendance.setEmployee(employee);
            attendance.setDate(today);
            attendance.setCheckInTime(LocalTime.now());
            attendance.setStatus(Attendance.AttendanceStatus.PRESENT);
            attendance.setIpAddress(attendanceDto.getIpAddress());
            attendance.setLocation(attendanceDto.getLocation());
            
            return attendanceRepository.save(attendance);
        }
    }

    @Transactional
    public Attendance checkOut(AttendanceDto attendanceDto) {
        Employee employee = employeeRepository.findById(attendanceDto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + attendanceDto.getEmployeeId()));
        
        LocalDate today = LocalDate.now();
        
        // Find today's attendance record
        Attendance attendance = attendanceRepository.findByEmployeeAndDate(employee, today)
                .orElseThrow(() -> new IllegalStateException("No check-in record found for today"));
        
        // If already checked out, throw exception
        if (attendance.getCheckOutTime() != null) {
            throw new IllegalStateException("Employee has already checked out today");
        }
        
        // If not checked in, throw exception
        if (attendance.getCheckInTime() == null) {
            throw new IllegalStateException("Employee has not checked in today");
        }
        
        // Update attendance record with check-out information
        attendance.setCheckOutTime(LocalTime.now());
        attendance.setIpAddress(attendanceDto.getIpAddress());
        attendance.setLocation(attendanceDto.getLocation());
        
        // Calculate work hours
        long workMinutes = Duration.between(attendance.getCheckInTime(), attendance.getCheckOutTime()).toMinutes();
        if (attendance.getBreakStartTime() != null && attendance.getBreakEndTime() != null) {
            long breakMinutes = Duration.between(attendance.getBreakStartTime(), attendance.getBreakEndTime()).toMinutes();
            workMinutes -= breakMinutes;
        }
        double workHours = workMinutes / 60.0;
        attendance.setWorkHours(workHours);
        
        return attendanceRepository.save(attendance);
    }
    
    // Overloaded method for controller compatibility
    @Transactional
    public Attendance checkOut(Long attendanceId, AttendanceDto attendanceDto) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with id: " + attendanceId));
        
        // If already checked out, throw exception
        if (attendance.getCheckOutTime() != null) {
            throw new IllegalStateException("Employee has already checked out today");
        }
        
        // If not checked in, throw exception
        if (attendance.getCheckInTime() == null) {
            throw new IllegalStateException("Employee has not checked in today");
        }
        
        // Update attendance record with check-out information
        attendance.setCheckOutTime(LocalTime.now());
        if (attendanceDto.getIpAddress() != null) {
            attendance.setIpAddress(attendanceDto.getIpAddress());
        }
        if (attendanceDto.getLocation() != null) {
            attendance.setLocation(attendanceDto.getLocation());
        }
        
        // Calculate work hours
        long workMinutes = Duration.between(attendance.getCheckInTime(), attendance.getCheckOutTime()).toMinutes();
        if (attendance.getBreakStartTime() != null && attendance.getBreakEndTime() != null) {
            long breakMinutes = Duration.between(attendance.getBreakStartTime(), attendance.getBreakEndTime()).toMinutes();
            workMinutes -= breakMinutes;
        }
        double workHours = workMinutes / 60.0;
        attendance.setWorkHours(workHours);
        
        return attendanceRepository.save(attendance);
    }

    @Transactional
    public Attendance startBreak(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        
        LocalDate today = LocalDate.now();
        
        // Find today's attendance record
        Attendance attendance = attendanceRepository.findByEmployeeAndDate(employee, today)
                .orElseThrow(() -> new IllegalStateException("No check-in record found for today"));
        
        // If not checked in, throw exception
        if (attendance.getCheckInTime() == null) {
            throw new IllegalStateException("Employee has not checked in today");
        }
        
        // If already on break, throw exception
        if (attendance.getBreakStartTime() != null && attendance.getBreakEndTime() == null) {
            throw new IllegalStateException("Employee is already on break");
        }
        
        // Update record with break start time
        attendance.setBreakStartTime(LocalTime.now());
        
        return attendanceRepository.save(attendance);
    }

    @Transactional
    public Attendance endBreak(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        
        LocalDate today = LocalDate.now();
        
        // Find today's attendance record
        Attendance attendance = attendanceRepository.findByEmployeeAndDate(employee, today)
                .orElseThrow(() -> new IllegalStateException("No check-in record found for today"));
        
        // If not on break, throw exception
        if (attendance.getBreakStartTime() == null) {
            throw new IllegalStateException("Employee has not started a break");
        }
        
        // If break already ended, throw exception
        if (attendance.getBreakEndTime() != null) {
            throw new IllegalStateException("Employee's break has already ended");
        }
        
        // Update record with break end time
        attendance.setBreakEndTime(LocalTime.now());
        
        return attendanceRepository.save(attendance);
    }

    @Transactional
    public Attendance updateAttendance(Long id, AttendanceDto attendanceDto) {
        Attendance attendance = getAttendanceById(id);
        
        // Update status if provided
        if (attendanceDto.getStatus() != null) {
            attendance.setStatus(attendanceDto.getStatus());
        }
        
        // Update times if provided
        if (attendanceDto.getCheckInTime() != null) {
            attendance.setCheckInTime(attendanceDto.getCheckInTime());
        }
        
        if (attendanceDto.getCheckOutTime() != null) {
            attendance.setCheckOutTime(attendanceDto.getCheckOutTime());
        }
        
        if (attendanceDto.getBreakStartTime() != null) {
            attendance.setBreakStartTime(attendanceDto.getBreakStartTime());
        }
        
        if (attendanceDto.getBreakEndTime() != null) {
            attendance.setBreakEndTime(attendanceDto.getBreakEndTime());
        }
        
        // Update work hours if check-in and check-out times are present
        if (attendance.getCheckInTime() != null && attendance.getCheckOutTime() != null) {
            Duration workDuration = Duration.between(attendance.getCheckInTime(), attendance.getCheckOutTime());
            
            // Subtract break time if recorded
            if (attendance.getBreakStartTime() != null && attendance.getBreakEndTime() != null) {
                Duration breakDuration = Duration.between(attendance.getBreakStartTime(), attendance.getBreakEndTime());
                workDuration = workDuration.minus(breakDuration);
            }
            
            // Convert to hours and minutes
            long totalMinutes = workDuration.toMinutes();
            double hours = totalMinutes / 60.0;
            attendance.setWorkHours(hours);
        }
        
        // Update notes if provided
        if (attendanceDto.getNotes() != null) {
            attendance.setNotes(attendanceDto.getNotes());
        }
        
        return attendanceRepository.save(attendance);
    }

    @Transactional
    public void deleteAttendance(Long id) {
        Attendance attendance = getAttendanceById(id);
        attendanceRepository.delete(attendance);
    }

    public Long countAttendancesByEmployeeAndStatusAndDateRange(Long employeeId, Attendance.AttendanceStatus status, LocalDate startDate, LocalDate endDate) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        
        return attendanceRepository.countByEmployeeAndStatusAndDateBetween(employee, status, startDate, endDate);
    }
}