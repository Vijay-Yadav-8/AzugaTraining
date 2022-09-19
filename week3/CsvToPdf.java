/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training.week3;

import au.com.bytecode.opencsv.CSVReader;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class converts the csv file to pdf file
 */
public class CsvToPdf {
    public static void main(String[] args) throws IOException, DocumentException {
        CSVReader reader = new CSVReader(new FileReader("/Users/azuga/Desktop/museum1.csv"));//reading csv file from given path
        String[] nextLine;
        Document my_pdf_data = new Document();

        Rectangle rc = new Rectangle(8300f,8000f);
        my_pdf_data.setPageSize(rc);


        PdfWriter.getInstance(my_pdf_data, new FileOutputStream("/Users/azuga/Desktop/museum2.pdf"));//writing pdf file to given path
        my_pdf_data.open();
        PdfPTable my_first_table = new PdfPTable(78);
//        float[] columnWidths = new float[77];
//        for (int j = 1; j <= 77; j++) {
//            columnWidths[j] = (float) j * 2;
//        }
//            my_first_table.setWidths(columnWidths);
            PdfPCell table_cell;
            while ((nextLine = reader.readNext()) != null) {
                int i = 0;
                while (i <= 77) {
                    table_cell = new PdfPCell(new Phrase(nextLine[i]));
                    my_first_table.addCell(table_cell);
                    i++;
                }
            }
            my_pdf_data.add(my_first_table);
            my_pdf_data.close();
        }
    }
