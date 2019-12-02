package com.usb.labchecker.model.service;

import com.usb.labchecker.model.dto.LabByIdDto;
import com.usb.labchecker.model.dto.LabByStudentIdDto;
import com.usb.labchecker.model.entity.*;
import com.usb.labchecker.model.repository.CourseRepository;
import com.usb.labchecker.model.repository.LabRepository;
import com.usb.labchecker.model.repository.LabResultRepository;
import com.usb.labchecker.model.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class LabService {
    private final LabRepository labRepository;
    private final StudentService studentService;
    private final CourseService courseService;
    private final LabResultRepository labResultRepository;
    private final SubjectRepository subjectRepository;
    private final CourseRepository courseRepository;
    private final VariantService variantService;

    public LabService(LabRepository labRepository,
                      StudentService studentService,
                      CourseService courseService,
                      LabResultRepository labResultRepository,
                      SubjectRepository subjectRepository,
                      CourseRepository courseRepository,
                      VariantService variantService) {
        this.labRepository = labRepository;
        this.studentService = studentService;
        this.courseService = courseService;
        this.labResultRepository = labResultRepository;
        this.subjectRepository = subjectRepository;
        this.courseRepository = courseRepository;
        this.variantService = variantService;

    }

    public Lab getOne(int id) {
        return labRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public List<Lab> getAllLabs() {
        return (List<Lab>)labRepository.findAll();
    }

    public List<LabByIdDto> getAllLabsForTelegramId(int telegramId) {
        Group group = studentService.getStudentByChatId(telegramId).getGroup();
        List<Course> courseList = new ArrayList<>(courseService.getAllCoursesForGroupId(group));
        return labRepository.findAllByCourseIsIn(courseList).stream()
                .map(e -> LabByIdDto.builder()
                        .id(e.getId())
                        .description(e.getLabTheme())
                        .subjectId(e.getCourse().getSubject().getId())
                        .number(e.getLabNumber())
                        .docs(e.getDocuments())
                        .maxMark(e.getMaxMark())
                .build())
                .collect(Collectors.toList());
    }

    public List<LabByStudentIdDto> getLabListByStudentId(Integer studentId) {
        List<LabByIdDto> results = getAllLabsForTelegramId(studentService.getOne(studentId).getChatId());
        Student student = studentService.getOne(studentId);
        return results.stream()
                .map(labByIdDto -> {
                    String repoName = labRepository.findById(labByIdDto.getId())
                            .orElseThrow(NoSuchElementException::new)
                            .getRepoName();

                    Integer variant = studentService
                            .getStudentVariantByGithubIdAndLabRepoName(student.getGithubId(), repoName);

                    return LabByStudentIdDto.builder().id(labByIdDto.getId())
                            .description(labByIdDto.getDescription())
                            .number(labByIdDto.getNumber())
                            .variant(variant)
                            .subjectId(labByIdDto.getSubjectId())
                            .docs(labByIdDto.getDocs())
                            .maxMark(labByIdDto.getMaxMark())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public List<LabByStudentIdDto> getLabListByStudentIdAndSubjectId(Integer studentId, Integer subjectId) {
        Group group = studentService.getOne(studentId).getGroup();
        Subject subject = subjectRepository.getOne(subjectId);
        Course course = courseRepository.getByGroupAndSubject(group, subject);

        List<Lab> labs = labRepository.findAllByCourse(course);

        Student student = studentService.getOne(studentId);

        return labs.stream()
                .map(lab -> {
                    String repoName = lab.getRepoName();
                    Integer variant = studentService
                            .getStudentVariantByGithubIdAndLabRepoName(student.getGithubId(), repoName);

                    return LabByStudentIdDto.builder().id(lab.getId())
                            .description(lab.getLabTheme())
                            .number(lab.getLabNumber())
                            .variant(variant)
                            .subjectId(lab.getCourse().getSubject().getId())
                            .docs(lab.getDocuments())
                            .maxMark(lab.getMaxMark())
                            .build();

                })
                .collect(Collectors.toList());

    }

    public Integer getMaxMarkByLabRepoName(String repoName) {
        return labRepository
                .findByRepoName(repoName)
                .orElseThrow(NoSuchElementException::new)
                .getMaxMark();
    }

    public String getTestRepoNameByLab(String repoName) {
        return labRepository
                .findByRepoName(repoName)
                .orElseThrow(NoSuchElementException::new)
                .getTestRepoName();
    }

}
