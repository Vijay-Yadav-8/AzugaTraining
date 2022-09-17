/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training.week3;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.*;

/**
 * This class creates XML file by taking input as Json File
 */
public class JsonToXml {

        public static void main(String[] args) throws JSONException {
            String result;
            try {
                result = new String(Files.readAllBytes(Paths.get("/Users/azuga/Desktop/museum.json")));
                String[] arr = result.split("},\\{\"o");

                FileWriter file = new FileWriter("/Users/azuga/Desktop/museum4.txt");
                file.write("");
                for(int i=0;i< arr.length;i++) {
                    if(i==0) {
                        file.append(convertToXML(arr[i] + "}", "root"));// This method converts json object to xml string
                        System.out.println("hi");
                    }
                        else if (i==arr.length-1) {
                        System.out.println(arr[i].charAt(arr[i].length()-1));
                        result="{\"o"+arr[i].replace("\"GalleryNumber\":\"\"}]","\"GalleryNumber\":\"\"}");
                        file.append(convertToXML(result, "root"));//file.append(convertToXML(arr[i], "root"));
                        System.out.println("hello");
                    }
                    else{
                        System.out.println(i);
                        result="{\"o"+arr[i]+"}";
                        file.append(convertToXML(result, "root"));


                    }
                }
                System.out.println("Your XML data is successfully written into XMLData.txt");
                // close FileWriter
                file.close();


            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

    /**
     * method for converting JSOn data into XML
     * @param jsonString-json string as input
     * @param root -root element for xml
     * @return - returns xml as a string
     * @throws JSONException - to handle syntax error of json string
     */
        public static String convertToXML(String jsonString, String root) throws JSONException {    // handle JSONException
            JSONObject jsonObject =new JSONObject(jsonString);
            return "<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>\n<"+root+">" + XML.toString(jsonObject) + "</"+root+">";

        }
    }