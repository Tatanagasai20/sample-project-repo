package com.priacc.hrsystem.service;

import com.priacc.hrsystem.dto.DepartmentDto;
import com.priacc.hrsystem.exception.ResourceNotFoundException;
import com.priacc.hrsystem.model.Department;
import com.priacc.hrsystem.model.Employee;
import com.priacc.hrsystem.repository.DepartmentRepository;
import com.priacc.hrsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Page<Department> getAllDepartments(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
    }

    public Department getDepartmentByName(String name) {
        return departmentRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with name: " + name));
    }

    @Transactional
    public Department createDepartment(DepartmentDto departmentDto) {
        if (departmentRepository.existsByName(departmentDto.getName())) {
            throw new RuntimeException("Department with name " + departmentDto.getName() + " already exists");
        }

        Department department = new Department();
        department.setName(departmentDto.getName());
        department.setDescription(departmentDto.getDescription());

        // Set department head if provided
        if (departmentDto.getDepartmentHeadId() != null) {
            Employee departmentHead = employeeRepository.findById(departmentDto.getDepartmentHeadId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + departmentDto.getDepartmentHeadId()));
            department.setDepartmentHead(departmentHead);
        }

        return departmentRepository.save(department);
    }

    @Transactional
    public Department updateDepartment(Long id, DepartmentDto departmentDto) {
        Department department = getDepartmentById(id);

        // Check if name is being changed and if it's already taken
        if (!department.getName().equals(departmentDto.getName()) && departmentRepository.existsByName(departmentDto.getName())) {
            throw new RuntimeException("Department with name " + departmentDto.getName() + " already exists");
        }

        department.setName(departmentDto.getName());
        department.setDescription(departmentDto.getDescription());

        // Update department head if provided
        if (departmentDto.getDepartmentHeadId() != null) {
            Employee departmentHead = employeeRepository.findById(departmentDto.getDepartmentHeadId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + departmentDto.getDepartmentHeadId()));
            department.setDepartmentHead(departmentHead);
        } else if (departmentDto.getDepartmentHeadId() == null && department.getDepartmentHead() != null) {
            // Remove department head if null is provided
            department.setDepartmentHead(null);
        }

        return departmentRepository.save(department);
    }

    @Transactional
    public void deleteDepartment(Long id) {
        Department department = getDepartmentById(id);

        // Check if department has employees
        if (!department.getEmployees().isEmpty()) {
            throw new RuntimeException("Cannot delete department with associated employees");
        }

        departmentRepository.delete(department);
    }
}