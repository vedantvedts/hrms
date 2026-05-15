package com.vts.hrms.repository;

import com.vts.hrms.dto.AuditStampingDTO;
import com.vts.hrms.entity.AuditStamping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AuditStampingRepository extends JpaRepository<AuditStamping, Long> {

    @Query("""
                SELECT NEW com.vts.hrms.dto.AuditStampingDTO(
                    a.auditStampingId, a.loginId, a.username, a.loginDate,
                    a.loginDatetime, a.logoutDateTime, a.ipAddress, a.macAddress, a.logoutType
                )
                FROM AuditStamping a
                WHERE a.loginDate >= :fromDate
                AND a.loginDate < :toDate
                AND a.username = :userName
                ORDER BY a.loginDate DESC
            """)
    List<AuditStampingDTO> auditList(String userName, LocalDate fromDate, LocalDate toDate);

    // TOTAL COUNT
    @Query(value = """
        SELECT COUNT(*)
        FROM audit_stamping
        WHERE login_date_time BETWEEN :start AND :end
    """, nativeQuery = true)
    Long countLogins(@Param("start") LocalDateTime start,
                     @Param("end") LocalDateTime end);


    // DAILY GROUP
    @Query(value = """
        SELECT DATE(login_date_time) AS label, COUNT(*) AS count
        FROM audit_stamping
        WHERE login_date_time BETWEEN :start AND :end
        GROUP BY DATE(login_date_time)
        ORDER BY DATE(login_date_time)
    """, nativeQuery = true)
    List<Object[]> groupDaily(@Param("start") LocalDateTime start,
                              @Param("end") LocalDateTime end);


    // HOURLY GROUP (24h)
    @Query(value = """
        SELECT HOUR(login_date_time) AS label, COUNT(*) AS count
        FROM audit_stamping
        WHERE login_date_time >= NOW() - INTERVAL 24 HOUR
        GROUP BY HOUR(login_date_time)
        ORDER BY HOUR(login_date_time)
    """, nativeQuery = true)
    List<Object[]> groupHourly();

    @Query(value = """
            SELECT COUNT(*)
            FROM audit_stamping
            WHERE logout_type IS NULL AND login_date_time >= NOW() - INTERVAL 30 MINUTE
    """, nativeQuery = true)
    Long countActiveNow();

    @Query(value = "SELECT a.audit_stamping_id FROM audit_stamping a WHERE a.audit_stamping_id = (SELECT MAX(b.audit_stamping_id) FROM audit_stamping b WHERE b.login_id = :loginid)", nativeQuery = true)
    Optional<Long> findLastLoginStampingId(@Param("loginid") Long loginId);
}
