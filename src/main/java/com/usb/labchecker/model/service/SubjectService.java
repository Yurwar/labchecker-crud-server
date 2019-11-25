package com.usb.labchecker.model.service;

import com.usb.labchecker.model.dto.SubjectByStudentIdDto;
import com.usb.labchecker.model.entity.Course;
import com.usb.labchecker.model.entity.Student;
import com.usb.labchecker.model.entity.Subject;
import com.usb.labchecker.model.repository.CourseRepository;
import com.usb.labchecker.model.repository.LabResultRepository;
import com.usb.labchecker.model.repository.StudentRepository;
import com.usb.labchecker.model.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;
    private final LabResultRepository labResultRepository;
    private final CourseRepository courseRepository;

    public SubjectService(SubjectRepository subjectRepository,
                          StudentRepository studentRepository,
                          LabResultRepository labResultRepository,
                          CourseRepository courseRepository) {
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
        this.labResultRepository = labResultRepository;
        this.courseRepository = courseRepository;
    }

    public Subject getOne(int id) {
        return subjectRepository.findById(id).orElseThrow(NoSuchElementException::new);

    }

    public Iterable<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public List<SubjectByStudentIdDto> getSubjectsByStudentId(Integer studentId) {
        List<SubjectByStudentIdDto> subjectList = new ArrayList<>();
        Student student = studentRepository.getOne(studentId);
        List<Course> courses = courseRepository.getAllByGroup(student.getGroup());

        courses.forEach(course -> {
            Subject subject = course.getSubject();
            SubjectByStudentIdDto subjectByStudentIdDto = SubjectByStudentIdDto.builder()
                    .id(subject.getId())
                    .name(subject.getName())
                    .teacher(course.getTeacher().getFirstName() + " " + course.getTeacher().getLastName())
                    .build();

            subjectList.add(subjectByStudentIdDto);
        });

        return subjectList;

    }
}

