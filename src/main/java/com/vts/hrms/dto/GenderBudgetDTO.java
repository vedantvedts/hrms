package com.vts.hrms.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GenderBudgetDTO implements Serializable {

    private String labName;

    private BigDecimal cepMaleNo;
    private BigDecimal cepMaleExp;
    private BigDecimal cepFemaleNo;
    private BigDecimal cepFemaleExp;
    private BigDecimal cepTotalNo;
    private BigDecimal cepTotalExp;

    private BigDecimal specialMaleNo;
    private BigDecimal specialMaleExp;
    private BigDecimal specialFemaleNo;
    private BigDecimal specialFemaleExp;
    private BigDecimal specialTotalNo;
    private BigDecimal specialTotalExp;

    private BigDecimal higherDegreeMaleNo;
    private BigDecimal higherDegreeMaleExp;
    private BigDecimal higherDegreeFemaleNo;
    private BigDecimal higherDegreeFemaleExp;
    private BigDecimal higherDegreeTotalNo;
    private BigDecimal higherDegreeTotalExp;

    private BigDecimal seminarMaleNo;
    private BigDecimal seminarMaleExp;
    private BigDecimal seminarFemaleNo;
    private BigDecimal seminarFemaleExp;
    private BigDecimal seminarTotalNo;
    private BigDecimal seminarTotalExp;

    private BigDecimal foreignMaleNo;
    private BigDecimal foreignMaleExp;
    private BigDecimal foreignFemaleNo;
    private BigDecimal foreignFemaleExp;
    private BigDecimal foreignTotalNo;
    private BigDecimal foreignTotalExp;

    private BigDecimal othersMaleNo;
    private BigDecimal othersMaleExp;
    private BigDecimal othersFemaleNo;
    private BigDecimal othersFemaleExp;
    private BigDecimal othersTotalNo;
    private BigDecimal othersTotalExp;


}
