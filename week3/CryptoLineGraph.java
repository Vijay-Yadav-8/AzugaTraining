/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training.week3;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(CryptoLineGraph.class.getName());
    public static void main(String[] args){
        String url = "https://api.coingecko.com/api/v3/coins/cardano/market_chart?vs_currency=usd&days=30&interval=daily";
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            logger.error("Exception "+e.getMessage());
            throw new RuntimeException(e);
        }
        String substring = url.substring(url.length() - 22, url.length() - 1);
        if(response.statusCode()==200) {
            logger.trace("making a call to the server using url "+url);
            logger.debug("response given by the end point  "+ substring +" is "+response.body());
            JSONObject jsonObject = new JSONObject(response.body());
            System.out.println(jsonObject);
            JSONArray jsonArray = jsonObject.getJSONArray("prices");
            System.out.println(jsonArray);
            XYSeries xySeries = new XYSeries("Price");
            for (int i = 0; i < jsonArray.length(); i++) {
                String[] a = jsonArray.get(i).toString().replace("[", "").replace("]", "").split(",");
                float f1 = Float.parseFloat(a[1]);
                Float f2 = f1 * 1000;
                xySeries.add(i + 1, f2);
            }

            XYSeriesCollection dataset = new XYSeriesCollection(xySeries);

            JFreeChart chart = ChartFactory.createXYLineChart("cardano's--CryptoCurrency\n Price to Days",
                    "From 20/08/2022 To 20/09/2022", "Price in dollars($)", dataset, PlotOrientation.VERTICAL, true, true, false);

            try {
                ChartUtils.saveChartAsJPEG(new File("/Users/azuga/Desktop/CryptoLineChart.jpeg"), chart, 650, 700);
                System.out.println("Line Chart created");
            } catch (Exception e) {
                logger.error("Exception "+e.getMessage());
            }
        }
        else
            logger.error(response.statusCode()+"Error code for url end point"+substring);

    }
}