package com.usb.labchecker.model.service;

import com.usb.labchecker.model.dto.LabResultSpreadSheetDto;
import com.usb.labchecker.model.dto.StudentDto;
import com.usb.labchecker.model.entity.Lab;
import com.usb.labchecker.model.entity.LabResult;
import com.usb.labchecker.model.entity.Student;
import com.usb.labchecker.model.repository.LabResultRepository;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class SpreadSheetService {

    private static final String API_LAB = "/api/lab/result";
    private static final String API_STUDENT = "/api/student";
    private final String SPREAD_SHEET_API_URL = "https://teacherlabchecker.azurewebsites.net";

    private LabResultRepository labResultRepository;

    public SpreadSheetService(LabResultRepository labResultRepository) {
        this.labResultRepository = labResultRepository;
    }

    public void postLabResult(LabResult labResult){

        System.out.println(labResult);
        labResult = findBestResult(labResult.getStudent(), labResult.getLab())
        .orElseThrow(NoSuchElementException::new);

        LabResultSpreadSheetDto labResultSpreadSheetDto =
                LabResultSpreadSheetDto.builder()
                        .labNumber(labResult.getLab().getLabNumber())
                        .studentName(labResult.getStudent().getFirstName() +
                                " " + labResult.getStudent().getLastName())
                        .subjectName(labResult.getLab().getCourse().getSubject().getName())
                        .groupName(labResult.getStudent().getGroup().getName())
                        .result(labResult.getMark())
                        .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        try {
            restTemplate.postForLocation(SPREAD_SHEET_API_URL + API_LAB, labResultSpreadSheetDto);
        }
        catch (HttpServerErrorException errorException){
        }

    }

    public void postStudent(StudentDto studentDto){

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        try {
            restTemplate.postForLocation(SPREAD_SHEET_API_URL + API_STUDENT, studentDto);
        }
        catch (HttpServerErrorException errorException){
        }

    }

    private Optional<LabResult> findBestResult(Student student, Lab lab){
        return labResultRepository.findAllByStudentAndLab(student, lab)
                .stream()
                .max(Comparator.comparing(LabResult::getMark));
    }

    //    public void putLabResult(LabResult labResult) {
//
//        LabResultSpreadSheetDto labResultSpreadSheetDto =
//                LabResultSpreadSheetDto.builder()
//                        .labNumber(labResult.getLab().getLabNumber())
//                        .studentName(labResult.getStudent().getFirstName() +
//                                " " + labResult.getStudent().getLastName())
//                        .subjectName(labResult.getLab().getCourse().getSubject().getName())
//                        .groupName(labResult.getStudent().getGroup().getName())
//                        .result(labResult.getMark())
//                .build();
//
//        CloseableHttpClient httpClient = HttpClients.custom()
//                .setSSLHostnameVerifier(new NoopHostnameVerifier())
//                .build();
//
//        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
//        requestFactory.setHttpClient(httpClient);
//
//        RestTemplate restTemplate = new RestTemplate(requestFactory);
//        HttpHeaders headers = new HttpHeaders();
//        HttpEntity<LabResultSpreadSheetDto> requestUpdate =
//                new HttpEntity<>(labResultSpreadSheetDto, headers);
//        String resourceUrl = SPREAD_SHEET_API_URL + API_LAB + "/" +
//                labResult.getLab().getId();
//
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        System.out.println("Dto: " + labResultSpreadSheetDto + '\n' +
//                "Url: " + resourceUrl);
//
//        restTemplate.exchange(resourceUrl, HttpMethod.PUT, requestUpdate, Void.class);
//    }

}
