package com.usb.labchecker.model.service;

import com.usb.labchecker.model.dto.BotBuildFinishNotificationDto;
import com.usb.labchecker.model.dto.BotBuildStartNotificationDto;
import com.usb.labchecker.model.dto.LabResultTestingServerDto;
import com.usb.labchecker.model.dto.TestRunnerBuildStartNotificationDto;
import com.usb.labchecker.model.entity.Lab;
import com.usb.labchecker.model.entity.Student;
import com.usb.labchecker.model.repository.LabRepository;
import com.usb.labchecker.model.repository.StudentRepository;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;

@Service
public class BotNotificationService {
    private static final String BUILD_START_ENDPOINT = "https://lab-checker-bot.maslovmikhail2000.now.sh/notification/build-start";
    private static final String BUILD_FINISH_ENDPOINT = "https://lab-checker-bot.maslovmikhail2000.now.sh/notification/build-finish";

    private StudentRepository studentRepository;
    private LabRepository labRepository;

    public BotNotificationService(StudentRepository studentRepository,
                                  LabRepository labRepository) {
        this.studentRepository = studentRepository;
        this.labRepository = labRepository;
    }

    public static void main(String[] args) {
        BotBuildStartNotificationDto botBuildStartNotificationDto = BotBuildStartNotificationDto.builder()
                .studentId(1)
                .subjectId(1)
                .labId(1)
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        restTemplate.postForLocation(BUILD_START_ENDPOINT, botBuildStartNotificationDto);
    }

    public void notifyAboutStartOfBuild(TestRunnerBuildStartNotificationDto testRunnerBuildStartNotificationDto) {

        Student student = studentRepository
                .findByGithubId(String.valueOf(testRunnerBuildStartNotificationDto.getGithubId()))
                .orElseThrow(NoSuchElementException::new);

        Lab lab = labRepository
                .findByRepoName(testRunnerBuildStartNotificationDto.getRepoName())
                .orElseThrow(NoSuchElementException::new);

        BotBuildStartNotificationDto botBuildStartNotificationDto = BotBuildStartNotificationDto.builder()
                .studentId(student.getId())
                .subjectId(lab.getCourse().getSubject().getId())
                .labId(lab.getId())
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        restTemplate.postForLocation(BUILD_START_ENDPOINT, botBuildStartNotificationDto);
    }

    public void notifyAboutFinishOfBuild(LabResultTestingServerDto labResultDto) {
        Student student = studentRepository
                .findByGithubId(String.valueOf(labResultDto.getStudentGithubID()))
                .orElseThrow(NoSuchElementException::new);

        Lab lab = labRepository
                .findByRepoName(labResultDto.getRepositoryName())
                .orElseThrow(NoSuchElementException::new);

        BotBuildFinishNotificationDto botBuildFinishNotificationDto = BotBuildFinishNotificationDto.builder()
                .studentId(student.getId())
                .subjectId(lab.getCourse().getSubject().getId())
                .labId(lab.getId())
                .result(labResultDto.getMark())
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        restTemplate.postForLocation(BUILD_FINISH_ENDPOINT, botBuildFinishNotificationDto);
    }
}
