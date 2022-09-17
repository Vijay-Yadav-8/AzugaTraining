/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training.week2;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.*;


/**
 * This class sends request to Metropolitan Museum api and saves the response into the text file
 */
public class ApiRest {
    /**
     * @throws IOException          -it is caused when an error occurs in writing or in reading a file.
     * @throws InterruptedException -it is thrown when the internet connection gets down or an error occurs in connection.
     */
    public static void main(String[] args) throws Exception {
        StringBuilder csv = new StringBuilder();
//        Random rm = new Random();
//        try (FileWriter fw = new FileWriter("/Users/azuga/Desktop/hello.json",true)) {
////            fw.write("");
//            fw.append("[");
//        for (int i = 1; i <=10; i++) {
//            String url = "https://collectionapi.metmuseum.org/public/collection/v1/objects/" + rm.nextInt(471581);
//            HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();//Used to build the api request by specifying the particular method
//            HttpClient httpClient = HttpClient.newBuilder().build();//Used to create the client object required to send the request
//            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());//Used to hold the response in string format
////            System.out.println(response.toString());
////            System.out.println(response);
//            if (response.statusCode() == 200) {
//                System.out.println("connecting with server........");
////                System.out.println(response.body());
//                StringBuilder sb = new StringBuilder();
//                String str = response.body().replaceAll("\\[]","null");
//                String str1 = str.replaceAll("\\[\\{","\"\",");
//                String str2 = str1.replaceAll("}]","\"\"");
//                String str3 = str2.replaceAll("},\\{",",");
//                String str4 = str3.replaceAll("\"\"\"\"","\"\"");
//                String str5 = str4.replaceAll("}\"\"","}");
//                String str6 = str5.replaceAll("\"\"\"","\"");
//                sb.append(response.body());
//                System.out.println(sb.toString());
//                if(i<9)
//                    fw.append(sb.toString()).append(",");
//                else
//                    fw.append(sb.toString());
//                JFlat flatMe = new JFlat(str);
//                flatMe.json2Sheet().headerSeparator("_").write2csv("/Users/azuga/Desktop/hello1.csv");
//                System.out.println("Done writing the file to hello1.csv in desktop");

//            } else
//                System.out.println("connection lost 😔");
//        }
//            fw.append("]");
//            System.out.println("data is filled into the file hello.json");
//        } catch (Exception e) {
//            System.out.println("an error occurred while creating or writing to the file");
//        }
//                    try (FileWriter fw = new FileWriter("/Users/azuga/Desktop/hello.json")) {
//                fw.write(sb.toString());
//                System.out.println("data is filled into the file hello.json");
//            } catch (Exception e) {
//                System.out.println("an error occurred while creating or writing to the file");
//            }
//            String json = new String(Files.readAllBytes(Paths.get("/Users/azuga/Desktop/hello.json")));
//            System.out.println(json);
//                    String json = sb.toString();
//            JFlat flatMe = new JFlat(json);
//          String st=flatMe.json2Sheet().headerSeparator("_").toString();
//            InputStream is = new FileInputStream("/Users/azuga/Desktop/hello.json");
//            JSONTokener tokener = new JSONTokener(is);
//            System.out.println(tokener);
//            JSONArray jsonArray = new JSONArray(tokener);
//            csv.append(CDL.toString(jsonArray));
////            System.out.println(CDL.toString(jsonArray));
//            try {
//                // Convert JSONArray into csv and save to file
//                Files.write(Path.of("/Users/azuga/Desktop/hello1.csv"), csv.toString().getBytes());
//                System.out.println("successfully created the csv file in desktop");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        Response ob = new ObjectMapper().readValue(new File("/Users/azuga/Desktop/hello.json"), Response.class);


//        JsonNode jsonTree = new ObjectMapper().readTree(new File("/Users/azuga/Desktop/hello.json"));
//        Response response = new CsvSchema();
        CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
//        String tagsItemsList = tagsItems.stream().map(Object::toString)
//                .collect(Collectors.joining(", "));
//
//        String measurementsList = measurements.stream().map(Object::toString)
//                .collect(Collectors.joining(", "));
//csnjcbkjacbsnmb
//        String additionalImagesList = additionalImages.stream().map(Object::toString)
//                .collect(Collectors.joining(", "));
//
//        String constituentsList = constituents.stream().map(Object::toString)
//                .collect(Collectors.joining(", "));
//        JsonNode firstObject = new ArrayNode();
//
//        firstObject.fieldNames().forEachRemaining(fieldName -> {csvSchemaBuilder.addColumn(fieldName);} );
//        firstObject.fieldNames().forEachRemaining(csvSchemaBuilder::addColumn);
        CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.writerFor(Response.class)
                .with(csvSchema)
                .writeValue(new File("/Users/azuga/Desktop/hello1.csv"), ob);
    }
}
