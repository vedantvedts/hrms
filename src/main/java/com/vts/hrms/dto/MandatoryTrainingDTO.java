package com.vts.hrms.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MandatoryTrainingDTO {

    private Long mandatoryTrainingId;

    @NotNull(message = "Participant is required")
    private Long participantId;
    private String participantName;
    private String empNo;
    private String empDesigName;
    private String desigCadre;
    private String empDivCode;
    private String email;
    private String mobileNo;

    @NotBlank(message = "Course name is required")
    @Size(max = 255, message = "Course name must not exceed 255 characters")
    private String courseName;

    @NotBlank(message = "Course type is required")
    @Size(max = 10, message = "Course type must not exceed 10 characters")
    private String courseType;

    @NotNull(message = "From date is required")
    private LocalDate fromDate;

    @NotNull(message = "To date is required")
    private LocalDate toDate;

    @Positive(message = "Duration must be positive")
    private Long duration;

    @NotBlank(message = "Organizer is required")
    @Size(max = 255, message = "Organizer must not exceed 255 characters")
    private String organizer;

    @Size(max = 255, message = "Reference must not exceed 255 characters")
    private String reference;

    @Size(max = 255, message = "Venue must not exceed 255 characters")
    private String venue;

    @DecimalMin(value = "0.0", inclusive = true, message = "Registration fee cannot be negative")
    @Digits(integer = 8, fraction = 2, message = "Invalid registration fee format")
    private BigDecimal registrationFee;

    @Size(max = 2000, message = "Remarks too long")
    private String remarks;

    private Integer isActive = 1;

    /**
     * Custom validation for date range
     */
    @AssertTrue(message = "To date must be after or equal to From date")
    public boolean isValidDateRange() {
        if (fromDate == null || toDate == null) {
            return true; // handled by @NotNull
        }
        return !toDate.isBefore(fromDate);
    }
}
