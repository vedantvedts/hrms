package com.vts.hrms.repository;

import com.vts.hrms.entity.Sponsorship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SponsorshipRepository extends JpaRepository<Sponsorship, Long> {

    @Query("""
            SELECT a FROM Sponsorship a WHERE a.degreeType = :type AND a.isActive=1
            ORDER BY a.sponsorshipId DESC
            """)
    List<Sponsorship> findAllByDegreeType(String type);

    @Query("""
                SELECT s
                FROM Sponsorship s
                WHERE s.isActive = 1
                  AND s.fromDate >= :fromDate
                  AND s.toDate <= :toDate
                ORDER BY s.sponsorshipId DESC
            """)
    List<Sponsorship> getSponsorshipDataByDateRange(@Param("fromDate") LocalDate fromDate,
                                                    @Param("toDate") LocalDate toDate);

    @Query("""
                SELECT s
                FROM Sponsorship s
                WHERE s.degreeType = :degreeType
                  AND s.fromDate >= :fromDate
                  AND s.toDate <= :toDate
                  AND s.isActive = 1
                ORDER BY s.sponsorshipId DESC
            """)
    List<Sponsorship> findByDegreeTypeAndDateRange(@Param("degreeType") String degreeType,
                                                   @Param("fromDate") LocalDate fromDate,
                                                   @Param("toDate") LocalDate toDate);
}
