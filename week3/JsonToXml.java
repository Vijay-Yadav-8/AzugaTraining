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
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 * This class creates XML file by taking input as Json File
 */
public class JsonToXml {

        public static void main(String[] args) throws JSONException {
        StringBuilder sb = new StringBuilder();
            sb.append("<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>\n");
            sb.append("<roots>");
            String result;
            try {
                result = new String(Files.readAllBytes(Paths.get("/Users/azuga/Desktop/museum.json")));
                String result1 = result.replace("[{\"objectID\"","{\"objectID\"");
                String[] arr = result1.split("},\\{\"o");

                FileWriter file = new FileWriter("/Users/azuga/Desktop/museum4.xml");
//                file.write("");
                for(int i=0;i< arr.length;i++) {
                    if(i==0) {
                        sb.append(convertToXML(arr[i] + "}", "root","root"));// This method converts json object to xml string
                        System.out.println("hi");
                    }
                        else if (i==arr.length-1) {
                        System.out.println(arr[i].charAt(arr[i].length()-1));
                        result="{\"o"+arr[i].replace("\"GalleryNumber\":\"\"}]","\"GalleryNumber\":\"\"}");
                        sb.append(convertToXML(result, "root","root"));//file.append(convertToXML(arr[i], "root"));
                        System.out.println("hello");
                    }
                    else{
                        System.out.println(i);
                        result="{\"o"+arr[i]+"}";
                        sb.append(convertToXML(result, "root","root"));


                    }
                }
                sb.append("</roots>");
                file.write(sb.toString());
                System.out.println("Your XML data is successfully written into XMLData.txt");
                // close FileWriter
                file.close();


            } catch (IOException e1) {

                e1.printStackTrace();
            }
        }
    public static String format(String xml) {

        try {
            final InputSource src = new InputSource(new StringReader(xml));
            final Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src).getDocumentElement();
            final Boolean keepDeclaration = xml.startsWith("<?xml");

            //May need this: System.setProperty(DOMImplementationRegistry.PROPERTY,"com.sun.org.apache.xerces.internal.dom.DOMImplementationSourceImpl");


            final DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            final DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
            final LSSerializer writer = impl.createLSSerializer();

            writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE); // Set this to true if the output needs to be beautified.
            writer.getDomConfig().setParameter("xml-declaration", keepDeclaration); // Set this to true if the declaration is needed to be outputted.

            return writer.writeToString(document);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * method for converting JSOn data into XML
     * @param jsonString-json string as input
     * @param root -root element for xml
     * @return - returns xml as a string
     * @throws JSONException - to handle syntax error of json string
     */
        public static String convertToXML(String jsonString, String root,String root1) throws JSONException {    // handle JSONException
            JSONObject jsonObject =new JSONObject(jsonString);
            String unformattedXml =  "<"+root+">" + XML.toString(jsonObject) + "</"+root1+">";
            return format(unformattedXml);
        }
    }