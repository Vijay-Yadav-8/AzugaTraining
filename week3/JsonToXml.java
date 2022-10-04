/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training.week3;


import com.github.underscore.U;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class creates XML file by taking input as Json File
 */
public class JsonToXml {

    private static final Logger logger = LogManager.getLogger(JsonToXml.class.getName());

    public void jsonToXml(String jsonPath,String xmlPath) throws NullPointerException{
        long start = System.currentTimeMillis();
        if(jsonPath!=null && xmlPath!=null) {
            try {
                String xml = U.jsonToXml(Files.readString(Path.of(jsonPath)));
                FileWriter writer = new FileWriter(xmlPath);
                writer.write(xml);
                writer.close();
            } catch (IOException e) {
                logger.error("{} occurred while reading file from {}", e, jsonPath);
            }
        }
        else {
            logger.error("jsonToXml() was expecting path but for either jsonPath or xmlPath is null");
            long end = System.currentTimeMillis();
            logger.info("jsonToXml() is executed in {} ms", (end - start));
            throw new NullPointerException("path cannot be null");
        }
        long end = System.currentTimeMillis();
        logger.info("jsonToXml() is executed in {} ms", (end - start));
    }
}