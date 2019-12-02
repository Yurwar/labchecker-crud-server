package com.usb.labchecker.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Table(name = "labs")
@Entity
@Data
@Getter
public class Lab {

    @Id
    @JsonIgnore
    @SequenceGenerator(name = "labIdSeq", sequenceName = "lab_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "labIdSeq")
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_course")
    private Course course;

    @Column(name = "number")
    private Integer labNumber;

    @Column(name = "max_mark")
    private Integer maxMark;

    @Column(name = "theme")
    private String labTheme;

    @Column(name = "repo_name")
    private String repoName;

    @Column(name = "test_repo_name")
    private String testRepoName;

    @OneToMany
    private List<Document> documents;
}
