package com.dakson.hr.app.attendance.domain.entity;

import com.dakson.hr.app.attendance.domain.constant.AttendaceStatus;
import com.dakson.hr.core.user.domain.entity.Employee;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
  name = "attendance_logs",
  uniqueConstraints = {
    @UniqueConstraint(columnNames = { "employee_id", "log_date" }),
  }
)
public class AttendanceLog implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "employee_id", referencedColumnName = "id")
  private Employee employee;

  @Temporal(TemporalType.DATE)
  @Column(name = "log_date", updatable = false)
  private LocalDate logDate;

  @Temporal(TemporalType.TIME)
  @Column(name = "check_in_time", updatable = false, nullable = true)
  private LocalTime checkInTime;

  @Temporal(TemporalType.TIME)
  @Column(name = "check_out_time", updatable = false, nullable = true)
  private LocalTime checkOutTime;

  @Enumerated(EnumType.STRING)
  private AttendaceStatus status;

  @Column(length = 100, nullable = true)
  private String remarks;
}
