/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training.interf;

import java.io.IOException;

/**
 * this class is used to call the converter and chart maker methods
 */
public class ChartsConvertMain {
    public static void main(String[] args) throws IOException {
        Convert convert = new Converter();
        convert.csvToPdf();
        convert.csvToHtml();
        convert.jsonToXml();
        convert.csvToExcel();
        ChartMake chartMake = new ChartsMaker();
        chartMake.pieChartMaker();
        chartMake.barGraphMaker();
        chartMake.lineGraphMaker();
    }
}
