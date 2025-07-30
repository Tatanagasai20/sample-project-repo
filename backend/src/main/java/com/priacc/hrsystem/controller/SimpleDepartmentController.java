package com.priacc.hrsystem.controller;

import com.priacc.hrsystem.model.Department;
import com.priacc.hrsystem.repository.DepartmentRepository;
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
@RequestMapping("/api/simple/departments")
@Tag(name = "Simple Departments", description = "Simplified Department management API")
public class SimpleDepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping
    @Operation(summary = "Get all departments", description = "Retrieve all departments")
    public ResponseEntity<Map<String, Object>> getAllDepartments() {
        try {
            List<Department> departments = departmentRepository.findAll();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", departments.size());
            response.put("departments", departments);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("error", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get department by ID", description = "Retrieve a specific department")
    public ResponseEntity<Map<String, Object>> getDepartment(@PathVariable Long id) {
        try {
            Optional<Department> department = departmentRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            
            if (department.isPresent()) {
                response.put("success", true);
                response.put("department", department.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Department not found");
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
    @Operation(summary = "Get department count", description = "Get total number of departments")
    public ResponseEntity<Map<String, Object>> getDepartmentCount() {
        try {
            long count = departmentRepository.count();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("total_departments", count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("error", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
}