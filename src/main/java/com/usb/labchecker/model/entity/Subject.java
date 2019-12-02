package com.usb.labchecker.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Table(name = "subjects")
@Entity
@Data
@Getter
public class Subject {

    @Id
    @JsonIgnore
    @SequenceGenerator(name = "subjectIdSeq", sequenceName = "subject_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjectIdSeq")
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;
}
