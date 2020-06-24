package com.usb.labchecker.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "variants")
@Entity
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Variant {

    @Id
    @SequenceGenerator(name = "variantIdSeq", sequenceName = "variant_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "variantIdSeq")
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_lab")
    private Lab lab;

    @Column(name = "number")
    private int number;

    @Column(name = "testfile_path")
    private String testfilePath;
}
