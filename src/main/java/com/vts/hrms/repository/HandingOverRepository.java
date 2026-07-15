package com.vts.hrms.repository;

import com.vts.hrms.entity.HandingOver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HandingOverRepository extends JpaRepository<HandingOver, Long> {

    List<HandingOver> findAllByFromDateBetweenOrderByHandingOverIdDesc(LocalDate fromDate, LocalDate toDate);

    @Query("""
                SELECT COUNT(h) > 0
                FROM HandingOver h
                WHERE h.fromEmpId = :fromEmpId
                AND h.isActive = 1
                AND (:handingOverId IS NULL OR h.handingOverId <> :handingOverId)
                AND :fromDate <= h.toDate
                AND :toDate >= h.fromDate
            """)
    boolean existsOverlappingDateRange(
            @Param("handingOverId") Long handingOverId, @Param("fromEmpId") Long fromEmpId,
            @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    List<HandingOver> findByToEmpIdAndToDateGreaterThanEqualAndIsActive(Long toEmpId, LocalDate date, int isActive);
}
