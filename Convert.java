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
public interface Convert{
    void csvToHtml(String src,String dest);
    void csvToPdf(String src,String des) throws IOException;
    void jsonToXml(String src,String desh);
    void csvToExcel(String src,String des);
}
