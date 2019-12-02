package com.usb.labchecker.model.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Table(name = "teachers")
@Entity
@Data
@Getter
public class Teacher {

    @Id
    @SequenceGenerator(name = "teacherIdSeq", sequenceName = "teacher_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacherIdSeq")
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "telegram_id")
    private int telegramId;
}
