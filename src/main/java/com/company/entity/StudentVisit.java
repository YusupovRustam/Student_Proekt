package com.company.entity;
import com.company.enums.VisitStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudent_Id() {
        return student_Id;
    }

    public void setStudent_Id(Long student_Id) {
        this.student_Id = student_Id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public VisitStatus getVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(VisitStatus visitStatus) {
        this.visitStatus = visitStatus;
    }

    private String getStringDate(LocalDateTime localDateTime){
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd.MM.YYYY  HH:mm");
        String format = localDateTime.format(dateTimeFormatter);
        return format;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", student_Id=" + student_Id +
                ", time=" + getStringDate(time) +
                ", visitStatus=" + visitStatus +
                '}';
    }
}
