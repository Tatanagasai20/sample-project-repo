package com.priacc.hrsystem.service;

import com.priacc.hrsystem.dto.EmployeeDto;
import com.priacc.hrsystem.exception.ResourceNotFoundException;
import com.priacc.hrsystem.model.Department;
import com.priacc.hrsystem.model.Employee;
import com.priacc.hrsystem.model.Position;
import com.priacc.hrsystem.model.User;
import com.priacc.hrsystem.repository.DepartmentRepository;
import com.priacc.hrsystem.repository.EmployeeRepository;
import com.priacc.hrsystem.repository.PositionRepository;
import com.priacc.hrsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PositionRepository positionRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Page<Employee> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    public Employee getEmployeeByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        return employeeRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for user with id: " + userId));
    }

    public List<Employee> getEmployeesByDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));
        
        return employeeRepository.findByDepartment(department);
    }

    public List<Employee> searchEmployees(String keyword) {
        return employeeRepository.searchByKeyword(keyword);
    }

    @Transactional
    public Employee createEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        
        // Set basic information
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setDateOfBirth(employeeDto.getDateOfBirth());
        employee.setGender(employeeDto.getGender());
        employee.setMaritalStatus(employeeDto.getMaritalStatus());
        employee.setNationality(employeeDto.getNationality());
        
        // Set contact information
        employee.setEmail(employeeDto.getEmail());
        employee.setPhone(employeeDto.getPhone());
        employee.setMobilePhone(employeeDto.getMobilePhone());
        
        // Set address if provided
        if (employeeDto.getAddress() != null) {
            employee.setAddress(employeeDto.getAddress());
        }
        
        // Set employment details
        if (employeeDto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(employeeDto.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + employeeDto.getDepartmentId()));
            employee.setDepartment(department);
        }
        
        if (employeeDto.getPositionId() != null) {
            Position position = positionRepository.findById(employeeDto.getPositionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Position not found with id: " + employeeDto.getPositionId()));
            employee.setPosition(position);
        }
        
        if (employeeDto.getManagerId() != null) {
            Employee manager = employeeRepository.findById(employeeDto.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found with id: " + employeeDto.getManagerId()));
            employee.setManager(manager);
        }
        
        employee.setEmployeeType(employeeDto.getEmployeeType());
        employee.setJoinDate(employeeDto.getJoinDate());
        employee.setStatus(employeeDto.getStatus());
        
        // Set salary information if provided
        if (employeeDto.getSalary() != null) {
            employee.setSalary(employeeDto.getSalary());
        }
        
        // Set bank details if provided
        if (employeeDto.getBankDetails() != null) {
            employee.setBankDetails(employeeDto.getBankDetails());
        }
        
        // Link to user if provided
        if (employeeDto.getUserId() != null) {
            User user = userRepository.findById(employeeDto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + employeeDto.getUserId()));
            employee.setUser(user);
            user.setEmployee(employee);
            userRepository.save(user);
        }
        
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee updateEmployee(Long id, EmployeeDto employeeDto) {
        Employee employee = getEmployeeById(id);
        
        // Update basic information
        if (employeeDto.getFirstName() != null) {
            employee.setFirstName(employeeDto.getFirstName());
        }
        if (employeeDto.getLastName() != null) {
            employee.setLastName(employeeDto.getLastName());
        }
        if (employeeDto.getDateOfBirth() != null) {
            employee.setDateOfBirth(employeeDto.getDateOfBirth());
        }
        if (employeeDto.getGender() != null) {
            employee.setGender(employeeDto.getGender());
        }
        if (employeeDto.getMaritalStatus() != null) {
            employee.setMaritalStatus(employeeDto.getMaritalStatus());
        }
        if (employeeDto.getNationality() != null) {
            employee.setNationality(employeeDto.getNationality());
        }
        
        // Update contact information
        if (employeeDto.getEmail() != null) {
            employee.setEmail(employeeDto.getEmail());
        }
        if (employeeDto.getPhone() != null) {
            employee.setPhone(employeeDto.getPhone());
        }
        if (employeeDto.getMobilePhone() != null) {
            employee.setMobilePhone(employeeDto.getMobilePhone());
        }
        
        // Update address if provided
        if (employeeDto.getAddress() != null) {
            employee.setAddress(employeeDto.getAddress());
        }
        
        // Update employment details
        if (employeeDto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(employeeDto.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + employeeDto.getDepartmentId()));
            employee.setDepartment(department);
        }
        
        if (employeeDto.getPositionId() != null) {
            Position position = positionRepository.findById(employeeDto.getPositionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Position not found with id: " + employeeDto.getPositionId()));
            employee.setPosition(position);
        }
        
        if (employeeDto.getManagerId() != null) {
            Employee manager = employeeRepository.findById(employeeDto.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found with id: " + employeeDto.getManagerId()));
            employee.setManager(manager);
        }
        
        if (employeeDto.getEmployeeType() != null) {
            employee.setEmployeeType(employeeDto.getEmployeeType());
        }
        if (employeeDto.getJoinDate() != null) {
            employee.setJoinDate(employeeDto.getJoinDate());
        }
        if (employeeDto.getStatus() != null) {
            employee.setStatus(employeeDto.getStatus());
        }
        
        // Update salary information if provided
        if (employeeDto.getSalary() != null) {
            employee.setSalary(employeeDto.getSalary());
        }
        
        // Update bank details if provided
        if (employeeDto.getBankDetails() != null) {
            employee.setBankDetails(employeeDto.getBankDetails());
        }
        
        // Update user link if provided
        if (employeeDto.getUserId() != null && (employee.getUser() == null || !employee.getUser().getId().equals(employeeDto.getUserId()))) {
            User user = userRepository.findById(employeeDto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + employeeDto.getUserId()));
            
            // If employee already has a user, unlink it
            if (employee.getUser() != null) {
                User oldUser = employee.getUser();
                oldUser.setEmployee(null);
                userRepository.save(oldUser);
            }
            
            employee.setUser(user);
            user.setEmployee(employee);
            userRepository.save(user);
        }
        
        return employeeRepository.save(employee);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        
        // If employee has a user, unlink it
        if (employee.getUser() != null) {
            User user = employee.getUser();
            user.setEmployee(null);
            userRepository.save(user);
        }
        
        employeeRepository.delete(employee);
    }

    public List<Employee> getEmployeesByJoinDateRange(LocalDate startDate, LocalDate endDate) {
        return employeeRepository.findByJoinDateBetween(startDate, endDate);
    }

    public List<Employee> getEmployeesByStatus(String status) {
        return employeeRepository.findByStatus(status);
    }
}