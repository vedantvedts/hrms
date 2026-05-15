package com.vts.hrms.repository;

import com.vts.hrms.dto.SignRoleAuthorityDTO;
import com.vts.hrms.entity.SignRoleAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SignRoleAuthorityRepository extends JpaRepository<SignRoleAuthority, Long> {

    @Query("""
            SELECT r FROM SignRoleAuthority r WHERE r.isActive=1 ORDER BY r.signRoleAuthorityId DESC
            """)
    List<SignRoleAuthority> findAllByIsActive(int isActive);

    @Query("SELECT new com.vts.hrms.dto.SignRoleAuthorityDTO(" +
            "r.signRoleAuthorityId, " +
            "r.empId, " +
            "r.validFrom, " +
            "r.validUpto, " +
            "s.signAuthRoleId, " +
            "s.signAuthRole) " +
            "FROM SignRoleAuthority r " +
            "JOIN SignAuthRole s ON r.signAuthRoleId = s.signAuthRoleId " +
            "WHERE (r.validUpto IS NULL OR CURRENT_DATE <= r.validUpto) " +
            "AND s.isActive = 1 AND r.isActive = 1 AND s.signAuthRole = :authRole")
    List<SignRoleAuthorityDTO> findBySignAuthRole(@Param("authRole") String authRole);
}
