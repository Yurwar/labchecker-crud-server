package com.usb.labchecker.model.service;

import com.usb.labchecker.model.dto.*;
import com.usb.labchecker.model.entity.Lab;
import com.usb.labchecker.model.entity.Student;
import com.usb.labchecker.model.repository.LabRepository;
import com.usb.labchecker.model.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;

@Service
public class BotNotificationService {
    private static final String BUILD_START_ENDPOINT = "http://lab-checker-bot.maslovmikhail2000.now.sh/notification/build-start";
    private static final String BUILD_FINISH_ENDPOINT = "http://lab-checker-bot.maslovmikhail2000.now.sh/notification/build-finish";

    private StudentRepository studentRepository;
    private LabRepository labRepository;

    public BotNotificationService(StudentRepository studentRepository,
                                  LabRepository labRepository) {
        this.studentRepository = studentRepository;
        this.labRepository = labRepository;
    }


    public void notifyAboutStartOfBuild(TestRunnerBuildStartNotificationDto testRunnerBuildStartNotificationDto) {

        Student student = studentRepository
                .findByGithubId(String.valueOf(testRunnerBuildStartNotificationDto.getGithubId()))
                .orElseThrow(NoSuchElementException::new);

        Lab lab = labRepository
                .findByRepoName(testRunnerBuildStartNotificationDto.getRepoName())
                .orElseThrow(NoSuchElementException::new);


        RestTemplate restTemplate = new RestTemplate();

        BotBuildStartNotificationDto botBuildStartNotificationDto = BotBuildStartNotificationDto.builder()
                .studentId(student.getId())
                .subjectId(lab.getCourse().getSubject().getId())
                .labId(lab.getId())
                .build();

        restTemplate.postForLocation(BUILD_START_ENDPOINT, botBuildStartNotificationDto);
    }

    public void notifyAboutFinishOfBuild(LabResultTestingServerDto labResultDto) {
        Student student = studentRepository
                .findByGithubId(String.valueOf(labResultDto.getStudentGithubID()))
                .orElseThrow(NoSuchElementException::new);

        Lab lab = labRepository
                .findByRepoName(labResultDto.getRepositoryName())
                .orElseThrow(NoSuchElementException::new);

        RestTemplate restTemplate = new RestTemplate();

        BotBuildFinishNotificationDto botBuildFinishNotificationDto = BotBuildFinishNotificationDto.builder()
                .studentId(student.getId())
                .subjectId(lab.getCourse().getSubject().getId())
                .labId(lab.getId())
                .result(labResultDto.getMark())
                .build();

        restTemplate.postForLocation(BUILD_FINISH_ENDPOINT, botBuildFinishNotificationDto);
    }
}
