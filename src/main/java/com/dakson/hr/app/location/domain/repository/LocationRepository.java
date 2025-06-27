package com.dakson.hr.app.location.domain.repository;

import com.dakson.hr.app.location.domain.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
  @Modifying
  @Query(
    """
    UPDATE Location l SET l.status = 'INACTIVE' WHERE l.id = :id
        """
  )
  void deleteLocationRegister(@Param("id") Integer id);
}
