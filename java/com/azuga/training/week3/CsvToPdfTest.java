/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training.week3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.pdfunit.AssertThat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the methods of CsvToPdf class
 */
class CsvToPdfTest {
    private static final Logger logger = LogManager.getLogger(CsvToPdfTest.class.getName());//Used to save the logs to the log file
    CsvToPdf csvToPdf ;
    @BeforeEach
    void init(){
        logger.trace("creating object for CsvToPdf class");
        csvToPdf = new CsvToPdf();
    }
    /**
     *This method test number of pages of pdf file.
     */
    @Test
    void testPdfForPages() {
        long start = System.currentTimeMillis();
        logger.info("Invoking test pdf for pages");
        try {
            csvToPdf.csvToPdf("/Users/azuga/Desktop/fakeCSV.csv", "/Users/azuga/Desktop/fakePDF.pdf");
            AssertThat.document("/Users/azuga/Desktop/fakePDF.pdf").hasNumberOfPages(1);
        } catch (IOException e) {
            logger.error("{} occurred while reading the files from paths {} , {}",e.getMessage(),"/Users/azuga/Desktop/fakePDF.pdf","/Users/azuga/Desktop/truth/fakePDF.pdf");
        }
        logger.info("Test case passed - pdf pages");
        long end = System.currentTimeMillis();
        logger.info("test_museum_CsvToPdf() is executed in {} ms", (end - start));
    }
    /**
     *This method test content of pdf file.
     */
    @Test
    void testPdfForContent() {
        long start = System.currentTimeMillis();
        logger.info("Invoking test pdf for content");
        try {
            csvToPdf.csvToPdf("/Users/azuga/Desktop/fakeCSV.csv", "/Users/azuga/Desktop/fakePDF.pdf");
            AssertThat.document("/Users/azuga/Desktop/truth/fakePDF.pdf").and("/Users/azuga/Desktop/fakePDF.pdf").haveSameText();
        } catch (IOException e) {
            logger.error("{} occurred while reading the files from paths {} , {}",e.getMessage(),"/Users/azuga/Desktop/fakePDF.pdf","/Users/azuga/Desktop/truth/fakePDF.pdf");
        }
        logger.info("Test case passed - pdf content matched");
        long end = System.currentTimeMillis();
        logger.info("test_museum_CsvToPdf() is executed in {} ms", (end - start));
    }
    /**
     *This method test number of pages of pdf file.
     */
    @Test
    void test_museum_PdfForPages() {
        long start = System.currentTimeMillis();
        logger.info("Invoking test pdf for pages");
        try {
            csvToPdf.csvToPdf("/Users/azuga/Desktop/museumCSV.csv", "/Users/azuga/Desktop/museumPDF.pdf");
            AssertThat.document("/Users/azuga/Desktop/museumPDF.pdf").hasNumberOfPages(1);
        } catch (IOException e) {
            logger.error("{} occurred while reading the files from paths {} , {}",e.getMessage(),"/Users/azuga/Desktop/museumPDF.pdf","/Users/azuga/Desktop/truth/museumPDF.pdf");
        }
        logger.info("Test case passed - pdf pages");
        long end = System.currentTimeMillis();
        logger.info("test_museum_CsvToPdf() is executed in {} ms", (end - start));
    }
    /**
     *This method test content of pdf file.
     */
    @Test
    void test_museum_PdfForContent() {
        long start = System.currentTimeMillis();
        logger.info("Invoking test pdf for content");
        try {
            csvToPdf.csvToPdf("/Users/azuga/Desktop/museumCSV.csv", "/Users/azuga/Desktop/museumPDF.pdf");
            AssertThat.document("/Users/azuga/Desktop/truth/museumPDF.pdf").and("/Users/azuga/Desktop/museumPDF.pdf").haveSameText();
        } catch (IOException e) {
            logger.error("{} occurred while reading the files from paths {} , {}",e.getMessage(),"/Users/azuga/Desktop/museumPDF.pdf","/Users/azuga/Desktop/truth/museumPDF.pdf");
        }
        logger.info("Test case passed - pdf content matched");
        long end = System.currentTimeMillis();
        logger.info("test_museum_CsvToPdf() is executed in {} ms", (end - start));
    }

