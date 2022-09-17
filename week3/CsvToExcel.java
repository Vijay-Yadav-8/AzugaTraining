/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */
package com.azuga.training.week3;


import au.com.bytecode.opencsv.CSVReader;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class converts csv file to Excel File
 */

public class CsvToExcel {

    public static void main(String[] args) throws Exception {
        try(FileWriter fw = new FileWriter("/Users/azuga/Desktop/museum123.xlsx")){
            fw.write(Files.readString(Path.of("/Users/azuga/Desktop/museum.csv")));
            System.out.println("file is saved as museum123.xlsx");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
