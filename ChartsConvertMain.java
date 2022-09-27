/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training;

import java.io.File;
import java.io.IOException;

/**
 * this class is used to call the converter and chart maker methods
 */
public class ChartsConvertMain {
    public static void main(String[] args) throws IOException {
        String path = "."+File.separator+"."+File.separator+"."+File.separator+"GeneratedFiles"+File.separator;
        MuseumApi api = new MuseumApi();
        File file = new File(path);
        if(file.mkdir()){
            System.out.println("done");
        }
        api.jsonWriter(path+"museumJson.json",path+"museumCsv.csv");
        Convert convert = new Converter();
        convert.csvToExcel(path+"museumCsv.csv",path+"museumExcel.xlsx");
        convert.csvToHtml(path+"museumCsv.csv",path+"museumHtml.html");
        convert.csvToPdf(path+"museumCsv.csv",path+"museumPdf.pdf");
        convert.jsonToXml(path+"museumJson.json",path+"museumXml.xml");
        ChartMake chartMake = new ChartsMaker();
        chartMake.pieChartMaker();
        chartMake.barGraphMaker();
        chartMake.lineGraphMaker(path+"lineGraph.jpeg");
    }
}
