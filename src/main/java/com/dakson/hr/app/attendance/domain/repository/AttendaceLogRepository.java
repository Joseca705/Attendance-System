package com.dakson.hr.app.attendance.domain.repository;

import com.dakson.hr.app.attendance.domain.dao.AttendanceLogByEmployeeDao;
import com.dakson.hr.app.attendance.domain.dao.EmployeeChecksDao;
import com.dakson.hr.app.attendance.domain.entity.AttendanceLog;
import com.dakson.hr.core.user.domain.entity.Employee;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendaceLogRepository
  extends JpaRepository<AttendanceLog, Long> {
  @Query(
    """
    SELECT new com.dakson.hr.app.attendance.domain.dao.EmployeeChecksDao(al.id, al.checkInTime, al.checkOutTime)
    FROM AttendanceLog al
    WHERE al.employee.id = :employeeId
    AND al.logDate = :logDate
    """
  )
  Optional<EmployeeChecksDao> findFlatByEmployeeIdAndLogDate(
    @Param("employeeId") Integer employeeId,
    @Param("logDate") LocalDate logDate
  );

  @Query(
    """
    UPDATE AttendanceLog al
    SET al.checkOutTime = :checkOutTime, al.remarks = :remarks
    WHERE al.id = :id
    """
  )
  @Modifying
  void updateCheckOut(
    @Param("checkOutTime") LocalTime checkOutTime,
    @Param("remarks") String remarks,
    @Param("id") Long id
  );

  Page<AttendanceLogByEmployeeDao> findByEmployeeOrderByLogDateDesc(
    Employee employee,
    Pageable pageable
  );
}
