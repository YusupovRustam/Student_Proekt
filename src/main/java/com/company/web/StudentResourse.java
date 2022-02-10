package com.company.web;

import com.company.entity.Student;
import com.company.service.PDFService;
import com.company.service.StudentService;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;


@RestController
@RequestMapping("/student")
public class StudentResourse {

    @Autowired
    StudentService studentService;

    @Autowired
    PDFService pdfService;


    @PostMapping
    public ResponseEntity save(@RequestBody Student student) {
        if (!studentService.checkName(student) || !studentService.checkPhone(student)){
            return ResponseEntity.ok("Phone or Name exist mistake ");
        }
        Student student1 = studentService.save(student);
        return ResponseEntity.ok(student1);
    }

    @GetMapping
    public ResponseEntity getAll() {

        return ResponseEntity.ok(studentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable Long id) {
        if (!studentService.isExists(id)){
            return ResponseEntity.ok("Student topilmadi");
        }
        Student student = studentService.getOne(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/photo/{id}")
    public HttpEntity<byte[]> getArticleImage(@PathVariable Long id) {
        byte[] image = studentService.getOne(id).getPhoto();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(image.length);
        return new HttpEntity<>(image, headers);
    }

    @PutMapping("update/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Student student) {
        if (!studentService.isExists(id)){
            return ResponseEntity.ok("Student topilmadi");
        }
        return ResponseEntity.ok(studentService.updete(id, student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (!studentService.isExists(id)){
            return ResponseEntity.ok("Student topilmadi");
        }
        studentService.delete(id);
        return ResponseEntity.ok("Ochirildi");
    }

    @PutMapping("/savephoto/{id}")
    public ResponseEntity savePhoto(@PathVariable Long id, @RequestParam("file") MultipartFile multipartFile) {
        if (!studentService.isExists(id)){
            return ResponseEntity.ok("Student topilmadi");
        }
        return ResponseEntity.ok(studentService.savePhoto(id, multipartFile));
    }

    @GetMapping("/download/student.xlsx")
    public void downloadExc(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=student.xlsx");
        ByteArrayInputStream stream =studentService.getExcel();
        IOUtils.copy(stream, response.getOutputStream());
    }

    @GetMapping("/download/pdf/{id}")
    public void downloadPdf(HttpServletResponse response,@PathVariable Long id) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=student.pdf");
        this.pdfService.Export(response,id);
    }

    @GetMapping("/null")
    public ResponseEntity getAllNull(){
        return ResponseEntity.ok(studentService.getAllnull());
    }



}
