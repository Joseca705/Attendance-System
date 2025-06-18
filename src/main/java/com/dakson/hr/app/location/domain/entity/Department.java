package com.dakson.hr.app.location.domain.entity;

import com.dakson.hr.common.entity.BaseEntity;
import com.dakson.hr.core.user.domain.entity.Employee;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "departments")
public class Department extends BaseEntity implements Serializable {

  public Department(Integer id) {
    this.id = id;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, length = 100)
  private String name;

  @ManyToOne
  @JoinColumn(
    name = "location_id",
    referencedColumnName = "id",
    nullable = false
  )
  private Location location;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "manager_id", referencedColumnName = "id", nullable = true)
  private Employee manager;
}
