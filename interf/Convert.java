/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training.interf;

import java.io.IOException;
/**
 * This method is used to convert one file to other file
 */
public interface Convert{
    void csvToHtml();
    void csvToPdf() throws IOException;
    void jsonToXml();
    void csvToExcel();
}
