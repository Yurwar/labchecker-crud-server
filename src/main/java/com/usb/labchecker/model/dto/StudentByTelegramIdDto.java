package com.usb.labchecker.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StudentByTelegramIdDto {
    private Integer studentId;
}
