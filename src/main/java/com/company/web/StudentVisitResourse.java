package com.company.web;

import com.company.entity.StudentVisit;
import com.company.service.StudentVisitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visit")
@Tag(name = "StudentVisit", description = "Studentlar keldi -ketdi ro'yhati")
public class StudentVisitResourse {

    @Autowired
    StudentVisitService studentVisitService;


    @Operation(summary = "Institutga kelgan vaqtini saqlash")
    @PostMapping("/coming")
    public ResponseEntity comingDatesave(@RequestBody StudentVisit studentVisit) {
        return ResponseEntity.ok(studentVisitService.comingDateSave(studentVisit).toString());
    }

    @Operation(summary = "Institutni tark etgan vaqtini saqlash")
    @PostMapping("/leaving")
    public ResponseEntity leavingDatesave(@RequestBody StudentVisit studentVisit) {
        return ResponseEntity.ok(studentVisitService.leavingDateSave(studentVisit).toString());
    }

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(studentVisitService.findAllString());
    }


}
