package com.usb.labchecker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDto {
    private String firstName;
    private String lastName;
    private Integer chatId;
    private String groupName;
    private String githubLink;
    private String githubId;
}