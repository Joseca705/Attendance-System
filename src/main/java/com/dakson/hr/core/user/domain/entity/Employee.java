package com.dakson.hr.core.user.domain.entity;

import com.dakson.hr.app.jobs.domain.entity.Job;
import com.dakson.hr.app.location.domain.entity.Department;
import com.dakson.hr.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "employees")
public class Employee extends BaseEntity implements Serializable {

  public Employee(Integer id) {
    this.id = id;
  }

  public Employee(
    String firstName,
    String lastName,
    String phoneNumber,
    String email,
    LocalDate hireDate,
    BigDecimal salary,
    Department department,
    Employee manager
  ) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.hireDate = hireDate;
    this.salary = salary;
    this.department = department;
    this.manager = manager;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "first_name", length = 100, nullable = false)
  private String firstName;

  @Column(name = "last_name", length = 100, nullable = true)
  private String lastName;

  @Column(name = "phone_number", length = 50, nullable = true)
  private String phoneNumber;

  @Column(name = "email", length = 100, nullable = false, unique = true)
  private String email;

  @Temporal(TemporalType.DATE)
  @Column(name = "hire_date", nullable = false)
  private LocalDate hireDate;

  @Column(nullable = false, precision = 15, scale = 3)
  private BigDecimal salary;

  @ManyToOne
  @JoinColumn(name = "manager_id")
  private Employee manager;

  @ManyToOne
  @JoinColumn(
    name = "department_id",
    referencedColumnName = "id",
    nullable = true
  )
  private Department department;

  @ManyToOne
  @JoinColumn(name = "job_id", referencedColumnName = "id", nullable = false)
  private Job job;

  @OneToOne(mappedBy = "employee")
  private UserEntity user;
}
