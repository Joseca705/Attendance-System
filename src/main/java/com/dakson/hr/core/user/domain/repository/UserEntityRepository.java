package com.dakson.hr.core.user.domain.repository;

import com.dakson.hr.core.user.api.model.response.AuthUserFlatDto;
import com.dakson.hr.core.user.domain.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository
  extends JpaRepository<UserEntity, Integer> {
  @Query(
    """
    SELECT new com.dakson.hr.core.user.api.model.response.AuthUserFlatDto(
    u.id,
    u.password,
    r.role
    )
    FROM UserEntity u
    JOIN u.roles ur
    JOIN ur.role r
    WHERE u.username = :username
    AND u.status = 'ACTIVE'
    """
  )
  List<AuthUserFlatDto> findFlatByUsername(@Param("username") String username);

  @Query(
    """
    SELECT new com.dakson.hr.core.user.api.model.response.AuthUserFlatDto(
    u.id,
    u.password,
    r.role
    )
    FROM UserEntity u
    JOIN u.roles ur
    JOIN ur.role r
    WHERE u.id = :id
    AND u.status = 'ACTIVE'
    """
  )
  List<AuthUserFlatDto> findFlatByUserId(@Param("id") Integer id);

  @Query(
    """
    UPDATE UserEntity u 
    SET u.password = :password,
    u.updatedAt = CURRENT_TIMESTAMP
    WHERE u.id = :id
    AND u.status = 'ACTIVE'
    """
  )
  @Modifying
  void updateUserPassword(
    @Param("password") String password,
    @Param("id") Integer id
  );
}
