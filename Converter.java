/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */
package com.azuga.training;


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
import java.util.List;

/**
 * This class converts one file to other file , it works for only four files
 * like csv to html
 * like json to xml
 * like csv to pdf
 * and csv to xlsx
 */

public class Converter implements Convert {
    private static final Logger logger = LogManager.getLogger(Converter.class.getName());
    /**
     * This method converts the csv file to html format
     */
    public void csvToHtml(String src,String des) {
        long start = System.currentTimeMillis();
        List<String> lines = new ArrayList<>();
        logger.info("reading data from {} using buffered reader",src);
        try (BufferedReader reader = new BufferedReader(new FileReader(src))) {//reading the file here
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
            logger.error("{} occurred while reading file from {}",e,src);
        }
        for (int i = 0; i < lines.size(); i++) {
            lines.set(i, "<tr><td>" + lines.get(i) + "</td></tr>");
            lines.set(i, lines.get(i).replaceAll(",", "</td><td>"));
        }
        lines.set(0, "<table border>" + lines.get(0));
        lines.set(lines.size() - 1, lines.get(lines.size() - 1) + "</table>");
        logger.info("writing the html data to specified destination path i.e,{}",des);
        try (FileWriter writer = new FileWriter(des)) {//writing String to html file
            for (String line : lines) {
                logger.debug("data which is converted to html form "+line);
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            logger.error("{} occurred while writing file to {}",e,des);
        }
        long end = System.currentTimeMillis();
        logger.info("csvToHtml() is executed in {} ms",(end-start));
    }
    /**
     * This method converts the csv file to pdf format
     */
    public void csvToPdf(String src,String des) throws IOException {
        long start = System.currentTimeMillis();
        CSVReader reader = null;
        logger.info("reading data from specified path i.e, {}",src);
        try {
            reader = new CSVReader(new FileReader(src));//reading csv file from given path
        } catch (FileNotFoundException e) {
            logger.error("{} occurred while reading file from {}",e,src);
        }
        String[] nextLine;
        Document my_pdf_data = new Document();

        Rectangle rc = new Rectangle(8300f,8000f);
        my_pdf_data.setPageSize(rc);
        logger.info("writing the pdf data to specified destination path i.e,{}",des);
        try {
            PdfWriter.getInstance(my_pdf_data, new FileOutputStream(des));//writing pdf file to given path
        } catch (DocumentException | FileNotFoundException e) {
            logger.error("{} occurred while writing file to {}",e,des);
        }
        my_pdf_data.open();
        PdfPTable my_first_table = new PdfPTable(73);
        PdfPCell table_cell;
        if(reader!=null) {
            while ((nextLine = reader.readNext()) != null) {
                int i = 0;
                while (i <= 71) {
                    table_cell = new PdfPCell(new Phrase(nextLine[i]));
                    my_first_table.addCell(table_cell);
                    i++;
                }
            }
        }
        try {
            my_pdf_data.add(my_first_table);
        } catch (DocumentException e) {
            logger.error("{} occurred while writing file to {}",e,des);
        }
        my_pdf_data.close();
        long end = System.currentTimeMillis();
        logger.info("csvToPdf() is executed in {} ms",(end-start));
    }
    /**
     * This method converts the json file to xml format
     */
    public void jsonToXml(String src,String dest){
        long start = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>\n");
        sb.append("<roots>");
        String result;
        logger.info("reading data from specified path i.e, {}",src);
        try {
            result = new String(Files.readAllBytes(Paths.get(src)));
            logger.debug("Data from json file is "+result);
            String result1 = result.replace("[{\"objectID\"","{\"objectID\"");
            String[] arr = result1.split("},\\{\"o");
            logger.info("writing the xml data to specified destination path i.e,{}",dest);
            FileWriter file = new FileWriter(dest);
            for(int i=0;i< arr.length;i++) {
                if(i==0) {
                    logger.debug("Json Data "+arr[i]);
                    sb.append(convertToXML(arr[i] + "}", "root","root"));// This method converts json object to xml string

                    logger.debug("Xml Data is "+sb);
                }
                else if (i==arr.length-1) {

                    logger.debug("Json Data "+arr[i]);
                    result="{\"o"+arr[i].replace("\"GalleryNumber\":\"\"}]","\"GalleryNumber\":\"\"}");
                    sb.append(convertToXML(result, "root","root"));//file.append(convertToXML(arr[i], "root"));
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
            // close FileWriter
            file.close();


        } catch (IOException e1) {
            logger.error("{} occurred while writing file to {} or reading file from {}",e1,dest,src);
        }
        long end = System.currentTimeMillis();
        logger.info("jsonToXml() is executed in {} ms",(end-start));
    }

    /**
     * This method formats the unformatted xml data
     * @param xml - used to take the input of xml data
     * @return -returns formatted xml data
     */
    public static String format(String xml) {
        long start = System.currentTimeMillis();

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

            long end = System.currentTimeMillis();
            logger.info("format() is executed in {} ms",(end-start));
            return writer.writeToString(document);

        } catch (Exception e) {
            logger.error("Exception "+e.getMessage());
            long end = System.currentTimeMillis();
            logger.info("format() is executed in {} ms",(end-start));
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
        long start = System.currentTimeMillis();
        JSONObject jsonObject =new JSONObject(jsonString);
        String unformattedXml =  "<"+root+">" + XML.toString(jsonObject) + "</"+root1+">";
        logger.info("converting the unformatted xml file to formatted xml file");
        long end = System.currentTimeMillis();
        logger.info("convertToXml() is executed in {} ms",(end-start));
        return format(unformattedXml);
    }
    /**
     * This method converts the csv file to xlsx format
     */
    public void csvToExcel(String src,String des) {
        long start = System.currentTimeMillis();

        try(FileWriter fw = new FileWriter(des)){
            logger.info("reading data from specified path i.e, {}",src);

            logger.info("writing the data to specified destination path i.e,{}",des);
            fw.write(Files.readString(Path.of(src)));
        }catch (Exception e) {
            logger.error("{} occurred while writing file to {} or reading file from {}",e,des,src);
        }

        long end = System.currentTimeMillis();
        logger.info("csvToExcel() is executed in {} ms",(end-start));
    }
}