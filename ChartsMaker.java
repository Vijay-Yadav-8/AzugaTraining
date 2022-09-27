/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to create Bar Graph ,Line chart and Pie chart by making appropriate api calls
 */
public class ChartsMaker extends JFrame implements ChartMake{
    private static final Logger logger = LogManager.getLogger(ChartsMaker.class);
    /**
     * This constructor is used to create Bar graph from the given json data.
     * @param appTitle -title for Bar Graph
     * @param dataset -data required to make graph
     */
    public ChartsMaker(String appTitle, CategoryDataset dataset) {
        super(appTitle);
        long start = System.currentTimeMillis();
        //Create chart
        JFreeChart chart=ChartFactory.createBarChart(
                "Carbon Intensity in UK", //Chart Title
                "Time with 30 min interval", // Category axis
                "Readings (kW/hour)", // Value axis
                dataset,
                PlotOrientation.VERTICAL,
                true,true,false
        );

        ChartPanel panel=new ChartPanel(chart);
        setContentPane(panel);
        long end = System.currentTimeMillis();
        logger.info("chartsMaker() is executed in {} ms",(end-start));
    }
    /**
     * This Constructor is used to create Pie chart with the given input
     * @param title-Used to set the title for Output image
     * @param dataset-Data required to create a pie chart
     */
    public ChartsMaker(String title, PieDataset<String> dataset) {
        super(title);
        long start = System.currentTimeMillis();
        // Creating chart
        JFreeChart chart = ChartFactory.createPieChart(
                "Market capital Percentage",
                dataset,
                true,
                true,
                false);
        //Format Label
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                "{0} : ({1})", new DecimalFormat("00.00"), new DecimalFormat("00%"));
        ((PiePlot<?>) chart.getPlot()).setLabelGenerator(labelGenerator);

        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel); long end = System.currentTimeMillis();
        logger.info("chartsMaker() is executed in {} ms",(end-start));
    }

    public ChartsMaker(){
    }

    /**
     * this method makes pie chart by collecting data from coin gecko api
     */
    public void pieChartMaker(){
        long start = System.currentTimeMillis();
        String url = "https://api.coingecko.com/api/v3/global";
        logger.info("making a call on URL {}",url);
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            logger.error("{} occurred while getting a response coin gecko url {}",e,url);
        }
        String str ;
        String substring = url.substring(url.length() - 14, url.length() - 1);
        if(response!=null) {
            if (response.statusCode() == 200) {
                logger.trace("making a call to the server using url " + url);
                str = response.body();
                logger.debug("response given by the end point  " + substring + " is " + str);
                String[] str1 = str.split("\"market_cap_percentage\"");

                String rem = str1[1].replace(":{", "").replace("}}", "");

                Map<String, Double> map = new HashMap<>();
                String[] str2 = rem.split(",");
                double d;
                int i = 0;
                for (String value : str2) {
                    if (!(value.charAt(value.length() - 1) == '}')) {
                        String[] s = value.split(":");
                        d = Double.parseDouble(s[1]);
                        if (i == 0 || i == 1) {
                            logger.info("dropped extra data of btc and eth");
                        } else {
                            map.put(s[0].replace("\"", ""), d);
                        }
                        i++;
                    } else {
                        break;
                    }
                }
                DefaultPieDataset<String> defaultPieDataset = new DefaultPieDataset<>();
                map.forEach(defaultPieDataset::setValue);
                SwingUtilities.invokeLater(() -> {
                    ChartsMaker example = new ChartsMaker("Crypto PieChart", defaultPieDataset);
                    example.setSize(800, 400);
                    example.setLocationRelativeTo(null);
                    example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    example.setVisible(true);
                });
            } else
                logger.error("server responded with Error code {} for url end point {}",response.statusCode(),substring);
        }
        long end = System.currentTimeMillis();
        logger.info("pieChartMaker() is executed in {} ms",(end-start));
    }
    /**
     * this method makes line graph by collecting data from coin gecko api/cardano crypto
     */
    public void lineGraphMaker(String srcPath){
        long start = System.currentTimeMillis();
        String url = "https://api.coingecko.com/api/v3/coins/cardano/market_chart?vs_currency=usd&days=30&interval=daily";
        logger.info("making a call on URL {}",url);
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.debug("Coin gecko api Data: "+response.body());
        } catch (IOException | InterruptedException e) {
            logger.error("{} occurred while getting a response form coin gecko url {}",e,url);
        }
        String substring = url.substring(url.length() - 22, url.length() - 1);
        if(response != null) {
            if (response.statusCode() == 200) {
                JSONObject jsonObject = new JSONObject(response.body());
                logger.debug("Json Object Data is :" + jsonObject);
                JSONArray jsonArray = jsonObject.getJSONArray("prices");
                logger.debug("Json Array Data is: " + jsonArray);
                XYSeries xySeries = new XYSeries("Price");
                for (int i = 0; i < jsonArray.length(); i++) {
                    String[] a = jsonArray.get(i).toString().replace("[", "").replace("]", "").split(",");
                    float f1 = Float.parseFloat(a[1]);
                    Float f2 = f1 * 1000;
                    xySeries.add(i + 1, f2);
                }
                logger.info("XYSeriesCollection dataset is being created");
                XYSeriesCollection dataset = new XYSeriesCollection(xySeries);

                JFreeChart chart = ChartFactory.createXYLineChart("cardano's--CryptoCurrency\n Price to Days",
                        "From 20/08/2022 To 20/09/2022", "Price in dollars($)", dataset, PlotOrientation.VERTICAL, true, true, false);

                try {
                    logger.warn("PrintWriter class must be closed or would create overwriting issue");
                    ChartUtils.saveChartAsJPEG(new File(srcPath), chart, 650, 700);
                } catch (Exception e) {
                    logger.error("Exception " + e.getMessage());
                }
            } else
                logger.error("server responded with Error code {} for url end point {}",response.statusCode(),substring);
        }
        long end = System.currentTimeMillis();
        logger.info("lineChartMaker() is executed in {} ms",(end-start));

    }
    /**
     * this method makes bar Graph by collecting data from carbon intensity api
     */
    public void barGraphMaker(){
        long start = System.currentTimeMillis();
        String url = "https://api.carbonintensity.org.uk/intensity/2018-01-22/2018-01-23";
        logger.info("making a call on URL {}",url);
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.debug("Carbon Intensity Data: "+response.body());
        } catch (IOException | InterruptedException e) {
            logger.error("Exception "+e.getMessage());
            throw new RuntimeException(e);
        }
        String str;
        String substring = url.substring(url.length() - 25, url.length() - 1);
        if (response.statusCode() == 200) {
            logger.trace("making a call to the server using url "+url);
            str = response.body();
            logger.debug("response given by the end point  "+ substring +" is "+str);
            JSONObject jsonObject = new JSONObject(str);
            logger.debug("JSONObject data is : "+jsonObject);
            JSONArray arr = jsonObject.getJSONArray("data");
            logger.debug("JSONArray data is :"+arr);
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (int i = 1; i < 10; i++) {
                dataset.addValue((Number) arr.getJSONObject(i).getJSONObject("intensity").get("forecast"),
                        "forecast", arr.getJSONObject(i).get("from").toString().substring(11,16));
                dataset.addValue((Number) arr.getJSONObject(i).getJSONObject("intensity").get("actual"),
                        "actual", arr.getJSONObject(i).get("from").toString().substring(11,16));
                dataset.addValue((Number) arr.getJSONObject(i).getJSONObject("intensity").get("forecast"),
                        "forecast", arr.getJSONObject(i).get("to").toString().substring(11,16));
                dataset.addValue((Number) arr.getJSONObject(i).getJSONObject("intensity").get("actual"),
                        "actual", arr.getJSONObject(i).get("to").toString().substring(11,16));
            }
            SwingUtilities.invokeLater(() -> {
                ChartsMaker example = new ChartsMaker("CarbonIntensity Line Chart", dataset);
                example.setAlwaysOnTop(true);
                example.pack();
                example.setSize(1200, 800);
                example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                example.setVisible(true);
            });
        }
        else{
            logger.error("server responded with Error code {} for url end point {}",response.statusCode(),substring);
        }
        long end = System.currentTimeMillis();
        logger.info("barChartMaker() is executed in {} ms",(end-start));
    }
}
