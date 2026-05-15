package com.vts.hrms.entity;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Data
@Entity
@Table(name = "hrms_distribution")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Distribution implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "distribution_id")
    private Long distributionId;

    @Column(name = "distribution_date")
    private LocalDate distributionDate;

    @Column(name = "emp_id")
    private Long empId;

    @Column(name = "ao_emp_id")
    private Long aoEmpId;

    @Column(name = "ro_emp_id")
    private Long roEmpId;

    @Size(max = 1000)
    @Column(name = "tech_activity", length = 1000)
    private String techActivity;

    @Size(max = 1000)
    @Column(name = "non_tech_activity", length = 1000)
    private String nonTechActivity;

    @Size(max = 255)
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Size(max = 255)
    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Integer isActive;
}
