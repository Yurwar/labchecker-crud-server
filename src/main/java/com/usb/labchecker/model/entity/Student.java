package com.usb.labchecker.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Builder
@Table(name = "students")
@Entity
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @JsonIgnore
    @SequenceGenerator(name = "studentIdSeq", sequenceName = "student_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "studentIdSeq")
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "id_group")
    private Group group;

    @Column(name = "github_link")
    private String githubLink;

    @Column(name = "github_id")
    private String githubId;

    @Column(name = "chat_id")
    private Integer chatId;

}
