package com.vts.hrms.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CepDTO implements Serializable {

    private Long cepId;

    private String divisionCode;

    @NotNull(message = "Division is required")
    private Long divisionId;

    @NotNull(message = "From date is required")
    private LocalDate fromDate;

    @NotNull(message = "To date is required")
    private LocalDate toDate;

    @Positive(message = "Duration must be greater than 0")
    private Long duration;

    @Positive(message = "Number of participants must be greater than 0")
    private Long noOfParticipants;

    @DecimalMin(value = "0.0", inclusive = true, message = "Total amount cannot be negative")
    @Digits(integer = 12, fraction = 2, message = "Total amount format is invalid")
    private BigDecimal totalAmount;

    @DecimalMin(value = "0.0", inclusive = true, message = "Amount spent cannot be negative")
    @Digits(integer = 12, fraction = 2, message = "Amount spent format is invalid")
    private BigDecimal amountSpent;

    @Size(max = 500, message = "Comments must not exceed 500 characters")
    private String comments;

    private Long courseCoordinatorId;
    private String courseCoordinatorName;

    private Long deputyCourseCoordinatorId;
    private String deputyCourseCoordinatorName;

    private List<CepAttachmentsDTO> cepAttachments;

}