    /**
     * Method under test: {@link CsvToPdf#csvToPdf(String, String)}
     * This method is used to test for museum api csv file and pdf file
     */
    @Test
    @Disabled("because of pdf content matching is incorrect")
    void test_museum_CsvToPdf() {
        long start = System.currentTimeMillis();
        try {
            csvToPdf.csvToPdf("/Users/azuga/Desktop/museumCSV.csv", "/Users/azuga/Desktop/museumPDF.pdf");
            byte[] expected = Files.readAllBytes(Path.of("/Users/azuga/Desktop/museumPDF.pdf"));
            byte[] actual = Files.readAllBytes(Path.of("/Users/azuga/Desktop/truth/museumPDF.pdf"));
            assertArrayEquals(expected,actual);
        } catch (IOException e) {
            logger.error("{} occurred while reading the files from paths {} , {}",e.getMessage(),"/Users/azuga/Desktop/museumPDF.pdf","/Users/azuga/Desktop/truth/museumPDF.pdf");
        }
        long end = System.currentTimeMillis();
        logger.info("test_museum_CsvToPdf() is executed in {} ms", (end - start));
    }
    /**
     * Method under test: {@link CsvToPdf#csvToPdf(String, String)}
     * This method is used to test for fake store api csv file and pdf file
     */
    @Test
    @Disabled("because of pdf content matching is incorrect")
    void test_fake_CsvToPdf() {
        long start = System.currentTimeMillis();
        try {
            csvToPdf.csvToPdf("/Users/azuga/Desktop/fakeCSV.csv", "/Users/azuga/Desktop/fakePDF.pdf");
            byte[] expected = Files.readAllBytes(Path.of("/Users/azuga/Desktop/fakePDF.pdf"));
            byte[] actual = Files.readAllBytes(Path.of("/Users/azuga/Desktop/truth/fakePDF.pdf"));
            assertArrayEquals(expected,actual);
        } catch (IOException e) {
            logger.error("{} occurred while reading the files from paths {} , {}",e.getMessage(),"/Users/azuga/Desktop/museumPDF.pdf","/Users/azuga/Desktop/truth/museumPDF.pdf");
        }
        long end = System.currentTimeMillis();
        logger.info("test_fake_CsvToPdf() is executed in {} ms", (end - start));
    }
    @Nested
    class CsvToPdfExceptions{
        /**
         * Method under test: {@link CsvToPdf#csvToPdf(String, String)}
         * This method is used to test whether NullPointerException is thrown or not
         */
        @Test
        void test_for_NPE_CsvToPdf(){
            long start = System.currentTimeMillis();
            assertThrows(NullPointerException.class,()->csvToPdf.csvToPdf(null, null));
            long end = System.currentTimeMillis();
            logger.info("test_for_NPE_CsvToPdf() is executed in {} ms", (end - start));
        }
        /**
         * Method under test: {@link CsvToPdf#csvToPdf(String, String)}
         * This method is used to test whether NullPointerException is thrown or not
         */
        @Test
        void test_for_NPE_CsvToPdf1(){
            long start = System.currentTimeMillis();
            assertThrows(NullPointerException.class,()->csvToPdf.csvToPdf("", ""));
            long end = System.currentTimeMillis();
            logger.info("test_for_NPE_CsvToPdf1() is executed in {} ms", (end - start));
        }
        /**
         * Method under test: {@link CsvToPdf#csvToPdf(String, String)}
         * This method is used to test whether NullPointerException is thrown or not
         */
        @Test
        void test_for_FNF_CsvToPdf(){
            long start = System.currentTimeMillis();
            assertThrows(FileNotFoundException.class,()->csvToPdf.csvToPdf("/Users/azuga/Downloads/fakeCSV.csv", "/Users/azuga/Downloads/fakeCSV.csv"));
            long end = System.currentTimeMillis();
            logger.info("test_for_FNF_CsvToPdf() is executed in {} ms", (end - start));
        }
    }
}

