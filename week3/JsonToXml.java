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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(JsonToXml.class.getName());

        public static void main(String[] args){
        StringBuilder sb = new StringBuilder();
            sb.append("<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>\n");
            sb.append("<roots>");
            String result;
            try {
                result = new String(Files.readAllBytes(Paths.get("/Users/azuga/Desktop/museum.json")));
                logger.debug("Data from json file is "+result);
                String result1 = result.replace("[{\"objectID\"","{\"objectID\"");
                String[] arr = result1.split("},\\{\"o");

                FileWriter file = new FileWriter("/Users/azuga/Desktop/museum4.xml");
//                file.write("");
                for(int i=0;i< arr.length;i++) {
                    if(i==0) {
                        logger.debug("Json Data "+arr[i]);
                        sb.append(convertToXML(arr[i] + "}", "root","root"));// This method converts json object to xml string
                        System.out.println("hi");

                        logger.debug("Xml Data is "+sb);
                    }
                        else if (i==arr.length-1) {

                        logger.debug("Json Data "+arr[i]);
                        System.out.println(arr[i].charAt(arr[i].length()-1));
                        result="{\"o"+arr[i].replace("\"GalleryNumber\":\"\"}]","\"GalleryNumber\":\"\"}");
                        sb.append(convertToXML(result, "root","root"));//file.append(convertToXML(arr[i], "root"));
                        System.out.println("hello");
                        logger.debug("Xml Data is "+sb);
                    }
                    else{

                        logger.debug("Json Data "+arr[i]);
                        result="{\"o"+arr[i]+"}";
                        sb.append(convertToXML(result, "root","root"));
                        logger.debug("Xml Data is "+sb);


                    }
                }
                sb.append("</roots>");
                file.write(sb.toString());
                System.out.println("Your XML data is successfully written into XMLData.txt");
                // close FileWriter
                file.close();


            } catch (IOException e1) {
                logger.error("Exception "+e1.getMessage());
                e1.printStackTrace();
            }
        }
    public static String format(String xml) {
            logger.info("format() is invoked");

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
            logger.debug("formatted data is"+writer.writeToString(document));
            return writer.writeToString(document);
        } catch (Exception e) {
            logger.error("Exception "+e.getMessage());
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
            logger.info("convertToXml() is invoked");
            JSONObject jsonObject =new JSONObject(jsonString);
            String unformattedXml =  "<"+root+">" + XML.toString(jsonObject) + "</"+root1+">";
            return format(unformattedXml);
        }
    }