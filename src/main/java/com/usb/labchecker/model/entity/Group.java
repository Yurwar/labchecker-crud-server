package com.usb.labchecker.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "groups")
@Entity
public class Group {

    @Id
    @SequenceGenerator(name = "groupIdSeq", sequenceName = "group_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groupIdSeq")
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;
}
