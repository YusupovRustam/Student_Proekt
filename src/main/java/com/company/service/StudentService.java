package com.company.service;

import com.company.entity.Student;
import com.company.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    ExcelService excelService;

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

    public boolean isExists(Long id) {
        return studentRepository.countById(id) == 1;
    }

    public boolean checkPhone(Student student) {
        String phone = student.getPhone();
        int i = studentRepository.countByPhone(phone);
        if (i != 0 || phone.length() != 13 || !phone.startsWith("+998")) {
            return false;
        }
        char[] chars = phone.toCharArray();
        for (int j = 1; j < chars.length; j++) {
            if (!Character.isDigit(chars[j])) {
                return false;
            }
        }
        return true;
    }

    public boolean checkName(Student student) {
        return student.getName().length() > 2;
    }

    public ByteArrayInputStream getExcel(){
       return excelService.getExcel(getAll());
    }

    public List<Student> getAllnull(){
        return studentRepository.getAllBy();
    }


}
