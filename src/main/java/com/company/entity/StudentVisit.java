package com.company.entity;

import com.company.enums.VisitStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
@ToString
@Getter
@Setter
@Entity
@Table(name = "visit")
public class StudentVisit {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private Long student_Id;
    private LocalDateTime time;
    @Enumerated(value = EnumType.STRING)
    private VisitStatus visitStatus;

}
