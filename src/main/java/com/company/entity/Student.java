package com.company.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "student3")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "Name cannot empty")
    @Size(min = 2, max = 35, message = "name 2 va 35 oralig'ida bo'sin")
    private String name;
    @NotBlank(message = "Name cannot empty")
    @Size(min = 2, max = 35, message = "name 2 va 35 oralig'ida bo'sin")
    private String surname;
    @Size(min = 17, max = 50, message = "age 17 dan 50 gacha bo'lishi kerak")
    private int age;
    @NotBlank(message = "Course cannot empty")
    @Size(min = 1, max = 5, message = "course 2 va 5 oralig'ida bo'sin")
    private int course;

    @Size(min = 13, max = 13)
    private String phone;
    private String address;
    @Lob
    private byte[] photo;

}
