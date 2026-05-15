package com.vts.hrms.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TrainingSCSTDTO {

    private String labName;

    private Long cepScstTrained;
    private BigDecimal cepExpenditure;
    private Long cepPercentage;

    private Long specialTargetedScstTrained;
    private BigDecimal specialTargetedExpenditure;
    private Long specialTargetedPercentage;

    private Long higherDegreeScstTrained;
    private BigDecimal higherDegreeExpenditure;
    private Long higherDegreePercentage;

    private Long sponsoredSeminarScstTrained;
    private BigDecimal sponsoredSeminarExpenditure;
    private Long sponsoredSeminarPercentage;

    private Long foreignTrainingScstTrained;
    private Long foreignTrainingExpenditure;
    private Long foreignTrainingPercentage;

    private Long othersScstTrained;
    private Long othersExpenditure;
    private Long othersPercentage;

}
