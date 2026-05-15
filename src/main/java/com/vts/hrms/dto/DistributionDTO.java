package com.vts.hrms.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DistributionDTO implements Serializable {

    private Long distributionId;
    private LocalDate distributionDate;

    private Long empId;
    private String empNo;
    private String employeeName;
    private String empDesigCode;
    private String desigCadre;
    private String empDivCode;

    private Long aoEmpId;
    private Long roEmpId;
    private String techActivity;
    private String nonTechActivity;

    private String aoOfficerName;
    private String roOfficerName;
    private List<ProjectRoleDto> roleDtoList;

}
