package com.company.web;

import com.company.entity.StudentVisit;
import com.company.service.StudentVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visit")
public class StudentVisitResourse {

    @Autowired
    StudentVisitService studentVisitService;

    @PostMapping("/coming")
    public ResponseEntity comingDatesave(@RequestBody StudentVisit studentVisit){
        return ResponseEntity.ok(studentVisitService.comingDateSave(studentVisit).toString());
    }

    @PostMapping("/leaving")
    public ResponseEntity leavingDatesave(@RequestBody StudentVisit studentVisit){
        return ResponseEntity.ok(studentVisitService.leavingDateSave(studentVisit).toString());
    }

    @GetMapping
    public ResponseEntity getAll(){
        return ResponseEntity.ok(studentVisitService.findAllString());
    }


}
