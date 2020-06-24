package com.usb.labchecker.model.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Table(name = "courses")
@Entity
@Getter
@Data
public class Course {

    @Id
    @SequenceGenerator(name = "courseIdSeq", sequenceName = "course_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "courseIdSeq")
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_teacher")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "id_subject")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "id_group")
    private Group group;

    @Column(name = "googlesheet_link")
    private String googleSheetLink;
}
