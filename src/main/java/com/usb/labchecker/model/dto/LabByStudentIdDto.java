package com.usb.labchecker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LabByStudentIdDto {
    private Integer id;
    private Integer subjectId;
    private Integer number;
    private String description;
    private List<DocsDto> docs;
    private Integer variant;
    private Integer maxMark;
}
