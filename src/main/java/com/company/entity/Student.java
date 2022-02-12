package com.company.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "student3")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private int age;
    private int course;
    private String phone;
    private String address;
    @Lob
    private byte[] photo;

}
