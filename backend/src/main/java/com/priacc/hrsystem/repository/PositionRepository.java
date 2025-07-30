package com.priacc.hrsystem.repository;

import com.priacc.hrsystem.model.Department;
import com.priacc.hrsystem.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    
    Optional<Position> findByTitleAndDepartment(String title, Department department);
    
    List<Position> findByDepartment(Department department);
    
    @Query("SELECT p FROM Position p WHERE p.isManagement = :isManagement")
    List<Position> findByIsManagementPosition(boolean isManagement);
    
    List<Position> findByIsManagement(boolean isManagement);
}