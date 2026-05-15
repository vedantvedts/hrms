package com.vts.hrms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnualTrainingReportDTO implements Serializable {

    private String course;
    private Long withinLab;
    private Long otherLabs;
    private Long outsideWithinIndia;
    private Long outsideForeignAgency;
    private Long subTotal;

    private Long drdsTni;
    private Long drdsRacBoard;
    private Long drdsOpen;
    private Long drdsTotal;
    private Long drdsMaleFemale;

    private Long drtcTni;
    private Long drtcCeptamBoard;
    private Long drtcOpen;
    private Long drtcTotal;
    private Long drtcMaleFemale;

    private Long adminAlliedTni;
    private Long adminAlliedOpen;
    private Long adminAlliedTotal;
    private Long adminAlliedMaleFemale;

    private Long othersTotal;
    private Long othersMaleFemale;

}
