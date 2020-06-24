package com.usb.labchecker.model.dto;

import com.usb.labchecker.model.entity.Lab;
import com.usb.labchecker.model.entity.Student;
import com.usb.labchecker.model.entity.Variant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LabResultDto {
    private Lab lab;
    private Student student;
    private Variant variant;
    private String githubRepositoryLink;
    private Double mark;
}
