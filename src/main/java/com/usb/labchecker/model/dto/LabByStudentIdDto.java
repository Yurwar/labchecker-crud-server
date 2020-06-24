package com.usb.labchecker.model.dto;

import com.usb.labchecker.model.entity.Document;
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
    private List<Document> docs;
    private Integer variant;
    private Integer maxMark;
}
