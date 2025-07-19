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
@Table(name = "educations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String degree;

    @NotBlank
    @Size(max = 100)
    private String institution;

    @NotBlank
    @Size(max = 100)
    private String fieldOfStudy;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    @Size(max = 20)
    private String grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public Education(String degree, String institution, String fieldOfStudy, 
                    LocalDate startDate, LocalDate endDate, String grade, Employee employee) {
        this.degree = degree;
        this.institution = institution;
        this.fieldOfStudy = fieldOfStudy;
        this.startDate = startDate;
        this.endDate = endDate;
        this.grade = grade;
        this.employee = employee;
    }
}