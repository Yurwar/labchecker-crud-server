package com.usb.labchecker.model.repository;

import com.usb.labchecker.model.entity.Lab;
import com.usb.labchecker.model.entity.LabResult;
import com.usb.labchecker.model.entity.Student;
import com.usb.labchecker.model.entity.Variant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LabResultRepository extends CrudRepository<LabResult, Integer> {
    List<LabResult> findAllByStudent(Student student);
    List<LabResult> findAllByLabIn(List<Lab> labList);
    List<LabResult> findAllByStudentAndLab(Student student, Lab lab);
    Optional<LabResult> findByLabAndStudentAndVariant(Lab lab, Student student, Variant variant);
}
