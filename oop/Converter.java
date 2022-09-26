/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */
package com.azuga.training.oop;


import au.com.bytecode.opencsv.CSVReader;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class converts csv file to Excel File
 */

public class Converter {
    private static final Logger logger = LogManager.getLogger(Converter.class.getName());
    public void csvToHtml() {
        List<String> lines = new ArrayList<>();
        logger.warn("Source Path for museum1.csv must be a valid path");
        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/azuga/Desktop/museum1.csv"))) {//reading the file here
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {

                logger.debug("Data before manipulation"+currentLine);
                String s2 = currentLine.replaceAll("\"\"","null");
                String s3 = s2.replaceAll("\"","");
                String s=s3.replaceAll("https://images.metmuseum.org/CRDImages","<img src=https\\://images.metmuseum.org/CRDImages");
                String s1=s.replaceAll("jpg","jpg style=\"width:100px;height:100px;\" >");
                logger.debug("Data after manipulation"+s1);
                lines.add(s1);
            }
        } catch (IOException e) {
            logger.error("Exception "+e.getMessage());
            e.printStackTrace();
        }
        for (int i = 0; i < lines.size(); i++) {
            lines.set(i, "<tr><td>" + lines.get(i) + "</td></tr>");
            lines.set(i, lines.get(i).replaceAll(",", "</td><td>"));
        }
        lines.set(0, "<table border>" + lines.get(0));
        lines.set(lines.size() - 1, lines.get(lines.size() - 1) + "</table>");

        try (FileWriter writer = new FileWriter("/Users/azuga/Desktop/museum3.html")) {//writing String to html file
            for (String line : lines) {
                logger.debug("data which is converted to html form "+line);
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            logger.error("Exception "+e.getMessage());
            e.printStackTrace();
        }
    }
    public void csvToPdf() throws IOException {
        CSVReader reader;
        try {
            reader = new CSVReader(new FileReader("/Users/azuga/Desktop/museum1.csv"));//reading csv file from given path
        } catch (FileNotFoundException e) {
            logger.error("Exception "+e.getMessage());
            throw new RuntimeException(e);
        }
        String[] nextLine;
        Document my_pdf_data = new Document();

        Rectangle rc = new Rectangle(8300f,8000f);
        my_pdf_data.setPageSize(rc);

        try {
            PdfWriter.getInstance(my_pdf_data, new FileOutputStream("/Users/azuga/Desktop/museum2.pdf"));//writing pdf file to given path
        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        my_pdf_data.open();
        PdfPTable my_first_table = new PdfPTable(78);
        PdfPCell table_cell;
        while ((nextLine = reader.readNext()) != null) {
            int i = 0;
            logger.debug("Reading from csv file "+ Arrays.toString(nextLine));
            while (i <= 77) {
                table_cell = new PdfPCell(new Phrase(nextLine[i]));
                my_first_table.addCell(table_cell);
                i++;
            }
        }
        try {
            my_pdf_data.add(my_first_table);
        } catch (DocumentException e) {
            logger.error("Exception "+e.getMessage());
            throw new RuntimeException(e);
        }
        my_pdf_data.close();
    }
    public void jsonToXml(){
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
    public void csvToExcel() {
        try(FileWriter fw = new FileWriter("/Users/azuga/Desktop/museum123.xlsx")){
            fw.write(Files.readString(Path.of("/Users/azuga/Desktop/museum.csv")));
            System.out.println("file is saved as museum123.xlsx");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
