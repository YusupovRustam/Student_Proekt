package com.company.service;

import com.company.entity.Student;
import com.lowagie.text.*;

import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;


@Service
public class PDFService {
    @Autowired
    StudentService service;

    public void Export(HttpServletResponse response, Long id) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Paragraph paragraphTitle = new Paragraph("RESUME", fontTitle);
        paragraphTitle.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraphTitle);
        Student student = service.getOne(id);
        LinkedList<String> list = new LinkedList<>();
        list.add("Name: " + student.getName());
        list.add("SurName: " + student.getSurname());
        list.add(String.format("%-35s%s", "Age: " + student.getAge(), "\tMalumoti: Oliy"));
        list.add(String.format("%-35s%s", "Address: " + student.getAddress(), "\tTil: English"));
        list.add(String.format("%-35s%s", "Phone: " + student.getPhone(), "Hobbiy: Suzish"));

        Image instance = null;
        try {
            instance = Image.getInstance(student.getPhoto());
            // instance = Image.getInstance("D:\\Rasmlar\\Rustam.jpg");
            instance.setAlignment(Element.ALIGN_RIGHT);
            instance.setBorderWidth(1.5f);
            instance.scaleAbsoluteHeight(50f);
            instance.scaleAbsoluteWidth(50);
            document.add(instance);
        } catch (IOException e) {
            e.printStackTrace();
        }


        list.forEach((i) -> {

            Font fontParagrafX = FontFactory.getFont(FontFactory.HELVETICA);
            fontParagrafX.setSize(12);
            Paragraph paragraphX = new Paragraph(i, fontParagrafX);
            paragraphX.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(paragraphX);
            //  document.add(paragraphY);
        });
        Font fontParagrafZ = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontParagrafZ.setSize(12);
        Paragraph paragraphZ = new Paragraph("JADVAL: ", fontParagrafZ);

        paragraphZ.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraphZ);


        PdfPTable pdfPTable = new PdfPTable(3);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setSpacingBefore(15);
        pdfPTable.setWidths(new float[]{1f, 3.5f, 6f});

        writeTableHeader(pdfPTable);
        writeTableData(pdfPTable);

        document.add(pdfPTable);

        document.close();
    }


    private void writeTableHeader(PdfPTable table) {
        PdfPCell pdfPCell = new PdfPCell();
        pdfPCell.setBackgroundColor(Color.BLUE);
        pdfPCell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.white);

        pdfPCell.setPhrase(new Paragraph("Soni", font));
        table.addCell(pdfPCell);

        pdfPCell.setPhrase(new Paragraph("Davr", font));
        table.addCell(pdfPCell);

        pdfPCell.setPhrase(new Paragraph("Ish joyi", font));
        table.addCell(pdfPCell);
    }

    private void writeTableData(PdfPTable table) {
        table.addCell("1");
        table.addCell("2018-2021");
        table.addCell("Temir yo'l");
        table.addCell("2");
        table.addCell("2021-2022");
        table.addCell("PDP");


    }


}
