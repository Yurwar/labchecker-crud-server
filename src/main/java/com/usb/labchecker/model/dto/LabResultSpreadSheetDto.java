package com.usb.labchecker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LabResultSpreadSheetDto {

    private String subjectName;
    private String studentName;
    private String groupName;
    private Integer labNumber;
    private Double result;

}
