package com.priacc.hrsystem.repository;

import com.priacc.hrsystem.model.Attendance;
import com.priacc.hrsystem.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    
    Optional<Attendance> findByEmployeeAndDate(Employee employee, LocalDate date);
    
    List<Attendance> findByEmployee(Employee employee);
    
    Page<Attendance> findByEmployee(Employee employee, Pageable pageable);
    
    List<Attendance> findByEmployeeAndDateBetween(Employee employee, LocalDate startDate, LocalDate endDate);
    
    List<Attendance> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT a FROM Attendance a WHERE a.employee.department.id = :departmentId AND a.date = :date")
    List<Attendance> findByDepartmentAndDate(Long departmentId, LocalDate date);
    
    @Query("SELECT a FROM Attendance a WHERE a.employee.department.id = :departmentId AND a.date BETWEEN :startDate AND :endDate")
    List<Attendance> findByDepartmentAndDateBetween(Long departmentId, LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.employee = :employee AND a.status = :status AND a.date BETWEEN :startDate AND :endDate")
    Long countByEmployeeAndStatusAndDateBetween(Employee employee, Attendance.AttendanceStatus status, LocalDate startDate, LocalDate endDate);
}