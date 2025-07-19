package com.priacc.hrsystem.controller;

import com.priacc.hrsystem.dto.EmployeeDto;
import com.priacc.hrsystem.model.Employee;
import com.priacc.hrsystem.service.EmployeeService;
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
@RequestMapping("/api/employees")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Employees", description = "Employee management API")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
    @Operation(summary = "Get all employees", description = "Retrieve all employees (Admin, HR, and Managers only)")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        List<EmployeeDto> employeeDtos = employees.stream()
                .map(EmployeeDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(employeeDtos);
    }

    @GetMapping("/paged")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
    @Operation(summary = "Get all employees with pagination", description = "Retrieve all employees with pagination (Admin, HR, and Managers only)")
    public ResponseEntity<Page<EmployeeDto>> getAllEmployees(Pageable pageable) {
        Page<Employee> employees = employeeService.getAllEmployees(pageable);
        Page<EmployeeDto> employeeDtos = employees.map(EmployeeDto::fromEntity);
        return ResponseEntity.ok(employeeDtos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER') or @userSecurity.isCurrentUser(#id) or @userSecurity.isManager(#id)")
    @Operation(summary = "Get employee by ID", description = "Retrieve an employee by ID (Admin, HR, Managers, the employee themselves, or their manager)")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(EmployeeDto.fromEntity(employee));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN') or @userSecurity.isCurrentUser(#userId)")
    @Operation(summary = "Get employee by user ID", description = "Retrieve an employee by user ID (Admin, HR, or the user themselves)")
    public ResponseEntity<EmployeeDto> getEmployeeByUserId(@PathVariable Long userId) {
        Employee employee = employeeService.getEmployeeByUserId(userId);
        return ResponseEntity.ok(EmployeeDto.fromEntity(employee));
    }

    @GetMapping("/department/{departmentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
    @Operation(summary = "Get employees by department", description = "Retrieve all employees in a department (Admin, HR, and Managers only)")
    public ResponseEntity<List<EmployeeDto>> getEmployeesByDepartment(@PathVariable Long departmentId) {
        List<Employee> employees = employeeService.getEmployeesByDepartment(departmentId);
        List<EmployeeDto> employeeDtos = employees.stream()
                .map(EmployeeDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(employeeDtos);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
    @Operation(summary = "Search employees", description = "Search employees by keyword (Admin, HR, and Managers only)")
    public ResponseEntity<List<EmployeeDto>> searchEmployees(@RequestParam String keyword) {
        List<Employee> employees = employeeService.searchEmployees(keyword);
        List<EmployeeDto> employeeDtos = employees.stream()
                .map(EmployeeDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(employeeDtos);
    }

    @GetMapping("/join-date")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
    @Operation(summary = "Get employees by join date range", description = "Retrieve employees who joined between the specified dates (Admin and HR only)")
    public ResponseEntity<List<EmployeeDto>> getEmployeesByJoinDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Employee> employees = employeeService.getEmployeesByJoinDateRange(startDate, endDate);
        List<EmployeeDto> employeeDtos = employees.stream()
                .map(EmployeeDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(employeeDtos);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
    @Operation(summary = "Get employees by status", description = "Retrieve employees by status (Admin and HR only)")
    public ResponseEntity<List<EmployeeDto>> getEmployeesByStatus(@PathVariable String status) {
        List<Employee> employees = employeeService.getEmployeesByStatus(status);
        List<EmployeeDto> employeeDtos = employees.stream()
                .map(EmployeeDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(employeeDtos);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
    @Operation(summary = "Create a new employee", description = "Create a new employee (Admin and HR only)")
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        Employee employee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(EmployeeDto.fromEntity(employee), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
    @Operation(summary = "Update an employee", description = "Update an employee (Admin and HR only)")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDto employeeDto) {
        Employee employee = employeeService.updateEmployee(id, employeeDto);
        return ResponseEntity.ok(EmployeeDto.fromEntity(employee));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete an employee", description = "Delete an employee (Admin only)")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}