package com.vts.hrms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardLoginStatsDTO {

    private String appCode;
    private Long count24h;
    private Long countWeek;
    private Long countMonth;
    private Long activeNow;
    private Long customTotalCount;
    private List<LoginChartPointDTO> customChartData;
    public static DashboardLoginStatsDTO empty(String appCode) {
        return new DashboardLoginStatsDTO(appCode, 0L, 0L, 0L, 0L, 0L, List.of());
    }
}
