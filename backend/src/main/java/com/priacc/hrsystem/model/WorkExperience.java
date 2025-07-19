package com.priacc.hrsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "work_experiences")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String company;

    @NotBlank
    @Size(max = 100)
    private String position;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    @Size(max = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public WorkExperience(String company, String position, LocalDate startDate, 
                         LocalDate endDate, String description, Employee employee) {
        this.company = company;
        this.position = position;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.employee = employee;
    }
}