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
 * This class tests the methods of JsonToXml class
 */
class JsonToXmlTest {

    private static final Logger logger = LogManager.getLogger(JsonToXmlTest.class.getName());//Used to save the logs to the log file
    /**
     * Method under test: {@link JsonToXml#jsonToXml(String, String)}
     * This method is used to test the generation of museum xml file from museum json file
     */
    @Test
    void test_museum_JsonToXml() {
        long start = System.currentTimeMillis();
        try {
            (new JsonToXml()).jsonToXml("/Users/azuga/Desktop/museumJSON.json", "/Users/azuga/Desktop/museumXML.xml");
            assertEquals(Files.readString(Path.of("/Users/azuga/Desktop/museumXML.xml")), Files.readString(Path.of("/Users/azuga/Desktop/truth/museumXML.xml")));
        } catch (IOException e) {
            logger.error("{} occurred while reading the files from paths {} , {}",e.getMessage(),"/Users/azuga/Desktop/museumXML.pdf","/Users/azuga/Desktop/truth/museumXML.pdf");
        }
        long end = System.currentTimeMillis();
        logger.info("test_museum_CsvToPdf() is executed in {} ms", (end - start));
    }
    /**
     * Method under test: {@link JsonToXml#jsonToXml(String, String)}
     * This method is used to test the generation of fake store xml file from fake store json file
     */
    @Test
    void test_fake_JsonToXml() {
        long start = System.currentTimeMillis();
        try {
            (new JsonToXml()).jsonToXml("/Users/azuga/Desktop/fakeJSON.json", "/Users/azuga/Desktop/fakeXML.xml");
            assertEquals(Files.readString(Path.of("/Users/azuga/Desktop/fakeXml.xml")), Files.readString(Path.of("/Users/azuga/Desktop/truth/fakeXML.xml")));
        } catch (IOException e) {
            logger.error("{} occurred while reading the files from paths {} , {}",e.getMessage(),"/Users/azuga/Desktop/museumPDF.pdf","/Users/azuga/Desktop/truth/museumPDF.pdf");
        }
        long end = System.currentTimeMillis();
        logger.info("test_museum_CsvToPdf() is executed in {} ms", (end - start));
    }
    @Nested
class JsonToXlExceptions {
    /**
     * Method under test: {@link JsonToXml#jsonToXml(String, String)}
     * This method is used to check for null pointer Exception in JsonTo Xml
     */
    @Test
    void test_for_RTE_JsonToXml() {
        long start = System.currentTimeMillis();
        assertThrows(NullPointerException.class, () -> (new JsonToXml()).jsonToXml(null, "foo"));
        long end = System.currentTimeMillis();
        logger.info("test_for_RTE_JsonToXml() is executed in {} ms", (end - start));
    }

    /**
     * Method under test: {@link JsonToXml#jsonToXml(String, String)}
     *  This method is used to check for null pointer Exception in JsonTo Xml
     */
    @Test
    void test_for_NPE_JsonToXml() {
        long start = System.currentTimeMillis();
        assertThrows(NullPointerException.class, () -> (new JsonToXml()).jsonToXml("", null));
        long end = System.currentTimeMillis();
        logger.info("test_for_NPE_JsonToXml() is executed in {} ms", (end - start));
    }
        /**
         * Method under test: {@link JsonToXml#jsonToXml(String, String)}
         *  This method is used to check for null pointer Exception in JsonTo Xml
         */
        @Test
        void test_for_FNF_JsonToXml() {
            long start = System.currentTimeMillis();
            assertThrows(IOException.class, () -> (new JsonToXml()).jsonToXml("/Users/azuga/Downloads/fakeJSON.json", "/Users/azuga/Downloads/fakeXML.xml"));
            long end = System.currentTimeMillis();
            logger.info("test_for_FNF_JsonToXml() is executed in {} ms", (end - start));
        }
}
}

