package com.usb.labchecker.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BotBuildStartNotificationDto {
    private int studentId;
    private int subjectId;
    private int labId;
}
