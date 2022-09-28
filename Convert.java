/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training;

import java.io.IOException;

/**
 * This method is used to convert one file to other file
 */
public interface Convert {
    /**
     * This method converts the csv file to html format
     */
    void csvToHtml(String src, String dest);

    /**
     * This method converts the csv file to pdf format
     */
    void csvToPdf(String src, String des) throws IOException;

    /**
     * This method converts the json file to xml format
     */
    void jsonToXml(String src, String des);

    /**
     * This method converts the csv file to xlsx format
     */
    void csvToExcel(String src, String des);
}
