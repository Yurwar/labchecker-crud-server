package com.usb.labchecker.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BotBuildFinishNotificationDto {
    private int studentId;
    private int subjectId;
    private int labId;
    private int result;
}
