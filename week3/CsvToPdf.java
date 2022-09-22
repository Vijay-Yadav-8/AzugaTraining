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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * This class converts the csv file to pdf file
 */
public class CsvToPdf {

    private static final Logger logger = LogManager.getLogger(CsvToPdf.class.getName());
    public static void main(String[] args) throws IOException {
        CSVReader reader;
        try {
            reader = new CSVReader(new FileReader("/Users/azuga/Desktop/museum1.csv"));//reading csv file from given path
        } catch (FileNotFoundException e) {
            logger.error("Exception "+e.getMessage());
            throw new RuntimeException(e);
        }
        String[] nextLine;
        Document my_pdf_data = new Document();

        Rectangle rc = new Rectangle(8300f,8000f);
        my_pdf_data.setPageSize(rc);

        try {
            PdfWriter.getInstance(my_pdf_data, new FileOutputStream("/Users/azuga/Desktop/museum2.pdf"));//writing pdf file to given path
        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        my_pdf_data.open();
        PdfPTable my_first_table = new PdfPTable(78);
        PdfPCell table_cell;
            while ((nextLine = reader.readNext()) != null) {
                int i = 0;
                logger.debug("Reading from csv file "+ Arrays.toString(nextLine));
                while (i <= 77) {
                    table_cell = new PdfPCell(new Phrase(nextLine[i]));
                    my_first_table.addCell(table_cell);
                    i++;
                }
            }
        try {
            my_pdf_data.add(my_first_table);
        } catch (DocumentException e) {
            logger.error("Exception "+e.getMessage());
            throw new RuntimeException(e);
        }
        my_pdf_data.close();
        }
    }
