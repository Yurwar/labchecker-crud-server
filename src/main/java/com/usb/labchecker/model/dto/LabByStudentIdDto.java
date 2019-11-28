package com.usb.labchecker.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class LabByStudentIdDto {
    private Integer id;
    private Integer subjectId;
    private Integer number;
    private String description;
    private List<DocsDto> docs;
    private Integer variant;
}
