package com.company.service;

import com.company.entity.Student;
import com.company.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    FileStorageService fileStorageService;


    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student getOne(Long id) {
        return studentRepository.findById(id).get();
    }

    public Student updete(Long id, Student student) {
        student.setId(id);
        return studentRepository.save(student);
    }

    public void delete(Long id) {
        fileStorageService.delete(id);
        studentRepository.deleteById(id);

    }

    public Student savePhoto(Long id, MultipartFile multipartFile) {
        Student student = studentRepository.findById(id).get();
        try {
            byte[] bytes = multipartFile.getBytes();
            student.setPhoto(bytes);
            save(student);
            fileStorageService.save(multipartFile, id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return student;
    }

    public int countByPhone(String phone) {
        return studentRepository.countByPhone(phone);
    }

    public int countById(Long id) {
        return studentRepository.countById(id);
    }

}
