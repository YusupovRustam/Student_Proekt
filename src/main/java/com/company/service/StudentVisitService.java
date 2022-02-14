package com.company.service;

import com.company.config.MessagingCopnfig;
import com.company.entity.StudentVisit;
import com.company.enums.VisitStatus;
import com.company.repository.StudentVisitRepository;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentVisitService {
    @Autowired
    StudentVisitRepository studentVisitRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    public StudentVisit comingDateSave(StudentVisit studentVisit) {
        studentVisit.setTime(LocalDateTime.now());
        studentVisit.setVisitStatus(VisitStatus.COMING);
        // StudentVisit savedVisit = studentVisitRepository.save(studentVisit);
        Gson gson = new Gson();
        String s = gson.toJson(studentVisit, StudentVisit.class);
        rabbitTemplate.convertAndSend(MessagingCopnfig.EXCHANGEUSER1, MessagingCopnfig.EXCHANGEUSER1, s);

        return studentVisit;
    }

    public StudentVisit leavingDateSave(StudentVisit studentVisit) {
        studentVisit.setTime(LocalDateTime.now());
        studentVisit.setVisitStatus(VisitStatus.LEAVING);
        //  StudentVisit save = studentVisitRepository.save(studentVisit);
        Gson gson = new Gson();
        String s = gson.toJson(studentVisit);
        rabbitTemplate.convertAndSend(MessagingCopnfig.EXCHANGEUSER1, MessagingCopnfig.EXCHANGEUSER1, s);


        return studentVisit;
    }

    public List<StudentVisit> findAll() {
        return studentVisitRepository.findAll();
    }

    public List<String> findAllString() {
        List<StudentVisit> all = findAll();
        return all.stream().map((StudentVisit::toString)).collect(Collectors.toList());
    }

    public List<StudentVisit> findAllByVisitStatus(VisitStatus status) {

        return studentVisitRepository.findAllByVisitStatus(status);
    }

    @Scheduled(cron = "0 0 18 * * *")
    public void updateAllLeavingStudents() {
        List<StudentVisit> allByVisitStatus = findAllByVisitStatus(VisitStatus.COMING);
        allByVisitStatus.forEach((studentVisit -> {
            studentVisit.setVisitStatus(VisitStatus.LEAVING);
        }));
        studentVisitRepository.saveAll(allByVisitStatus);


    }
}
