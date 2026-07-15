package com.vts.hrms.dto;

import lombok.Data;

@Data
public class DivisionGroupDTO {

    private Long groupId;
    private String labCode;
    private String groupCode;
    private String groupName;
    private Long groupHeadId;
    private Long tdId;

}
