
/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training.week2;

import com.github.opendevl.JFlat;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
/**
 * This class is used to fetch the data from museum api as json string and appends to csv file
 */
public class MuseumApi {
    private static final Logger logger = LogManager.getLogger(MuseumApi.class);
    public static void main(String[] args){

        PrintWriter pw;
        try {
            pw = new PrintWriter("/Users/azuga/Desktop/museum.json");
        } catch (FileNotFoundException e) {
            logger.error("Exception "+e.getMessage());
            throw new RuntimeException(e);
        }
        Random rm = new Random();
        for(int i=0;i<10;i++) {
            String url = "https://collectionapi.metmuseum.org/public/collection/v1/objects/" + rm.nextInt(481220);
            HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response;
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                logger.error("Exception "+e.getMessage());
                throw new RuntimeException(e);
            }
            logger.info("making a call on url");
            if (response.statusCode() == 200) {
                System.out.println("connected to server..✦..Fetching the data.✦..✦..");
                pw.write(response.body().replaceAll("\\[]", "null"));
            }else
                System.out.println("connection lost 😔");
        }
        pw.close();
        String json;
        try {
            json = new String(Files.readAllBytes(Paths.get("/Users/azuga/Desktop/museum.json")));
        } catch (IOException e) {
            logger.error("Exception "+e.getMessage());
            throw new RuntimeException(e);
        }
        String jsonn = json.replaceAll("}\\{","},{");
        String s = "[" +
                jsonn +
                "]";
        JFlat flatMe = new JFlat(s);
        try {
            flatMe.json2Sheet().headerSeparator("_").write2csv("/Users/azuga/Desktop/museum.csv");
        } catch (Exception e) {
            logger.error("Exception "+e.getMessage());
            throw new RuntimeException(e);
        }
        System.out.println("\nFile created");
        try {
            CSVReader reader = new CSVReader(new FileReader("/Users/azuga/Desktop/museum.csv"));
            List<String[]> csvBody  =reader.readAll();

            for(int m=1 ; m<csvBody.size();m++) {
                String i = csvBody.get(m)[0].replaceAll(".0+$", "");
                String j = csvBody.get(m)[29].replaceAll(".0+$", "");
                String k = csvBody.get(m)[30].replaceAll(".0+$", "");
                csvBody.get(m)[0] = i;
                csvBody.get(m)[29] = j;
                csvBody.get(m)[30] = k;
            }
            for(int m=0 ; m<csvBody.size();m++) {
                int n=m+1;
                    while (n< csvBody.size()&&(csvBody.get(m)[0]).equals(csvBody.get(n)[0])) {
                        String[] i=csvBody.get(m);
                        csvBody.remove(i);
                    }
                }
            reader.close();
            CSVWriter writer = new CSVWriter(new FileWriter("/Users/azuga/Desktop/museum.csv"));
            writer.writeAll(csvBody);
            writer.close();
            System.out.println("Data got modified");
        }
        catch (Exception e){
            logger.warn("Exception "+e.getMessage());
            e.printStackTrace();
        }
    }
}
