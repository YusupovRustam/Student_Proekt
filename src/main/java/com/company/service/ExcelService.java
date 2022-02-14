package com.company.service;

import com.company.entity.Student;
import com.company.payload.APIResponse;
import com.company.utils.StudentDataUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@Service
public class ExcelService {

    @Autowired
    StudentService studentService;

    public ByteArrayInputStream writeDBFromExcel(List<Student> list) {
        FileInputStream file = null;
        ByteArrayInputStream byteArrayInputStream = null;

        try {
            file = new FileInputStream("excel/student1.xlsx");
            Workbook workbook = WorkbookFactory.create(file);
            CellStyle style = getStyle(workbook.createCellStyle());

            Sheet sheet1 = workbook.getSheetAt(0);
            Row row = sheet1.createRow(0);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");
            LocalDate localDate = LocalDate.now();
            String format = localDate.format(dateTimeFormatter);
            row.createCell(1).setCellValue(format);
            int countRow = 3;
            for (Student student : list) {
                Row row1 = sheet1.createRow(countRow++);
                Cell cell = row1.createCell(0);
                cell.setCellValue(student.getId());
                cell.setCellStyle(style);

                Cell cell1 = row1.createCell(1);
                cell1.setCellValue(student.getName());
                cell1.setCellStyle(style);

                Cell cell2 = row1.createCell(2);
                cell2.setCellValue(student.getSurname());
                cell2.setCellStyle(style);

                Cell cell3 = row1.createCell(3);
                cell3.setCellValue(student.getAge());
                cell3.setCellStyle(style);

                Cell cell4 = row1.createCell(4);
                cell4.setCellValue(student.getCourse());
                cell4.setCellStyle(style);

                Cell cell5 = row1.createCell(5);
                cell5.setCellValue(student.getPhone());
                cell5.setCellStyle(style);

                Cell cell6 = row1.createCell(6);
                cell6.setCellValue(student.getAddress());
                cell6.setCellStyle(style);
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byteArrayInputStream = new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayInputStream;

    }


    public ResponseEntity writeFromExcelToDB(MultipartFile multipartFile) {
        List<Student> list = new LinkedList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow rowFirst = sheet.getRow(2);

            for (int i = 3; i < sheet.getLastRowNum(); i++) {
                Student student = getCellData(rowFirst.getLastCellNum(), sheet.getRow(i), rowFirst);
                if (StudentDataUtil.checkName(student) &&
                        StudentDataUtil.checkPhone(student, studentService.countByPhone(student.getPhone()))) {
                    studentService.save(student);

                } else {
                    list.add(student);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list.size() > 0) {
            return ResponseEntity.status(203).body(new APIResponse("Quyidagilar db ga saqlanmadi", list));
        } else {
            return ResponseEntity.ok("Hammasi db ga saqlandi");
        }
    }

    private Student getCellData(int lastCellNum, XSSFRow row, XSSFRow rowFirst) {
        Student student = new Student();
        for (int i = 1; i < lastCellNum; i++) {
            if (row.getCell(i).getCellType().equals(CellType.NUMERIC)) {
                if (rowFirst.getCell(i).getStringCellValue().trim().equals("age")) {
                    Double d = row.getCell(i).getNumericCellValue();
                    int age = d.intValue();
                    student.setAge(age);

                } else if (rowFirst.getCell(i).getStringCellValue().trim().equals("course")) {
                    Double d = row.getCell(i).getNumericCellValue();
                    int course = d.intValue();
                    student.setCourse(course);

                }
            }
            if (row.getCell(i).getCellType().equals(CellType.STRING)) {
                if (rowFirst.getCell(i).getStringCellValue().trim().equals("name")) {
                    String name = row.getCell(i).getStringCellValue();
                    student.setName(name);

                } else if (rowFirst.getCell(i).getStringCellValue().trim().equals("surname")) {
                    String surname = row.getCell(i).getStringCellValue();
                    student.setSurname(surname);

                } else if (rowFirst.getCell(i).getStringCellValue().trim().equals("phone")) {
                    String phone = row.getCell(i).getStringCellValue();
                    student.setPhone(phone);

                } else if (rowFirst.getCell(i).getStringCellValue().trim().equals("address")) {
                    String address = row.getCell(i).getStringCellValue();
                    student.setAddress(address);
                }

            }

        }
        return student;

    }

    public CellStyle getStyle(CellStyle cellStyle) {
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }


}
