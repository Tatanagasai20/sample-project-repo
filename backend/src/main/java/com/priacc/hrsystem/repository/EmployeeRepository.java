package com.priacc.hrsystem.repository;

import com.priacc.hrsystem.model.Department;
import com.priacc.hrsystem.model.Employee;
import com.priacc.hrsystem.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    Optional<Employee> findByEmployeeId(String employeeId);
    
    Optional<Employee> findByUser(User user);
    
    List<Employee> findByDepartment(Department department);
    
    Page<Employee> findByDepartment(Department department, Pageable pageable);
    
    @Query("SELECT e FROM Employee e WHERE e.status = :status")
    List<Employee> findByStatus(String status);
    
    @Query("SELECT e FROM Employee e WHERE e.hireDate BETWEEN :startDate AND :endDate")
    List<Employee> findByHireDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT e FROM Employee e WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(e.employeeId) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Employee> searchEmployees(String keyword, Pageable pageable);
    
    @Query("SELECT e FROM Employee e WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(e.employeeId) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Employee> searchByKeyword(String keyword);
}