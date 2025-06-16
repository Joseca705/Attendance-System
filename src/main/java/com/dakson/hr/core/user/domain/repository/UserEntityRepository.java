package com.dakson.hr.core.user.domain.repository;

import com.dakson.hr.core.user.api.model.response.AuthUserFlatDto;
import com.dakson.hr.core.user.domain.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
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
    """
  )
  List<AuthUserFlatDto> findingFlatByUsername(@Param("username") String username);
}
