package com.vts.hrms.repository;

import com.vts.hrms.entity.RequisitionTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequisitionTransactionRepository extends JpaRepository<RequisitionTransaction, Long> {

    @Query(value = """
            SELECT *
            FROM (
                SELECT rt.*, 1 as rn
                FROM hrms_req_transaction rt
                WHERE rt.is_active = 1
                AND rt.requisition_id = :reqId
                AND rt.status_code NOT IN ('SF', 'CA', 'AR', 'AS')
                UNION ALL
                SELECT rt.*,
                       ROW_NUMBER() OVER (
                           PARTITION BY status_code
                           ORDER BY action_date DESC, transaction_id DESC
                       ) as rn
                FROM hrms_req_transaction rt
                WHERE rt.is_active = 1
                AND rt.requisition_id = :reqId
                AND rt.status_code IN ('SF', 'CA', 'AR', 'AS')
            ) t
            WHERE t.rn = 1 ORDER BY t.action_date;
            """, nativeQuery = true)
    List<RequisitionTransaction> findFilteredTransactions(@Param("reqId") Long reqId);

    List<RequisitionTransaction> findByRequisitionIdAndIsActiveOrderByActionDateDesc(Long requisitionId, int isActive);

    List<RequisitionTransaction> findAllByActionToAndStatusCodeInAndIsActive(Long empId, List<String> statusCodes, int i);

}
