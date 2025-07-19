package com.priacc.hrsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "employees")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(unique = true)
    private String employeeId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @NotBlank
    @Size(max = 50)
    private String phone;

    @NotNull
    private LocalDate dateOfBirth;

    @NotBlank
    @Size(max = 10)
    private String gender;

    @Embedded
    private Address address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @OneToMany(mappedBy = "manager")
    @JsonIgnore
    private List<Employee> subordinates = new ArrayList<>();

    @NotBlank
    @Size(max = 20)
    private String employmentType;

    @NotNull
    private LocalDate joinDate;

    private LocalDate terminationDate;

    @NotBlank
    @Size(max = 20)
    private String status;

    @Embedded
    private Salary salary;

    @Embedded
    private BankDetails bankDetails;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents = new ArrayList<>();

    @Embedded
    private EmergencyContact emergencyContact;

    @ElementCollection
    @CollectionTable(name = "employee_skills", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "skill")
    private Set<String> skills = new HashSet<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> educations = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkExperience> workExperiences = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Leave> leaves = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attendance> attendances = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Embeddable
    @Data
    public static class Address {
        private String street;
        private String city;
        private String state;
        private String zipCode;
        private String country;
    }

    @Embeddable
    @Data
    public static class Salary {
        private BigDecimal amount;
        private String currency;
        private LocalDate effectiveDate;
    }

    @Embeddable
    @Data
    public static class BankDetails {
        private String bankName;
        private String accountNumber;
        private String ifscCode;
    }

    @Embeddable
    @Data
    public static class EmergencyContact {
        private String name;
        private String relationship;
        private String phone;
        private String email;
    }
}