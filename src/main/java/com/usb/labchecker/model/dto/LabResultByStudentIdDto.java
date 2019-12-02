package com.usb.labchecker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LabResultByStudentIdDto {

    private Integer id;
    private Integer subjectId;
    private Integer labId;
    private Double result;
    private LocalDateTime checkDateTime;

}
