/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyz.
 */

package com.azuga.training.week2;

import com.azuga.training.random.Json2Flat;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class ReApi {
    public static void main(String[] args) throws Exception {
        Random rm = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 1; i <= 4; i++) {
            String url = "https://collectionapi.metmuseum.org/public/collection/v1/objects/" + rm.nextInt(471581);
            HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();//Used to build the api request by specifying the particular method
            HttpClient httpClient = HttpClient.newBuilder().build();//Used to create the client object required to send the request
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());//Used to hold the response in string format
            if (response.statusCode() == 200) {
                System.out.println("connecting with server.✦.✦.✦.✦.✦.✦.✦.");
                sb.append(response.body().replaceAll("\\[]","null"));
            } else
                System.out.println("connection lost 😔");
        }
        sb.append("]");
        String i = sb.toString().replaceAll("}\\{","},{");
        System.out.println(i);
//        String json = new String(Files.readAllBytes(Paths.get("/Users/azuga/Desktop/museum.json")));
//        String jsonn = json.replaceAll("}\\{","},{");
//        String s = "[" +
//                jsonn +
//                "]";
        Json2Flat flatMe = new Json2Flat(i);
        flatMe.json2Sheet().headerSeparator("_").write2csv("/Users/azuga/Desktop/hello1.csv");
        System.out.println("Done writing the file to hello1.csv in desktop");

    }
}