package com.priacc.hrsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "attendances")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private LocalDate date;

    private LocalTime checkInTime;

    private LocalTime checkOutTime;

    private LocalTime breakStartTime;

    private LocalTime breakEndTime;

    private double workHours;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    @Size(max = 500)
    private String notes;

    private String ipAddress;

    private String location;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public enum AttendanceStatus {
        PRESENT,
        ABSENT,
        HALF_DAY,
        LATE,
        LEAVE,
        HOLIDAY,
        WEEKEND
    }

    public Attendance(Employee employee, LocalDate date, LocalTime checkInTime) {
        this.employee = employee;
        this.date = date;
        this.checkInTime = checkInTime;
        this.status = AttendanceStatus.PRESENT;
    }
}