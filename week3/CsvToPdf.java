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

/**
 * This class converts the csv file to pdf file
 */
public class CsvToPdf {

    private static final Logger logger = LogManager.getLogger(CsvToPdf.class.getName());//Used to save the logs to the log file

    /**
     * This method can be used to convert a csv file to pdf file
     * @param csvPath - used to take the path of csv file
     * @param pdfPath - used to take the path of pdf file
     * @throws IOException - thrown
     */
    public void csvToPdf(String csvPath,String pdfPath) throws IOException {
        long start = System.currentTimeMillis();
        CSVReader reader;
        logger.info("reading data from specified path i.e, {}", csvPath);
        try {
            reader = new CSVReader(new FileReader(csvPath));//reading csv file from given path
        } catch (FileNotFoundException e) {
            logger.error("{} occurred while reading file from {}", e, csvPath);
            throw new FileNotFoundException("File Path should be correct");
        }
        String[] nextLine;
        Document my_pdf_data = new Document();
        Rectangle rc = new Rectangle(8300f, 8000f);
        my_pdf_data.setPageSize(rc);
        logger.info("writing the pdf data to specified destination path i.e,{}", pdfPath);
        try {
            PdfWriter.getInstance(my_pdf_data, new FileOutputStream(pdfPath));//writing pdf file to given path
        } catch (DocumentException | FileNotFoundException e) {
            logger.error("{} occurred while writing file to {}", e, pdfPath);
            throw new FileNotFoundException("File Path should be correct");
        }
        my_pdf_data.open();
            String[] nextL = reader.readNext();
            PdfPTable my_first_table = new PdfPTable(nextL.length);
            PdfPCell table_cell;
            int i = 0;
            while (i < nextL.length) {
                table_cell = new PdfPCell(new Phrase(nextL[i]));
                my_first_table.addCell(table_cell);
                i++;
            }
            while ((nextLine = reader.readNext()) != null) {
                i = 0;
                while (i < nextL.length) {
                    table_cell = new PdfPCell(new Phrase(nextLine[i]));
                    my_first_table.addCell(table_cell);
                    i++;
                }
            }
            try {
                my_pdf_data.add(my_first_table);
            } catch (DocumentException e) {
                logger.error("{} occurred while writing file to {}", e, pdfPath);
            } finally {
                my_pdf_data.close();
            }
        long end = System.currentTimeMillis();
        logger.info("csvToPdf() is executed in {} ms", (end - start));
    }
}
