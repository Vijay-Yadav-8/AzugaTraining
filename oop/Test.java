/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training.oop;

public class Test {
    public static void main(String[] args) throws Exception {
        ChartsMaker charts = new ChartsMaker();
        charts.barGraphMaker();
        charts.lineGraphMaker();
        charts.pieChartMaker();
        Converter convert = new Converter();
        convert.csvToHtml();
        convert.csvToExcel();
        convert.csvToPdf();
        convert.jsonToXml();
    }
}
