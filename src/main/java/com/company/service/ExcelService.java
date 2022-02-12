package com.company.service;

import com.company.entity.Student;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
public class ExcelService {

    @Autowired
    StudentService studentService;

    public ByteArrayInputStream getExcel(List<Student> list) {
        FileInputStream file = null;
        ByteArrayInputStream byteArrayInputStream = null;

        try {
            file = new FileInputStream("excel/student1.xlsx");
            Workbook workbook = WorkbookFactory.create(file);
            CellStyle style = getStyle(workbook.createCellStyle());

            Sheet sheet1 = workbook.getSheet("Лист1");
            Row row = sheet1.createRow(3);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");
            LocalDate localDate = LocalDate.now();
            String format = localDate.format(dateTimeFormatter);
            row.createCell(1).setCellValue(format);
            int countRow = 8;
            for (Student student : list) {
                Row row1 = sheet1.createRow(countRow++);
                Cell cell = row1.createCell(1);
                cell.setCellValue(student.getId());
                cell.setCellStyle(style);

                Cell cell1 = row1.createCell(2);
                cell1.setCellValue(student.getName());
                cell1.setCellStyle(style);

                Cell cell2 = row1.createCell(3);
                cell2.setCellValue(student.getSurname());
                cell2.setCellStyle(style);

                Cell cell3 = row1.createCell(4);
                cell3.setCellValue(student.getAge());
                cell3.setCellStyle(style);

                Cell cell4 = row1.createCell(5);
                cell4.setCellValue(student.getCourse());
                cell4.setCellStyle(style);

                Cell cell5 = row1.createCell(6);
                cell5.setCellValue(student.getPhone());
                cell5.setCellStyle(style);

                Cell cell6 = row1.createCell(7);
                cell6.setCellValue(student.getAddress());
                cell6.setCellStyle(style);
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byteArrayInputStream=new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayInputStream;

    }

    public CellStyle getStyle(CellStyle cellStyle) {
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }
    private Workbook getWorkbook(String excelFilePath,InputStream inputStream)
            throws IOException {
        Workbook workbook = null;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    public void writeFromExcelToDB(MultipartFile multipartFile) throws IOException, InvalidFormatException {
        InputStream inputStream = multipartFile.getInputStream();
        Workbook workbook=getWorkbook(Objects.requireNonNull(multipartFile.getOriginalFilename()),inputStream);
        Sheet sheet = workbook.getSheet("Лист1");
        Iterator<Row> iterator = sheet.iterator();
        int count=1;
        while (iterator.hasNext()){
            if (count==1){
                iterator.next();
                iterator.next();
                iterator.next();
                count++;
            }
            Row row =iterator.next();
//

            Cell cell2 = row.getCell(2);
            String name = cell2.getStringCellValue();
            if (name.equals("")){
                break;
            }
            Cell cell3 = row.getCell(3);
            String surname = cell3.getStringCellValue();

            Cell cell4 = row.getCell(4);
            Double numericCellValue = cell4.getNumericCellValue();
            int age1 = numericCellValue.intValue();

            Cell cell5 = row.getCell(5);
            Double numericCellValue1 = cell5.getNumericCellValue();
            int course1 = numericCellValue1.intValue();

            Cell cell6 = row.getCell(6);
            String phone = cell6.getStringCellValue();

            Cell cell7 = row.getCell(7);
            String address = cell7.getStringCellValue();
              Student student=new Student();
              student.setName(name);
              student.setSurname(surname);
              student.setAge(age1);
              student.setAddress(address);
              student.setCourse(course1);
              student.setPhone(phone);
            if (studentService.checkName(student) && !studentService.checkPhone(student)){
                studentService.save(student);

            }else {
                ResponseEntity.ok("Phone or Name exist mistake ");
            }



        }

    }
}
