/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training.week2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class MuseumApiTest {
    private static final Logger logger = LogManager.getLogger(MuseumApiTest.class);//Used to save the logs to the log file
    /**
     * Method under test: default or parameterless constructor of {@link MuseumApi}
     * This method is used to create object for MuseumApi class
     */
    MuseumApi api;
    @BeforeEach
    void init()
    {
        logger.trace("MuseumApi constructor invoked");
        api=new MuseumApi();
    }
    /**
     * Method under test: {@link MuseumApi#apiCallMaker(String)}
     * This method is used to test for empty input of the museum api url
     */
    @Test
    @Disabled("requires to run separately")
    void test_museum_ApiCallMaker() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            try {
                api.apiCallMaker("https://collectionapi.metmuseum.org/public/collection/v1/objects/" + (i * 10));
            } catch (InterruptedException e) {
                logger.error("{} occurred while fetching data from museum server", e.getMessage());
            }
        }
            try {
                api.csvWriter("/Users/azuga/Desktop/museumJSON.json","/Users/azuga/Desktop/museumCSV.csv");
            } catch (FileNotFoundException e) {
                logger.error("{} occurred while reading data from {} or writing data to {}",e.getMessage(),"/Users/azuga/Desktop/museumJSON.json","/Users/azuga/Desktop/museumCSV.csv");
            }
            try {
                assertEquals(Files.readString(Path.of("/Users/azuga/Desktop/museumJSON.json")),Files.readString(Path.of("/Users/azuga/Desktop/truth/museumJSON.json")));
                assertEquals(Files.readString(Path.of("/Users/azuga/Desktop/museumCSV.csv")),Files.readString(Path.of("/Users/azuga/Desktop/truth/museumCSV.csv")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        long end = System.currentTimeMillis();
        logger.info("test_museum_ApiCallMaker() is executed in {} ms", (end - start));
    }
    /**
     * Method under test: {@link MuseumApi#apiCallMaker(String)}
     * This method is used to test for empty input of the fake store api url
     */
    @Test
    void test_fakeStore_ApiCallMaker() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            try {
                api.apiCallMaker("https://fakestoreapi.com/products/1" + i);
            } catch (InterruptedException e) {
                logger.error("{} occurred while fetching data from fake store server", e.getMessage());
            }
        }
            try {
                api.csvWriter("/Users/azuga/Desktop/fakeJSON.json","/Users/azuga/Desktop/fakeCSV.csv");
            } catch (FileNotFoundException e) {
                logger.error("{} occurred while reading data from {} or writing data to {}",e.getMessage(),"/Users/azuga/Desktop/fakeJSON.json","/Users/azuga/Desktop/fakeCSV.csv");
            }
            try {
                assertEquals(Files.readString(Path.of("/Users/azuga/Desktop/fakeJSON.json")),Files.readString(Path.of("/Users/azuga/Desktop/truth/fakeJSON.json")));
                assertEquals(Files.readString(Path.of("/Users/azuga/Desktop/fakeCSV.csv")),Files.readString(Path.of("/Users/azuga/Desktop/truth/fakeCSV.csv")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        long end = System.currentTimeMillis();
        logger.info("test_fakeStore_ApiCallMaker() is executed in {} ms", (end - start));
    }

    @Nested
    class ApiExceptionTester {
        /**
         * Method under test: {@link MuseumApi#apiCallMaker(String)}
         * This method is used to test for empty input of the url
         */
        @Test
        void test_NPE_ApiCallMaker() {
            long start = System.currentTimeMillis();
            assertThrows(NullPointerException.class, () -> api.apiCallMaker(""));
            long end = System.currentTimeMillis();
            logger.info("test_NPE_ApiCallMaker() is executed in {} ms", (end - start));
        }

        /**
         * Method under test: {@link MuseumApi#apiCallMaker(String)}
         * This method is used to test for null input of the url
         */
        @Test
        void test_NPE_ApiCallMaker3() {
            long start = System.currentTimeMillis();
            assertThrows(NullPointerException.class, () -> api.apiCallMaker(null));
            long end = System.currentTimeMillis();
            logger.info("test_NPE_ApiCallMaker3() is executed in {} ms", (end - start));
        }
        /**
         * Method under test: {@link MuseumApi#apiCallMaker(String)}
         * This method is used to test for illegal input of the url
         */
        @Test
        void test_IAE_ApiCallMaker(){
            long start = System.currentTimeMillis();
            assertThrows(IllegalArgumentException.class, () -> api.apiCallMaker("foo"));
            long end = System.currentTimeMillis();
            logger.info("test_IAE_ApiCallMaker() is executed in {} ms", (end - start));
            }
        /**
         * Method under test: {@link MuseumApi#csvWriter(String, String)}
         * This method is used to test for empty input of the csvPath and JsonPath
         */
        @Test
        void test_NPE_CsvWriter(){
            long start = System.currentTimeMillis();
            assertThrows(NullPointerException.class,()->api.csvWriter("", null));
            long end = System.currentTimeMillis();
            logger.info("test_NPE_CsvWriter() is executed in {} ms", (end - start));
        }
        /**
         * Method under test: {@link MuseumApi#csvWriter(String, String)}
         * This method is used to test for null input of the csvPath and JsonPAth
         */
        @Test
        void test_NPE_CsvWriter1(){
            long start = System.currentTimeMillis();
            assertThrows(NullPointerException.class,()->api.csvWriter(null, null));
            long end = System.currentTimeMillis();
            logger.info("test_NPE_CsvWriter1() is executed in {} ms", (end - start));
        }

    }
}

