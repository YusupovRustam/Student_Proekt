package com.company.utils;

import com.company.entity.Student;
import com.company.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class StudentDataUtil {

    @Autowired
    StudentRepository studentRepository;


    public static boolean checkName(Student student) {
        return student.getName().length() > 2;
    }

    public static boolean checkPhone(Student student, int i) {
        String phone = student.getPhone();
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

}
