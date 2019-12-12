package com.usb.labchecker.model.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usb.labchecker.model.dto.LabResultDto;
import com.usb.labchecker.model.dto.LabResultSpreadSheetDto;
import com.usb.labchecker.model.dto.StudentDto;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class    SpreadSheetService {

    private final String spreadSheetAPIUrl = "https://teacherlabchecker.azurewebsites.net";
    private RestTemplate restTemplate;

    public SpreadSheetService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void putLabResult(LabResultDto labResultDto) {

        LabResultSpreadSheetDto labResultSpreadSheetDto =
                new LabResultSpreadSheetDto(labResultDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String resourceUrl = spreadSheetAPIUrl + "/api/lab/" +
                labResultDto.getLab().getId();
        HttpEntity<LabResultSpreadSheetDto> requestUpdate =
                new HttpEntity<>(labResultSpreadSheetDto, headers);

        restTemplate.exchange(resourceUrl, HttpMethod.PUT, requestUpdate, Void.class);
    }

    public void postStudent(StudentDto studentDto){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject studentDtoJSONObject = new JSONObject();

        studentDtoJSONObject.put("firstName", studentDto.getFirstName());
        studentDtoJSONObject.put("lastName", studentDto.getLastName());
        studentDtoJSONObject.put("telegramId", studentDto.getChatId());
        studentDtoJSONObject.put("groupName", studentDto.getGroupName());
        studentDtoJSONObject.put("githubId", studentDto.getGithubId());
        studentDtoJSONObject.put("githubLink", studentDto.getGithubLink());

        HttpEntity<String> request = new HttpEntity<String>(studentDtoJSONObject.toString(), headers);

        restTemplate.postForObject(spreadSheetAPIUrl + "/api/student", request, String.class);
    }
}
