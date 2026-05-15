package com.vts.hrms.repository;

import com.vts.hrms.entity.Cep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CepRepository extends JpaRepository<Cep, Long> {

    List<Cep> findAllByIsActive(Integer isActive);

    List<Cep> findAllByIsActiveOrderByCepIdDesc(int isActive);

    @Query("""
                SELECT c
                FROM Cep c
                WHERE c.isActive = 1
                  AND c.fromDate >= :fromDate
                  AND c.toDate <= :toDate
                ORDER BY c.cepId DESC
            """)
    List<Cep> getCepDataByDateRange(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );
}
