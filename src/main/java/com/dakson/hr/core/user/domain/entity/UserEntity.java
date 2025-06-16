package com.dakson.hr.core.user.domain.entity;

import com.dakson.hr.common.entity.BaseEntity;
import com.dakson.hr.core.authorization.domain.entity.UserRoleEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "username", length = 100, nullable = false)
  private String username;

  @Column(name = "password", length = 100, nullable = false)
  private String password;

  @Column(name = "email", length = 100, nullable = false, unique = true)
  private String email;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
  private PersonEntity person;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private Set<UserRoleEntity> roles;
}
