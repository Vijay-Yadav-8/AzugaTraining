/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyz.
 */

package com.azuga.training.week3;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class JsonToExcel {

        public static void main(String[] args) throws JSONException {
            String result;
            try {
                result = new String(Files.readAllBytes(Paths.get("/Users/azuga/Desktop/museum.json")));

                FileWriter file = new FileWriter("/Users/azuga/Desktop/museum4.txt");

                file.write(convertToXML(result, "root"));// This method converts json object to xml string
                file.flush();
                System.out.println("Your XML data is successfully written into XMLData.txt");
                // close FileWriter
                file.close();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        // create convertToXML() method for converting JSOn data into XML
        public static String convertToXML(String jsonString, String root) throws JSONException {    // handle JSONException
            JSONObject jsonObject = new JSONObject(jsonString);
            // pass the XML data to the main() method
            return "<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>\n<"+root+">" + XML.toString(jsonObject) + "</"+root+">";

        }
    }