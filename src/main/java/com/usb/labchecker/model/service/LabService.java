package com.usb.labchecker.model.service;

import com.usb.labchecker.model.dto.LabByIdDto;
import com.usb.labchecker.model.dto.LabByStudentIdDto;
import com.usb.labchecker.model.entity.Course;
import com.usb.labchecker.model.entity.Group;
import com.usb.labchecker.model.entity.Lab;
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

    public LabService(LabRepository labRepository,
                      StudentService studentService,
                      CourseService courseService,
                      LabResultRepository labResultRepository,
                      SubjectRepository subjectRepository,
                      CourseRepository courseRepository) {
        this.labRepository = labRepository;
        this.studentService = studentService;
        this.courseService = courseService;
        this.labResultRepository = labResultRepository;
        this.subjectRepository = subjectRepository;
        this.courseRepository = courseRepository;


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
                .build())
                .collect(Collectors.toList());
    }

    public List<LabByStudentIdDto> getLabListByStudentId(Integer studentId) {
        List<LabByIdDto> results = getAllLabsForTelegramId(studentService.getOne(studentId).getChatId());
        return results.stream()
                .map(labByIdDto -> LabByStudentIdDto.builder().id(labByIdDto.getId())
                .description(labByIdDto.getDescription())
                .number(labByIdDto.getNumber())
                .subjectId(labByIdDto.getSubjectId())
                .build())
                .collect(Collectors.toList());
    }

    public List<LabByStudentIdDto> getLabListByStudentIdAndSubjectId(Integer studentId, Integer subjectId) {
        List<Lab> labListByStudents = new ArrayList<>();
        labResultRepository.findAllByStudent(studentService.getOne(studentId))
                .forEach(e -> labListByStudents.add(e.getLab()));

        List<Lab> labListBySubjects = labRepository.findAllByCourseIsIn(courseRepository.findAllBySubject(subjectRepository
                .findById(subjectId)
                .orElseThrow(NoSuchElementException::new)));

        return labListByStudents.stream()
                .distinct()
                .filter(labListBySubjects::contains)
                .map(e -> LabByStudentIdDto.builder()
                            .description(e.getLabTheme())
                            .id(e.getId())
                            .number(e.getLabNumber())
                            .subjectId(subjectId)
                            .build())
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
