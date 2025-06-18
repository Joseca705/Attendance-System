package com.dakson.hr.app.jobs.domain.entity;

import com.dakson.hr.app.location.domain.entity.Department;
import com.dakson.hr.common.entity.BaseEntity;
import com.dakson.hr.core.user.domain.entity.Employee;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "job_history")
public class JobHistory extends BaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(
    name = "employee_id",
    referencedColumnName = "id",
    nullable = false
  )
  private Employee employee;

  @Temporal(TemporalType.DATE)
  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Temporal(TemporalType.DATE)
  @Column(name = "end_date", nullable = true)
  private LocalDate endDate;

  @ManyToOne
  @JoinColumn(name = "job_id", referencedColumnName = "id", nullable = false)
  private Job job;

  @ManyToOne
  @JoinColumn(
    name = "department_id",
    referencedColumnName = "id",
    nullable = false
  )
  private Department department;
}
