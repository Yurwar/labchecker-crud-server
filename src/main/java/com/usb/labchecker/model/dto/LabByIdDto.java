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
public class LabByIdDto {
    private Integer id;
    private Integer number;
    private Integer subjectId;
    private String description;
    private List<Document> docs;
    private Integer maxMark;
}
