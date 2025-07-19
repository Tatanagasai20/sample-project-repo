package com.priacc.hrsystem.repository;

import com.priacc.hrsystem.model.Employee;
import com.priacc.hrsystem.model.Leave;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {
    
    List<Leave> findByEmployee(Employee employee);
    
    Page<Leave> findByEmployee(Employee employee, Pageable pageable);
    
    List<Leave> findByEmployeeAndStatus(Employee employee, Leave.LeaveStatus status);
    
    List<Leave> findByStatus(Leave.LeaveStatus status);
    
    @Query("SELECT l FROM Leave l WHERE l.employee = :employee AND l.startDate >= :startDate AND l.endDate <= :endDate")
    List<Leave> findByEmployeeAndDateRange(Employee employee, LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT l FROM Leave l WHERE l.startDate >= :startDate AND l.endDate <= :endDate")
    List<Leave> findByDateRange(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT l FROM Leave l WHERE l.employee.department.id = :departmentId AND l.status = :status")
    List<Leave> findByDepartmentAndStatus(Long departmentId, Leave.LeaveStatus status);
}