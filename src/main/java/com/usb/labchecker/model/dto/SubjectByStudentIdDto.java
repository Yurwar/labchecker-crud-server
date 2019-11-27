package com.usb.labchecker.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SubjectByStudentIdDto {

    private Integer id;
    private String name;
    private String teacher;
}
