package com.usb.labchecker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LabResultTestingServerDto {
    String studentGithubLogin;
    long studentGithubID;
    int variant;
    int mark;
    String repositoryName;
    String language;
    LocalDateTime updatedDate;
}
