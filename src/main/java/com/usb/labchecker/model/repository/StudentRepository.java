package com.usb.labchecker.model.repository;

import com.usb.labchecker.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> getByChatId(int chatId);
    Optional<Student> findByGithubId(String githubId);
    Student findByGithubLink(String githubId);
}