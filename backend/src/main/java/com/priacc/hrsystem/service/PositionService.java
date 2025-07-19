package com.priacc.hrsystem.service;

import com.priacc.hrsystem.dto.PositionDto;
import com.priacc.hrsystem.exception.ResourceNotFoundException;
import com.priacc.hrsystem.model.Department;
import com.priacc.hrsystem.model.Position;
import com.priacc.hrsystem.repository.DepartmentRepository;
import com.priacc.hrsystem.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PositionService {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    public Page<Position> getAllPositions(Pageable pageable) {
        return positionRepository.findAll(pageable);
    }

    public Position getPositionById(Long id) {
        return positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found with id: " + id));
    }

    public List<Position> getPositionsByDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));
        
        return positionRepository.findByDepartment(department);
    }

    public List<Position> getManagementPositions() {
        return positionRepository.findByIsManagementPosition(true);
    }

    @Transactional
    public Position createPosition(PositionDto positionDto) {
        Position position = new Position();
        position.setTitle(positionDto.getTitle());
        position.setDescription(positionDto.getDescription());
        position.setIsManagementPosition(positionDto.isManagementPosition());

        // Set department if provided
        if (positionDto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(positionDto.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + positionDto.getDepartmentId()));
            position.setDepartment(department);
        }

        return positionRepository.save(position);
    }

    @Transactional
    public Position updatePosition(Long id, PositionDto positionDto) {
        Position position = getPositionById(id);

        position.setTitle(positionDto.getTitle());
        position.setDescription(positionDto.getDescription());
        position.setIsManagementPosition(positionDto.isManagementPosition());

        // Update department if provided
        if (positionDto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(positionDto.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + positionDto.getDepartmentId()));
            position.setDepartment(department);
        } else if (positionDto.getDepartmentId() == null && position.getDepartment() != null) {
            // Remove department if null is provided
            position.setDepartment(null);
        }

        return positionRepository.save(position);
    }

    @Transactional
    public void deletePosition(Long id) {
        Position position = getPositionById(id);

        // Check if position has employees
        if (!position.getEmployees().isEmpty()) {
            throw new RuntimeException("Cannot delete position with associated employees");
        }

        positionRepository.delete(position);
    }
}