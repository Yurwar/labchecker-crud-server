package com.usb.labchecker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestRunnerBuildStartNotificationDto {
    private String repoName;
    private long githubId;
}
