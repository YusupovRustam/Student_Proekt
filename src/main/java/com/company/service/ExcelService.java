package com.company.service;

import com.company.entity.Student;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import javax.swing.text.Style;
import java.io.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExcelService {

    public ByteArrayInputStream getExcel(List<Student> list) {
        FileInputStream file = null;
        ByteArrayInputStream byteArrayInputStream = null;

        try {
            file = new FileInputStream("excel/student_hisoboti.xlsx");
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
            FileOutputStream fileOutputStream = new FileOutputStream("excel/student.xlsx");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.writeTo(fileOutputStream);
            workbook.write(outputStream);
            byteArrayInputStream = new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayInputStream;

    }

    public CellStyle getStyle(CellStyle cellStyle) {
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }
}
