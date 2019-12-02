package com.usb.labchecker.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "lab_results")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class LabResult {

    @Id
    @JsonIgnore
    @SequenceGenerator(name = "labResultIdSeq", sequenceName = "lab_result_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "labResultIdSeq")
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_lab")
    private Lab lab;

    @ManyToOne
    @JoinColumn(name = "id_student")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "id_variant")
    private Variant variant;

    @Column(name = "github_repository_link")
    private String githubRepositoryLink;

    @Column(name = "mark")
    private Double mark;

    @Column(name = "check_date_time")
    private LocalDateTime localDateTime;
}
