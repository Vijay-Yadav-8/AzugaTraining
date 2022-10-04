/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training.week3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class converts csv file to html file
 */
public class CsvToHtml {

    private static final Logger logger = LogManager.getLogger(CsvToHtml.class.getName());//Used to save the logs to the log file

    /**
     * This method can be used to convert a csv File to Html File
     * @param csvPath- used to take csv file path
     * @param htmlPath- used to take html file path
     * @throws NullPointerException thrown when file path is empty or null
     * @throws IOException thrown when file path is not given
     */
    public void csvToHtml(String csvPath, String htmlPath) throws NullPointerException, IOException {
        if (csvPath != null && htmlPath != null &&(!csvPath.isBlank())&&(!htmlPath.isBlank())) {
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(csvPath))) {//reading the file here
                String currentLine;
                while ((currentLine = reader.readLine()) != null) {
                    lines.add(currentLine);
                }
            } catch (IOException e) {
                logger.error("{} occurred while reading the file from path {}",e,csvPath);
                throw new IOException("File path is incorrect or file does not exist");
            }
            if(lines.size()!=0) {
                for (int i = 0; i < lines.size(); i++) {
                    lines.set(i, "<tr><td>" + lines.get(i) + "</td></tr>");
                    lines.set(i, lines.get(i).replaceAll(",", "</td><td>"));
                }
                lines.set(0, "<table border>" + lines.get(0));
                lines.set(lines.size() - 1, lines.get(lines.size() - 1) + "</table>");
                try (FileWriter writer = new FileWriter(htmlPath)) {//writing String to html file
                    for (String line : lines) {
                        logger.debug("data which is converted to html form " + line);
                        writer.write(line + "\n");
                    }
                } catch (IOException e) {
                    logger.error("{} occurred while reading the file from path {}", e, htmlPath);
                }
            }else{
                logger.error("Given File at {} does not contain any data",csvPath);
            }
        }else{
            logger.error("either csvPath or htmlPath is empty");
            throw new NullPointerException("csvPath or htmlPath can not be null or empty!!!!");
        }
    }

    public static void main(String[] args) {
        CsvToHtml csvToHtml = new CsvToHtml();
        try {
            csvToHtml.csvToHtml("","");
        } catch (IOException e) {
            logger.error("{} occurred while reading file from {} or writing file to {}",e.getMessage(),"","");
        }
    }
}