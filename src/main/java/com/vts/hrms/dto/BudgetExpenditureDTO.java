package com.vts.hrms.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class BudgetExpenditureDTO implements Serializable {

    private Long empId;
    private String empName;
    private BigDecimal cep;
    private BigDecimal specialRE;
    private BigDecimal specialFE;
    private BigDecimal targetedRE;
    private BigDecimal targetedFE;
    private BigDecimal meRt;
    private BigDecimal meDirector;
    private BigDecimal techManagerial;
    private BigDecimal foreignRE;
    private BigDecimal foreignFE;
    private BigDecimal phd;
    private BigDecimal registrationRE;
    private BigDecimal registrationFE;
    private BigDecimal courseFee;
    private BigDecimal othersRE;
    private BigDecimal othersFE;
    private BigDecimal total;

}
