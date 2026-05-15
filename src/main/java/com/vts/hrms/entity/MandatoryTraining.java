package com.vts.hrms.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "hrms_mandatory_training")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MandatoryTraining implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mandatory_training_id")
    private Long mandatoryTrainingId;

    @Column(name = "participant_id")
    private Long participantId;

    @Column(name = "course_name", length = 255)
    private String courseName;

    @Column(name = "course_type", length = 10)
    private String courseType;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "organizer", length = 255)
    private String organizer;

    @Column(name = "reference", length = 255)
    private String reference;

    @Column(name = "venue", length = 255)
    private String venue;

    @Column(name = "registration_fee", precision = 10, scale = 2)
    private BigDecimal registrationFee;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    @Column(name = "created_by", length = 255)
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_by", length = 255)
    private String modifiedBy;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "is_active", nullable = false)
    private Integer isActive = 1;

}
