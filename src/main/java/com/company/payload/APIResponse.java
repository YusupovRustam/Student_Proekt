package com.company.payload;

import com.company.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class APIResponse {
    private String message;
    private List<Student> students;
}
