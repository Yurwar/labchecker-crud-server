package com.usb.labchecker.model.service;

import com.usb.labchecker.model.dto.GithubUserDto;
import com.usb.labchecker.model.dto.StudentByTelegramIdDto;
import com.usb.labchecker.model.dto.StudentDto;
import com.usb.labchecker.model.entity.Lab;
import com.usb.labchecker.model.entity.Student;
import com.usb.labchecker.model.entity.Variant;
import com.usb.labchecker.model.repository.LabRepository;
import com.usb.labchecker.model.repository.LabResultRepository;
import com.usb.labchecker.model.repository.StudentRepository;
import com.usb.labchecker.model.repository.VariantRepository;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final String GITHUB_API_URL_PREFIX = "https://api.github.com/user/";
    private RestTemplate restTemplate;
    private final StudentRepository studentRepository;
    private final LabResultRepository labResultRepository;
    private final LabRepository labRepository;
    private final VariantRepository variantRepository;
    private final GroupService groupService;

    public StudentService(RestTemplateBuilder restTemplate,
                          StudentRepository studentRepository,
                          GroupService groupService,
                          LabResultRepository labResultRepository,
                          LabRepository labRepository,
                          VariantRepository variantRepository) {
        this.restTemplate = restTemplate.build();
        this.studentRepository = studentRepository;
        this.groupService = groupService;
        this.labResultRepository = labResultRepository;
        this.labRepository = labRepository;
        this.variantRepository = variantRepository;
    }

    public Student getOne(int id) {
        return studentRepository.getOne(id);
    }

    public Student createStudent(StudentDto studentDto) {
        Student student = Student.builder()
                .firstName(studentDto.getFirstName())
                .lastName(studentDto.getLastName())
                .githubLink(studentDto.getGithubLink())
                .githubId(studentDto.getGithubId())
                .group(groupService.getByName(studentDto.getGroupName()))
                .telegramId(studentDto.getTelegramId())
                .build();

        studentRepository.save(student);
        return student;
    }

    public Iterable<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentByTelegramId(int telegramId) {
        return studentRepository.getByTelegramId(telegramId).orElseThrow(NoSuchElementException::new);
    }

    public StudentByTelegramIdDto getStudentIdByTelegramId(Integer telegramId) {
        return StudentByTelegramIdDto.builder()
                .studentId(studentRepository.getByTelegramId(telegramId)
                .orElseThrow(NoSuchElementException::new).getId())
                .build();
    }

    public Integer getStudentVariantByGithubIdAndLabRepoName(String githubId, String labRepoName) {
        Optional<Student> student = studentRepository.findByGithubId(githubId);
        Optional<Lab> lab = labRepository.findByRepoName(labRepoName);
        List<Variant> variants = variantRepository.findAllByLab(lab.orElseThrow(NoSuchElementException::new));

        return student.orElseThrow(NoSuchElementException::new).getId() % variants.size();
    }
}
