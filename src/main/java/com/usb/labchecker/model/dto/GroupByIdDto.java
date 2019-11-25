package com.usb.labchecker.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GroupByIdDto {
    private Integer id;
    private String name;
}
