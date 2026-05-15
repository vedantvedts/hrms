package com.vts.hrms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "hrms_journal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Journal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "journal_id")
    private Long journalId;

    @Column(name = "emp_id", nullable = false)
    private Long empId;

    @Column(name = "title_of_paper", columnDefinition = "TEXT")
    private String titleOfPaper;

    @Column(name = "journal_type", nullable = false, length = 20)
    private String journalType;

    @Column(name = "journal_name")
    private String journalName;

    @Column(name = "volume")
    private String volume;

    @Column(name = "impact_factor")
    private BigDecimal impactFactor;

    @Column(name = "publication_fee")
    private BigDecimal publicationFee;

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

    @Column(name = "is_active")
    private Integer isActive = 1;
}