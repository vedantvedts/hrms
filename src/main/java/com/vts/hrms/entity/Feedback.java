package com.vts.hrms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "hrms_feedback")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id", nullable = false)
    private Long feedbackId;

    @Column(name = "requisition_id")
    private Long requisitionId;

    @Column(name = "feedback_date")
    private LocalDate feedbackDate;

    @Column(name = "participant_id")
    private Long participantId;

    @Column(name = "faculty_name")
    private String facultyName;

    @Column(name = "faculty_address")
    private String facultyAddress;

    @Column(name = "remarks")
    private String remark;

    @Column(name = "course")
    private String course;

    @Column(name = "coverage")
    private String coverage;

    @Column(name = "duration")
    private String duration;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "participant")
    private String participant;

    @Column(name = "course_venue")
    private String courseVenue;

    @Column(name = "quality")
    private String quality;

    @Column(name = "seminar_venue")
    private String seminarVenue;

    @Column(name = "certificate")
    private String certificate;

    @Column(name = "invoice")
    private String invoice;

    @Column(name = "is_accepted")
    private String isAccepted;

    @Column(name = "request_through")
    private String requestThrough;

    @Column(name = "accepted_by")
    private Long acceptedBy;

    @Column(name = "accepted_date")
    private LocalDateTime acceptedDate;

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

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Integer isActive;
}
