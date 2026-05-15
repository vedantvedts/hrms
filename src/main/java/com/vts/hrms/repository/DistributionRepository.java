package com.vts.hrms.repository;

import com.vts.hrms.entity.Distribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DistributionRepository extends JpaRepository<Distribution, Long> {

    List<Distribution> findAllByIsActive(int isActive);

    @Query("""
                SELECT d
                FROM Distribution d
                WHERE d.isActive = 1
                  AND d.distributionDate BETWEEN :fromDate AND :toDate
            """)
    List<Distribution> findActiveDistributionsByDateRange(@Param("fromDate") LocalDate fromDate,
                                                          @Param("toDate") LocalDate toDate);
}
