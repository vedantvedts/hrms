package com.vts.hrms.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class JournalDTO {

    private Long journalId;

    @NotNull(message = "Employee ID is required")
    private Long empId;
    private String empNo;
    private String desigCadre;
    private String employeeName;

    @NotBlank(message = "Title of paper cannot be empty")
    private String titleOfPaper;

    @NotBlank(message = "Journal type is required")
    private String journalType;

    @NotBlank(message = "Journal name is required")
    @Size(max = 255, message = "Journal name is too long")
    private String journalName;

    @Size(max = 50, message = "Volume name is too long")
    private String volume;

    @DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer = 3, fraction = 3, message = "Impact factor must be in format 0.000")
    private BigDecimal impactFactor;

    @DecimalMin(value = "0.0", inclusive = true, message = "Fee cannot be negative")
    private BigDecimal publicationFee;
}

