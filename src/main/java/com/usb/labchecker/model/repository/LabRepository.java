package com.usb.labchecker.model.repository;

import com.usb.labchecker.model.entity.Course;
import com.usb.labchecker.model.entity.Lab;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LabRepository extends CrudRepository<Lab, Integer> {
    List<Lab> findAllByCourseIsIn(List<Course> courseList);
    Optional<Lab> findByRepoName(String repoName);
    List<Lab> findAllByCourse(Course course);
}
