package com.vts.hrms.repository;

import com.vts.hrms.entity.Journal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JournalRepository extends JpaRepository<Journal, Long> {

    List<Journal> findAllByIsActiveOrderByJournalIdDesc(int isActive);

    List<Journal> findAllByEmpIdAndIsActiveOrderByJournalIdDesc(Long empId, int isActive);
}
