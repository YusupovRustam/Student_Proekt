package com.company.web;

import com.company.entity.Student;
import com.company.service.ExcelService;
import com.company.service.PDFService;
import com.company.service.StudentService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;


@RequestMapping("/student")
@RestController
@Tag(name = "StudentResourse",description = "Studentlar hisoboti")
public class StudentResourse {

    @Autowired
    StudentService studentService;

    @Autowired
    PDFService pdfService;

    @Autowired
    ExcelService excelService;

      @Operation(summary = "Yangi student yaratish")
    @PostMapping
    public ResponseEntity save(@RequestBody Student student) {
        if (!studentService.checkName(student) || !studentService.checkPhone(student)){
            return ResponseEntity.ok("Phone or Name exist mistake ");
        }
        Student student1 = studentService.save(student);
        return ResponseEntity.ok(student1);
    }
@Operation( summary= "StudentLar royhatini olish")
@ApiResponses(value = {
        @ApiResponse(code = 200,message ="Seccess|OK" ),
        @ApiResponse(code = 401,message ="Avtorizatsiyadan o'tmagan" ),
        @ApiResponse(code = 403,message ="Forbidden!!!!!!!!!" ),
        @ApiResponse(code = 404,message ="not found!!!!" )
})
    @GetMapping
    public ResponseEntity getAll() {

        return ResponseEntity.ok(studentService.getAll());
    }
@Operation(summary = "Id boyicha studentni olish")
    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable Long id) {
        if (!studentService.isExists(id)){
            return ResponseEntity.ok("Student topilmadi");
        }
        Student student = studentService.getOne(id);
        return ResponseEntity.ok(student);
    }
    @Operation(summary = "Id bo'yicha studentni rasmini olish")
    @GetMapping("/photo/{id}")
    public HttpEntity<byte[]> getArticleImage(@PathVariable Long id) {
        byte[] image = studentService.getOne(id).getPhoto();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(image.length);
        return new HttpEntity<>(image, headers);
    }
    @Operation(summary = "Id boyicha studentni o'zgartirish")
    @PutMapping("update/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Student student) {
        if (!studentService.isExists(id)){
            return ResponseEntity.ok("Student topilmadi");
        }
        return ResponseEntity.ok(studentService.updete(id, student));
    }
    @Operation(summary = "Id boyicha studentni o'chirish")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (!studentService.isExists(id)){
            return ResponseEntity.ok("Student topilmadi");
        }
        studentService.delete(id);
        return ResponseEntity.ok("Ochirildi");
    }
    @Operation(summary = "Id boyicha studentga rasm saqlash")
    @PutMapping("/savephoto/{id}")
    public ResponseEntity savePhoto(@PathVariable Long id, @RequestParam("file") MultipartFile multipartFile) {
        if (!studentService.isExists(id)){
            return ResponseEntity.ok("Student topilmadi");
        }
        return ResponseEntity.ok(studentService.savePhoto(id, multipartFile));
    }
    @Operation(summary = "Studentlar ro'yhatini excel file da download qilish ")
    @GetMapping("/download/student.xlsx")
    public void downloadExc(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=student1.xlsx");

        ByteArrayInputStream stream =excelService.getExcel(studentService.getAll());
        IOUtils.copy(stream, response.getOutputStream());
    }
    @Operation(summary = "Student resumesi Id bo'yicha pdf formatda ")
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

    @Operation(summary = "Excel file dan Db ga student malumotlarini yozish")
    @PostMapping("/excel/writeDB")
    public ResponseEntity writeFromExcelToDB(@RequestParam("file") MultipartFile multipartFile){
        try {
         excelService.writeFromExcelToDB(multipartFile);
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("DB ga yozildi");
    }




}
