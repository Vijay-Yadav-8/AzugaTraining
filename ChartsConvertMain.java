/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * this class is used to call the converter and chart maker methods
 */
public class ChartsConvertMain {
    private static final Logger logger = LogManager.getLogger(ChartsConvertMain.class.getName());

    public static void main(String[] args) {
        String path = "." + File.separator + "." + File.separator + "." + File.separator + "GeneratedFiles" + File.separator;
        MuseumApi api = new MuseumApi();
        File file = new File(path);
        if (file.mkdir()) {
            logger.info("Created the folder GeneratedFiles to create the required files to run the app");
        }
        api.jsonWriter(path + "museumJson.json", path + "museumCsv.csv");
        Convert convert = new Converter();
        convert.csvToExcel(path + "museumCsv.csv", path + "museumExcel.xlsx");
        convert.csvToHtml(path + "museumCsv.csv", path + "museumHtml.html");
        try {
            convert.csvToPdf(path + "museumCsv.csv", path + "museumPdf.pdf");
        } catch (IOException e) {
            logger.error("{} occurred while reading file from {} or writing file to {}", e, path + "museumCsv.csv", path + "museumPdf.pdf");
        }
        convert.jsonToXml(path + "museumJson.json", path + "museumXml.xml");
        ChartMake chartMake = new ChartsMaker();
        chartMake.pieChartMaker();
        chartMake.barGraphMaker();
        chartMake.lineGraphMaker(path + "lineGraph.jpeg");
        File file1 = new File(path + "museumJson.json");
        if (file1.delete())
            logger.info("cleaning up the temp file MuseumJson");
        File file2 = new File(path + "museumCsv.csv");
        if (file2.delete())
            logger.info("cleaning up the temp file MuseumCsv");
    }
}
