package com.vts.hrms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "hrms_requisition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Requisition implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requisition_id", nullable = false)
    private Long requisitionId;

    @Column(name = "requisition_number", nullable = false, unique = true)
    private String requisitionNumber;

    @NotNull
    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @Column(name = "journal_id")
    private Long journalId;

    @NotNull
    @Column(name = "initiating_officer", nullable = false)
    private Long initiatingOfficer;

    @Size(max = 100)
    @Column(name = "reference", length = 100)
    private String reference;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "registration_fee")
    private BigDecimal registrationFee;

    @Column(name = "mode_of_payment")
    private String modeOfPayment;

    @Size(max = 2000)
    @Column(name = "necessity", length = 2000)
    private String necessity;

    @Size(max = 1)
    @Column(name = "is_submitted", length = 1)
    private String isSubmitted;

    @Size(max = 1)
    @Column(name = "is_paper_present", length = 1)
    private String isPaperPresent;

    @Column(name = "file_ecs")
    private String fileEcs;

    @Column(name = "status")
    private String status;

    @Column(name = "file_cheque")
    private String fileCheque;

    @Column(name = "file_pan")
    private String filePan;

    @Column(name = "file_brochure")
    private String fileBrochure;

    @Column(name = "file_committee_approval")
    private String fileCommitteeApproval;

    @Column(name = "file_acceptance_letter")
    private String fileAcceptanceLetter;

    @Column(name = "file_paper")
    private String filePaper;

    @Column(name = "reason")
    private String reason;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Integer isActive;

    @Size(max = 100)
    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Size(max = 100)
    @Column(name = "modified_by", length = 100)
    private String modifiedBy;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

}
