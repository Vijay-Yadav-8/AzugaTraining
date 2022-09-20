/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training.week3;



import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * This class can be used to create Line Graph form the json data fetched from cryptocurrency api
 */
public class CryptoLineGraph {
    public static void main(String[] args) throws IOException, InterruptedException {
        String url = "https://api.coingecko.com/api/v3/coins/cardano/market_chart?vs_currency=usd&days=20&interval=daily";
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonObject = new JSONObject(response.body());
        System.out.println(jsonObject);
        JSONArray jsonArray = jsonObject.getJSONArray("prices");
        System.out.println(jsonArray);
        XYSeries xySeries = new XYSeries("Price");
        for(int i = 0;i<jsonArray.length();i++){
            System.out.println(jsonArray.get(i));
            String[] a  = jsonArray.get(i).toString().replace("[","").replace("]","").split(",");
            System.out.println(a[1]);
            xySeries.add(i+1,Float.valueOf(a[1]));
        }

        XYSeriesCollection dataset = new XYSeriesCollection(xySeries);

        JFreeChart chart = ChartFactory.createXYLineChart("cardano's--CryptoCurrency\n Price to Days",
                "Days", "Price", dataset, PlotOrientation.VERTICAL, true, true, false);

        try {
            ChartUtils.saveChartAsJPEG(new File("/Users/azuga/Desktop/LineChart.jpeg"),chart,650,700);
            System.out.println("Line Chart created");
        }
        catch (Exception e){
            System.err.println("error");
        }


    }
}