package com.company.service;

import com.company.entity.StudentVisit;
import com.company.enums.VisitStatus;
import com.company.repository.StudentVisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentVisitService {
    @Autowired
    StudentVisitRepository studentVisitRepository;

    public StudentVisit comingDateSave(StudentVisit studentVisit) {
        studentVisit.setTime(LocalDateTime.now());
        studentVisit.setVisitStatus(VisitStatus.COMING);
        studentVisitRepository.save(studentVisit);
        return studentVisit;
    }

    public StudentVisit leavingDateSave(StudentVisit studentVisit) {
        studentVisit.setTime(LocalDateTime.now());
        studentVisit.setVisitStatus(VisitStatus.LEAVING);
        studentVisitRepository.save(studentVisit);
        return studentVisit;
    }

    public List<StudentVisit> findAll() {
        return studentVisitRepository.findAll();
    }

    public List<String> findAllString() {
        List<StudentVisit> all = findAll();
        return all.stream().map((StudentVisit::toString)).collect(Collectors.toList());
    }
}
