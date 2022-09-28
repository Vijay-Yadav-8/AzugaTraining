
/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training;

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
    private static final Logger logger = LogManager.getLogger(MuseumApi.class.getName());

    public void jsonWriter(String srcPath, String destPath) {
        long start = System.currentTimeMillis();
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(srcPath);
        } catch (FileNotFoundException e) {
            logger.error("{} occurred while accessing file from {}", e, srcPath);
        }
        Random rm = new Random();
        if (pw != null) {
            for (int i = 1; i <= 10; i++) {
                String url = "https://collectionapi.metmuseum.org/public/collection/v1/objects/" + rm.nextInt(481501);
                logger.info("Making the call on url: {}", url);
                HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
                HttpClient client = HttpClient.newBuilder().build();
                HttpResponse<String> response = null;
                try {
                    response = client.send(request, HttpResponse.BodyHandlers.ofString());
                } catch (IOException | InterruptedException e) {
                    logger.error("{} occurred while getting a response museum api url {}", e, url);
                }
                if (response != null) {
                    if (response.statusCode() == 200) {
                        logger.debug("Response from server is :{}", response.body());
                        pw.write(response.body().replaceAll("\\[]", "null"));
                    } else
                        logger.info("Museum api responded with ErrorCode :{}", response.statusCode());
                } else
                    logger.info("Response from Museum api is null");
            }
            pw.close();
        }
        String json = null;
        try {
            json = new String(Files.readAllBytes(Paths.get(srcPath)));
        } catch (IOException e) {
            logger.error("{} occurred while reading file from {}", e, srcPath);
        }
        if (json != null) {
            String jsonn = json.replaceAll("}\\{", "},{");
            String s = "[" +
                    jsonn +
                    "]";
            JFlat flatMe = new JFlat(s);
            try {
                flatMe.json2Sheet().headerSeparator("_").write2csv(destPath);
            } catch (Exception e) {
                logger.error("{} occurred while writing file to {}", e, destPath);
            }
            try {
                CSVReader reader = new CSVReader(new FileReader(destPath));
                logger.info("Reading file from Specified path i.e, {}", destPath);
                List<String[]> csvBody = reader.readAll();

                for (int m = 1; m < csvBody.size(); m++) {
                    String i = csvBody.get(m)[0].replaceAll(".0+$", "");
                    String j = csvBody.get(m)[29].replaceAll(".0+$", "");
                    String k = csvBody.get(m)[30].replaceAll(".0+$", "");
                    csvBody.get(m)[0] = i;
                    csvBody.get(m)[29] = j;
                    csvBody.get(m)[30] = k;
                }
                for (int m = 0; m < csvBody.size(); m++) {
                    int n = m + 1;
                    while (n < csvBody.size() && (csvBody.get(m)[0]).equals(csvBody.get(n)[0])) {
                        String[] i = csvBody.get(m);
                        csvBody.remove(i);
                    }
                }
                reader.close();
                logger.trace("opening file on {}", destPath);
                CSVWriter writer = new CSVWriter(new FileWriter(destPath));
                writer.writeAll(csvBody);
                writer.close();
                logger.trace("closing file on {}", destPath);
            } catch (CsvException | IOException e) {
                logger.error("{} occurred while writing file to {}", e, destPath);
            }
        }
        long end = System.currentTimeMillis();
        logger.info("jsonWriter() is executed in {} ms", (end - start));
    }
}
