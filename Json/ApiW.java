/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyz.
 */

package com.azuga.training.Json;

import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

public class ApiW {
    public static void main(String[] args) throws IOException, InterruptedException {
        var url = "http://api.weatherapi.com/v1/current.json?key=bd4fc916e20c46f886b05941221309&q=London&aqi=yes";
        var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        var client = HttpClient.newBuilder().build();
        var response = client.send(request , HttpResponse.BodyHandlers.ofString());
        String sb= response.body();
        String sb1=sb.replace("{\"location\":{","");
        String sb2 = sb1.replace("\"current\":{","");
        String sb4 = sb2.replace("{","\"\",");
        String sb3 = sb4.replace("}","");
        System.out.println(sb3);
        try (FileWriter fw = new FileWriter("/Users/azuga/Desktop/weather.json")) {
            fw.write("[{"+sb3+"}]");
            System.out.println("data is filled into the file whether.json");
        } catch (Exception e) {
            System.out.println("an error occurred while creating or writing to the file");
        }
        InputStream is = new FileInputStream("/Users/azuga/Desktop/weather.json");
        JSONTokener tokener = new JSONTokener(is);
        JSONArray jsonArray = new JSONArray(tokener);
        StringBuilder csv = new StringBuilder();
        csv.append(CDL.toString(jsonArray));
        try {
            // Convert JSONArray into csv and save to file
            Files.write(Path.of("/Users/azuga/Desktop/weather1.csv"), csv.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("completed");

    }
}
