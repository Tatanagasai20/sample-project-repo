package com.priacc.hrsystem.controller;

import com.priacc.hrsystem.dto.PositionDto;
import com.priacc.hrsystem.model.Position;
import com.priacc.hrsystem.service.PositionService;
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
@RequestMapping("/api/positions")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Positions", description = "Position management API")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all positions", description = "Retrieve all positions (All authenticated users)")
    public ResponseEntity<List<PositionDto>> getAllPositions() {
        List<Position> positions = positionService.getAllPositions();
        List<PositionDto> positionDtos = positions.stream()
                .map(PositionDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(positionDtos);
    }

    @GetMapping("/paged")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all positions with pagination", description = "Retrieve all positions with pagination (All authenticated users)")
    public ResponseEntity<Page<PositionDto>> getAllPositions(Pageable pageable) {
        Page<Position> positions = positionService.getAllPositions(pageable);
        Page<PositionDto> positionDtos = positions.map(PositionDto::fromEntity);
        return ResponseEntity.ok(positionDtos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get position by ID", description = "Retrieve a position by ID (All authenticated users)")
    public ResponseEntity<PositionDto> getPositionById(@PathVariable Long id) {
        Position position = positionService.getPositionById(id);
        return ResponseEntity.ok(PositionDto.fromEntity(position));
    }

    @GetMapping("/department/{departmentId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get positions by department", description = "Retrieve all positions in a department (All authenticated users)")
    public ResponseEntity<List<PositionDto>> getPositionsByDepartment(@PathVariable Long departmentId) {
        List<Position> positions = positionService.getPositionsByDepartment(departmentId);
        List<PositionDto> positionDtos = positions.stream()
                .map(PositionDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(positionDtos);
    }

    @GetMapping("/management")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
    @Operation(summary = "Get management positions", description = "Retrieve all management positions (Admin and HR only)")
    public ResponseEntity<List<PositionDto>> getManagementPositions() {
        List<Position> positions = positionService.getManagementPositions();
        List<PositionDto> positionDtos = positions.stream()
                .map(PositionDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(positionDtos);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
    @Operation(summary = "Create a new position", description = "Create a new position (Admin and HR only)")
    public ResponseEntity<PositionDto> createPosition(@Valid @RequestBody PositionDto positionDto) {
        Position position = positionService.createPosition(positionDto);
        return new ResponseEntity<>(PositionDto.fromEntity(position), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
    @Operation(summary = "Update a position", description = "Update a position (Admin and HR only)")
    public ResponseEntity<PositionDto> updatePosition(@PathVariable Long id, @Valid @RequestBody PositionDto positionDto) {
        Position position = positionService.updatePosition(id, positionDto);
        return ResponseEntity.ok(PositionDto.fromEntity(position));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a position", description = "Delete a position (Admin only)")
    public ResponseEntity<Void> deletePosition(@PathVariable Long id) {
        positionService.deletePosition(id);
        return ResponseEntity.noContent().build();
    }
}