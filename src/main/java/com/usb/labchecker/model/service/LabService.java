package com.usb.labchecker.model.service;

import com.usb.labchecker.model.entity.Course;
import com.usb.labchecker.model.entity.Group;
import com.usb.labchecker.model.entity.Lab;
import com.usb.labchecker.model.repository.LabRepository;
import com.usb.labchecker.model.repository.LabResultRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LabService {
    private final LabRepository labRepository;
    private final StudentService studentService;
    private final CourseService courseService;
    private final LabResultRepository labResultRepository;

    public LabService(LabRepository labRepository, StudentService studentService, CourseService courseService, LabResultRepository labResultRepository) {
        this.labRepository = labRepository;
        this.studentService = studentService;
        this.courseService = courseService;
        this.labResultRepository = labResultRepository;
    }

    public Lab getOne(int id) {
        return labRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Iterable<Lab> getAllLabs() {
        return labRepository.findAll();
    }

    public Iterable<Lab> getAllLabsForTelegramId(int telegramId) {
        List<Course> courseList = new ArrayList<>();
        Group group = studentService.getStudentByTelegramId(telegramId).getGroup();
        courseService.getAllCoursesForGroupId(group).forEach(courseList::add);
        return labRepository.findAllByCourseIsIn(courseList);
    }

    public List<Lab> getLabListByStudentId(Integer studentId) {
        List<Lab> resultList = new ArrayList<>();
        labResultRepository.findAllByStudent(studentService.getOne(studentId))
                .forEach(e -> resultList.add(e.getLab()));
        return resultList;
    }
}
