package com.vts.hrms.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CepAttachmentsDTO {

    private Long attachmentId;
    private Long cepId;
    private String attachmentName;
    private MultipartFile attachFile;
    private String existingFileName;

}
