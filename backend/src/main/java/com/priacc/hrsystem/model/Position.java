package com.priacc.hrsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "positions")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String title;

    @Size(max = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "position")
    @JsonIgnore
    private List<Employee> employees = new ArrayList<>();

    private boolean isManagement;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // Custom getter for backward compatibility
    public boolean isManagementPosition() {
        return isManagement;
    }
    
    // Custom setter for backward compatibility
    public void setIsManagementPosition(boolean isManagementPosition) {
        this.isManagement = isManagementPosition;
    }
    
    // Getter method for isManagementPosition
    public boolean getIsManagementPosition() {
        return isManagement;
    }

    public Position(String title, String description, Department department, boolean isManagement) {
        this.title = title;
        this.description = description;
        this.department = department;
        this.isManagement = isManagement;
    }
}