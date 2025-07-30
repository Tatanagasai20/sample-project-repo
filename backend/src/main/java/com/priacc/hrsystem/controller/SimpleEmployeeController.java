package com.priacc.hrsystem.controller;

import com.priacc.hrsystem.model.Employee;
import com.priacc.hrsystem.repository.EmployeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/simple/employees")
@Tag(name = "Simple Employees", description = "Simplified Employee management API")
public class SimpleEmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    @Operation(summary = "Get all employees", description = "Retrieve all employees")
    public ResponseEntity<Map<String, Object>> getAllEmployees() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", employees.size());
            response.put("employees", employees);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("error", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee by ID", description = "Retrieve a specific employee")
    public ResponseEntity<Map<String, Object>> getEmployee(@PathVariable Long id) {
        try {
            Optional<Employee> employee = employeeRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            
            if (employee.isPresent()) {
                response.put("success", true);
                response.put("employee", employee.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Employee not found");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("error", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @GetMapping("/count")
    @Operation(summary = "Get employee count", description = "Get total number of employees")
    public ResponseEntity<Map<String, Object>> getEmployeeCount() {
        try {
            long count = employeeRepository.count();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("total_employees", count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("error", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @GetMapping("/search")
    @Operation(summary = "Search employees", description = "Search employees by employee ID")
    public ResponseEntity<Map<String, Object>> searchEmployees(@RequestParam String employeeId) {
        try {
            Optional<Employee> employee = employeeRepository.findByEmployeeId(employeeId);
            Map<String, Object> response = new HashMap<>();
            
            if (employee.isPresent()) {
                response.put("success", true);
                response.put("employee", employee.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Employee not found with ID: " + employeeId);
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("error", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
}