package com.dakson.hr.app.location.domain.repository;

import com.dakson.hr.app.location.domain.dao.DepartmentResponseDao;
import com.dakson.hr.app.location.domain.dao.DetailedDepartmentDao;
import com.dakson.hr.app.location.domain.entity.Department;
import com.dakson.hr.common.constant.Status;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository
  extends JpaRepository<Department, Integer> {
  @Query(
    """
    SELECT new com.dakson.hr.app.location.domain.dao.DetailedDepartmentDao(
      d.id, d.name,
      m.id, m.firstName, m.lastName,
      l.id, l.city
    )
    FROM Department d
    LEFT JOIN d.manager m
    JOIN d.location l
    WHERE d.id = :id
    """
  )
  Optional<DetailedDepartmentDao> findDetailedById(@Param("id") Integer id);

  @Modifying
  @Query(
    """
      UPDATE Department d SET d.manager.id = :managerId, d.updatedBy = :updatedBy WHERE d.id = :departmentId
    """
  )
  int assignManagerToDepartment(
    @Param("departmentId") Integer departmentId,
    @Param("managerId") Integer managerId,
    @Param("updatedBy") Integer updatedBy
  );

  @Modifying
  @Query(
    """
      UPDATE Department d SET d.location.id = :locationId, d.updatedBy = :updatedBy WHERE d.id = :departmentId
    """
  )
  int changeDepartmentLocation(
    @Param("departmentId") Integer departmentId,
    @Param("locationId") Integer locationId,
    @Param("updatedBy") Integer updatedBy
  );

  @Modifying
  @Query(
    """
    UPDATE Department d
    SET d.status = 'INACTIVE',
        d.updatedAt = CURRENT_TIMESTAMP,
        d.updatedBy = :updatedBy
    WHERE d.id = :id
    """
  )
  int deleteDepartmentById(
    @Param("id") Integer id,
    @Param("updatedBy") Integer updatedBy
  );

  @Modifying
  @Query(
    """
    UPDATE Department d
    SET d.name = :name,
        d.updatedAt = CURRENT_TIMESTAMP,
        d.updatedBy = :updatedBy
    WHERE d.id = :id
    """
  )
  int updateDepartmentName(
    @Param("id") Integer id,
    @Param("name") String name,
    @Param("updatedBy") Integer updatedBy
  );

  @Query(
    "SELECT COUNT(d) > 0 FROM Department d WHERE d.id = :departmentId AND d.manager.id = :managerId"
  )
  boolean isManagerAlreadyAssigned(
    @Param("departmentId") Integer departmentId,
    @Param("managerId") Integer managerId
  );

  @Query(
    "SELECT COUNT(d) > 0 FROM Department d WHERE d.id = :departmentId AND d.location.id = :locationId"
  )
  boolean isLocationAlreadyAssigned(
    @Param("departmentId") Integer departmentId,
    @Param("locationId") Integer locationId
  );

  boolean existsByIdAndStatus(Integer id, Status status);

  @Query(
    """
      SELECT new com.dakson.hr.app.location.domain.dao.DepartmentResponseDao(
        d.id, d.name,
        m.id, m.firstName, m.lastName
      )
      FROM Department d
      LEFT JOIN d.manager m
      WHERE d.status = :status
    """
  )
  Page<DepartmentResponseDao> findAllByStatusCustom(
    @Param("status") Status status,
    Pageable pageable
  );

  @Query(
    "SELECT COUNT(d) > 0 FROM Department d WHERE d.manager.id = :managerId"
  )
  boolean isManagerAssignedToAnyDepartment(
    @Param("managerId") Integer managerId
  );
}
