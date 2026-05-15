package com.vts.hrms.repository;

import com.vts.hrms.entity.CepAttachments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CepAttachmentsRepository extends JpaRepository<CepAttachments, Long> {

    List<CepAttachments> findByCepId(Long cepId);
}
