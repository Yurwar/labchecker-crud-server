package com.usb.labchecker.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestRunnerBuildStartNotificationDto {
    private String repoName;
    private long githubId;
}
