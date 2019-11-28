package com.usb.labchecker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
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
