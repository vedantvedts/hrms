package com.vts.hrms.repository;

import com.vts.hrms.entity.MandatoryTraining;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MandatoryTrainingRepository extends JpaRepository<MandatoryTraining, Long> {

    List<MandatoryTraining> findAllByIsActiveOrderByMandatoryTrainingIdDesc(int isActive);

    List<MandatoryTraining> findAllByParticipantIdAndIsActiveOrderByMandatoryTrainingIdDesc(Long empId, int isActive);

    @Query("""
                SELECT r
                FROM MandatoryTraining r
                WHERE r.isActive = 1
                  AND r.fromDate >= :fromDate
                  AND r.toDate <= :toDate
                ORDER BY r.mandatoryTrainingId DESC
            """)
    List<MandatoryTraining> getTrainingDataByDateRange(@Param("fromDate") LocalDate fromDate,
                                                       @Param("toDate") LocalDate toDate);
}
