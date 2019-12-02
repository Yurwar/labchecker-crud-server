package com.usb.labchecker.model.service;

import com.usb.labchecker.model.dto.LabResultByStudentIdDto;
import com.usb.labchecker.model.dto.LabResultDto;
import com.usb.labchecker.model.dto.LabResultTestingServerDto;
import com.usb.labchecker.model.entity.Lab;
import com.usb.labchecker.model.entity.LabResult;
import com.usb.labchecker.model.entity.Student;
import com.usb.labchecker.model.entity.Variant;
import com.usb.labchecker.model.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LabResultService {
    private final LabResultRepository labResultRepository;
    private final StudentRepository studentRepository;
    private final LabRepository labRepository;
    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;
    private final VariantRepository variantRepository;

    public LabResultService(LabResultRepository labResultRepository,
                            StudentRepository studentRepository,
                            LabRepository labRepository,
                            CourseRepository courseRepository,
                            SubjectRepository subjectRepository,
                            VariantRepository variantRepository) {
        this.labResultRepository = labResultRepository;
        this.studentRepository = studentRepository;
        this.labRepository = labRepository;
        this.courseRepository = courseRepository;
        this.subjectRepository = subjectRepository;
        this.variantRepository = variantRepository;
    }

    public LabResult getOne(int id) {
        return labResultRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Iterable<LabResult> getAllLabResults() {
        return labResultRepository.findAll();
    }

    public Set<LabResultByStudentIdDto> findLabResultsForStudent(Integer studentId) {

        return labResultRepository
                .findAllByStudent(studentRepository.getOne(studentId))
                .stream()
                .map(e -> LabResultByStudentIdDto.builder()
                        .id(e.getId())
                        .labId(e.getLab().getId())
                        .result(e.getMark())
                        .subjectId(e.getLab()
                                .getCourse()
                                .getSubject()
                                .getId())
                        .checkDateTime(e.getCheckDateTime())
                        .build())
                .collect(Collectors.toSet());
    }

    public Set<LabResultByStudentIdDto> getLabResultsByStudentIdAndSubjectId(Integer studentId,
                                                                             Integer subjectId) {
        List<LabResult> labResultListByStudent = labResultRepository
                .findAllByStudent(studentRepository.getOne(studentId));
        List<LabResult> labResultListBySubject = labResultRepository
                .findAllByLabIn(labRepository
                        .findAllByCourseIsIn(courseRepository
                                .findAllBySubject(subjectRepository
                                        .findById(subjectId)
                                        .orElseThrow(NoSuchElementException::new))));

        return labResultListByStudent.stream()
                .distinct()
                .filter(labResultListBySubject::contains)
                .map(e -> LabResultByStudentIdDto.builder()
                        .id(e.getId())
                        .labId(e.getLab().getId())
                        .result(e.getMark())
                        .subjectId(subjectRepository
                                .findById(subjectId)
                                .orElseThrow(NoSuchElementException::new)
                                .getId())
                        .checkDateTime(e.getCheckDateTime())
                        .build())
                .collect(Collectors.toSet());

    }

    public LabResult addLabResult(LabResultTestingServerDto labResultDto) {
        Lab lab = labRepository.findByRepoName(labResultDto.getRepositoryName())
                .orElseThrow(NoSuchElementException::new);
        Student student = studentRepository.findByGithubId(String.valueOf(labResultDto.getStudentGithubID()))
                .orElseThrow(NoSuchElementException::new);
        Variant variant = variantRepository.findByLabAndNumber(lab, labResultDto.getVariant())
                .orElseThrow(NoSuchElementException::new);

        LabResult labResultToAdd = LabResult.builder()
                .lab(lab)
                .githubRepositoryLink(labResultDto.getStudentGithubLogin() + "/" + labResultDto.getRepositoryName())
                .mark((double) labResultDto.getMark())
                .student(student)
                .variant(variant)
                .checkDateTime(LocalDateTime.now(ZoneId.of("Europe/Kiev")))
                .build();

        labResultRepository.save(labResultToAdd);
        return labResultToAdd;
    }
}
