/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyz.
 */

package com.azuga.training.week2;

import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public class NewApi {
    public static void main(String[] args) throws IOException, InterruptedException, FileNotFoundException {
        var url = "https://fakestoreapi.com/products/1";
        var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        var client = HttpClient.newBuilder().build();
        var response = client.send(request , HttpResponse.BodyHandlers.ofString());
        String sb= response.body();
        try (FileWriter fw = new FileWriter("/Users/azuga/Desktop/fakestore.json")) {
            fw.write(sb);
            System.out.println("data is filled into fakestore.json file");
        } catch (Exception e) {
            System.out.println("an error occurred while creating or writing to the file");
        }
        JSONObject jo=new JSONObject(sb);
        Iterator<?> keys;
        keys = jo.keys();
        /*String str=(String)keys.next();
        System.out.println(str);
        System.out.println(jo.get((str)));
        System.out.println(jo.has("description"));
        System.out.println(jo);
        System.out.println(keys);*/
        StringBuilder res= new StringBuilder("[{");
        int cou=0;
        while (keys.hasNext()){
            cou+=1;
            String s=(String)keys.next();
            if (jo.get(s) instanceof JSONObject){
                JSONObject job= (JSONObject) jo.get(s);
                //System.out.println(jo.get(s));
                //System.out.println(job);
                Iterator<?> keys1;
                keys1 = job.keys();
                int co=0;
                while (keys1.hasNext()){
                    String s1=(String)keys1.next();
                    res.append("\""+s+"_"+s1+"\""+":"+"\""+job.get(s1)+"\"");
                    co+=1;
                    if (keys1.hasNext()){
                        res.append(",");
                    }
                    else{
                        if (keys.hasNext()){
                            res.append(",");
                        }
                    }
                }
                //System.out.println(co);
            }
            else{
                res.append("\""+s+"\""+":"+"\""+jo.get(s)+"\"");
                if (keys.hasNext()){
                    res.append(",");
                }
            }
        }
        res.append("}]");
        System.out.println(res);
        System.out.println(sb);
        //System.out.println(cou);
        String sb2= new String(res);
        //System.out.println(sb.charAt(338)+sb.charAt(339)+sb.charAt(340)+sb.charAt(341)+sb.charAt(342));
        //String sb=sb1.replace(":{","_");
        try (FileWriter fw = new FileWriter("/Users/azuga/Desktop/fakestore.json")) {
            fw.write(sb2);
            System.out.println("data is filled into the file fakestore.json");
        } catch (Exception e) {
            System.out.println("an error occurred while creating or writing to the file");
        }
        InputStream file = new FileInputStream("/Users/azuga/Desktop/fakestore.json");
        JSONTokener tokenizer = new JSONTokener(file);
        JSONArray jsonArray = new JSONArray(tokenizer);
        StringBuilder csv = new StringBuilder();
        csv.append(CDL.toString(jsonArray));
        try {

            // Convert JSONArray into csv and save to file
            Files.write(Path.of("/Users/azuga/Desktop/fakestore1.csv"), csv.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("completed");
        System.out.println(csv);
    }

}
