
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
import com.opencsv.exceptions.CsvException;
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

    private static final Logger logger = LogManager.getLogger(MuseumApi.class);//Used to save the logs to the log file
    static StringBuilder sb = new StringBuilder();  //used to save the response from the server.
    MuseumApi(){}

    /**
     * this method pings the server with specified url and saves the data
     * @param url -used to specify the required api url
     * @throws InterruptedException - it is thrown when server does not respond for call
     */
    public void apiCallMaker(String url) throws InterruptedException {
        logger.trace("apiCallMaker() is invoked");
        long start = System.currentTimeMillis();
        logger.info("Making the call on url: {}", url);
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException | IOException e) {
            logger.error("{} occurred while getting a response museum api url {}", e, url);
            long end = System.currentTimeMillis();
            logger.info("MuseumApi() is executed in {} ms", (end - start));
        }
        if(response != null) {
            if (response.statusCode() == 200) {
                logger.debug("Response from server is :{}", response.body());
                MuseumApi.sb.append(response.body().replaceAll("\"\"", "null"));
            } else {
                logger.error("server thrown {} error while fetching with url:{}",response.statusCode(),url);
                throw new InterruptedException("server is not responding......... "+response.statusCode()+" error");
            }
        }else{
            logger.error("No response from the server for url as {}",url);
        }
        long end = System.currentTimeMillis();
        logger.info("MuseumApi() is executed in {} ms", (end - start));
        logger.trace("apiCallMaker() is executed");
    }

    /**
     * This method can be used to convert json file data to csv data file.
     * @param jsonPath-used to take json file path
     * @param csvPath-used to take csv file path
     * @throws FileNotFoundException-thrown when no file is found
     */
    public void csvWriter(String jsonPath, String csvPath) throws FileNotFoundException {
        logger.trace("csvWriter() is invoked");
        long start = System.currentTimeMillis();
        if(sb!=null){
            try ( PrintWriter pw = new PrintWriter(jsonPath)){
                pw.append(sb.toString());
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException(e.getMessage());
            }
        }else
            logger.error("Either Response from api is null or the method apiCallMaker() is not invoked");
        String json = null;
        try {
            json = new String(Files.readAllBytes(Paths.get(jsonPath)));
        } catch (IOException e) {
            logger.error("{} occurred while reading file from {}", e, jsonPath);
        }
        if (json != null) {
            String s="["+json.replaceAll("}\\{","},{")+"]";
            JFlat flatMe = new JFlat(s);
            try {
                flatMe.json2Sheet().headerSeparator("_").write2csv(csvPath);
            } catch (Exception e) {
                logger.error("{} occurred while writing file to {}", e, csvPath);
            }
            try {
                CSVReader reader = new CSVReader(new FileReader(csvPath));
                logger.info("Reading file from Specified path i.e, {}", csvPath);
                List<String[]> csvBody = reader.readAll();

                for (int m = 1; m < csvBody.size(); m++) {
                    String i = csvBody.get(m)[0].replaceAll(".0+$", "");
                    csvBody.get(m)[0] = i;
                }
                for (int m = 0; m < csvBody.size(); m++) {
                    int n = m + 1;
                    while (n < csvBody.size() && (csvBody.get(m)[0]).equals(csvBody.get(n)[0])) {
                        String[] i = csvBody.get(m);
                        csvBody.remove(i);
                    }
                }
                reader.close();
                logger.trace("opening file on {}", csvPath);
                CSVWriter writer = new CSVWriter(new FileWriter(csvPath));
                writer.writeAll(csvBody);
                writer.close();
                logger.trace("closing file on {}", csvPath);
            } catch (CsvException | IOException e) {
                logger.error("{} occurred while writing file to {}", e, csvPath);
            }
        }
        long end = System.currentTimeMillis();
        logger.info("csvWriter() is executed in {} ms", (end - start));
        logger.trace("csvWriter() is invoked");
    }
    public static void main(String[] args) {
        Random rm = new Random();
        MuseumApi api = new MuseumApi();
        for (int i = 0; i < 10; i++) {
            try {
                api.apiCallMaker("https://collectionapi.metmuseum.org/public/collection/v1/objects/" + rm.nextInt(481501));
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            api.csvWriter("/Users/azuga/Desktop/museumJSON2.json","/Users/azuga/Desktop/museumCSV2.csv");
        } catch (FileNotFoundException e) {
            logger.error("{} occurred while reading the file from path{}",e.getMessage(),"/Users/azuga/Desktop/museumJSON2.json");
        }
    }
}
