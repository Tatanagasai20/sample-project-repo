package com.priacc.hrsystem.controller;

import com.priacc.hrsystem.dto.DepartmentDto;
import com.priacc.hrsystem.model.Department;
import com.priacc.hrsystem.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/departments")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Departments", description = "Department management API")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all departments", description = "Retrieve all departments (All authenticated users)")
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        List<DepartmentDto> departmentDtos = departments.stream()
                .map(DepartmentDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(departmentDtos);
    }

    @GetMapping("/paged")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all departments with pagination", description = "Retrieve all departments with pagination (All authenticated users)")
    public ResponseEntity<Page<DepartmentDto>> getAllDepartments(Pageable pageable) {
        Page<Department> departments = departmentService.getAllDepartments(pageable);
        Page<DepartmentDto> departmentDtos = departments.map(DepartmentDto::fromEntity);
        return ResponseEntity.ok(departmentDtos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get department by ID", description = "Retrieve a department by ID (All authenticated users)")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long id) {
        Department department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(DepartmentDto.fromEntity(department));
    }

    @GetMapping("/name/{name}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get department by name", description = "Retrieve a department by name (All authenticated users)")
    public ResponseEntity<DepartmentDto> getDepartmentByName(@PathVariable String name) {
        Department department = departmentService.getDepartmentByName(name);
        return ResponseEntity.ok(DepartmentDto.fromEntity(department));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
    @Operation(summary = "Create a new department", description = "Create a new department (Admin and HR only)")
    public ResponseEntity<DepartmentDto> createDepartment(@Valid @RequestBody DepartmentDto departmentDto) {
        Department department = departmentService.createDepartment(departmentDto);
        return new ResponseEntity<>(DepartmentDto.fromEntity(department), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
    @Operation(summary = "Update a department", description = "Update a department (Admin and HR only)")
    public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentDto departmentDto) {
        Department department = departmentService.updateDepartment(id, departmentDto);
        return ResponseEntity.ok(DepartmentDto.fromEntity(department));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a department", description = "Delete a department (Admin only)")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}