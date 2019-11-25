package com.usb.labchecker.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class LabByIdDto {

    private Integer id;
    private Integer number;
    private Integer subjectId;
    private String description;
    private List<DocsDto> docs;
}
