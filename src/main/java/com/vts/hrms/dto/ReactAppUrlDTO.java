package com.vts.hrms.dto;


import lombok.Data;

@Data
public class ReactAppUrlDTO {

    private Long appUrlId;
    private String appCode;
    private String appUrl;
    private Integer isActive;
}
