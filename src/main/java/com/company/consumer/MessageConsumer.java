package com.company.consumer;

import com.company.entity.StudentVisit;
import com.company.enums.VisitStatus;
import com.company.repository.StudentRepository;
import com.company.repository.StudentVisitRepository;
import com.company.service.StudentService;
import com.company.service.StudentVisitService;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MessageConsumer {

    @Autowired
    StudentVisitRepository studentVisitRepository;

    @RabbitListener(queues = "student")
    public void receive(String message) {
        Gson gson = new Gson();
        StudentVisit studentVisit = gson.fromJson(message, StudentVisit.class);
        ;
        studentVisitRepository.save(studentVisit);

    }
}
