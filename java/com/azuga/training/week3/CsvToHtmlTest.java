/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training.week3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This class tests the methods of CsvToHtml class
 */
class CsvToHtmlTest {
    private static final Logger logger = LogManager.getLogger(CsvToHtml.class.getName());//Used to save the logs to the log file
    /**
     * Method under test: {@link CsvToHtml#csvToHtml(String, String)}
     * This method can be used to test whether csvToHtml method works correct or not
     */
    @Test
    void testCsvToHtml() {
        long start = System.currentTimeMillis();
        try {
            (new CsvToHtml()).csvToHtml("/Users/azuga/Desktop/MuseumCSV.csv", "/Users/azuga/Desktop/museumHTML.html");
            assertEquals(Files.readString(Path.of("/Users/azuga/Desktop/onlineHTML.html")),Files.readString(Path.of("/Users/azuga/Desktop/museumHTML.html")));
        } catch (IOException e) {
            logger.error("{} occurred while reading the file from path",e.getMessage());
        }
        long end = System.currentTimeMillis();
        logger.info("testCsvToHtml() is executed in {} ms", (end - start));
    }

    /**
     * This class is used to test for all exceptions which are raised in csvToHtml method
     */
    @Nested
    class CsvToHtmlExceptions {
        /**
         * This method is used to test for NullPointerException thrown by csvToHtml()
         */
        @Test
        void test_for_NPE_CsvToHtml() {
            long start = System.currentTimeMillis();
            assertThrows(NullPointerException.class, () -> (new CsvToHtml()).csvToHtml(null, null));
            long end = System.currentTimeMillis();
            logger.info("test_for_NPE_CsvToHtml() is executed in {} ms", (end - start));
        }
        /**
         * This method is used to test for NullPointerException thrown by csvToHtml()
         */
        @Test
        void test_for_NPE_CsvToHtml1() {
            long start = System.currentTimeMillis();
            assertThrows(NullPointerException.class, () -> (new CsvToHtml()).csvToHtml(null, "null"));
            long end = System.currentTimeMillis();
            logger.info("test_for_NPE_CsvToHtml1() is executed in {} ms", (end - start));
        }
        /**
         * This method is used to test for NullPointerException thrown by csvToHtml()
         */
        @Test
        void test_for_NPE_CsvToHtml2() {
            long start = System.currentTimeMillis();
            assertThrows(NullPointerException.class, () -> (new CsvToHtml()).csvToHtml("null", null));
            long end = System.currentTimeMillis();
            logger.info("test_for_NPE_CsvToHtml2() is executed in {} ms", (end - start));
        }
        /**
         * This method is used to test for IOException thrown by csvToHtml()
         */
        @Test
        void test_for_IOEx_CsvToHtml() {
            long start = System.currentTimeMillis();
            assertThrows(IOException.class, () -> (new CsvToHtml()).csvToHtml("null", "null"));
            long end = System.currentTimeMillis();
            logger.info("test_for_IOEx_CsvToHtml() is executed in {} ms", (end - start));
        }
    }

}

