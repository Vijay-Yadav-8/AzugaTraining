/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training.week3;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class is used to create Bar Graph from the json data fetched from carbon intensity
 */
public class CarbonIntensity extends JFrame{

    /**
     * This constructor is used to create Bar graph from the given json data.
     * @param appTitle -title for Bar Graph
     * @param dataset -data required to make graph
     */
    public CarbonIntensity(String appTitle, CategoryDataset dataset) {
        super(appTitle);

        //Create chart
        JFreeChart chart=ChartFactory.createBarChart(
                "Carbon Intensity in UK", //Chart Title
                "Time with 30 min Gap", // Category axis
                "Readings (kW/hour)", // Value axis
                dataset,
                PlotOrientation.VERTICAL,
                true,true,false
        );

        ChartPanel panel=new ChartPanel(chart);
        setContentPane(panel);
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        String url = "https://api.carbonintensity.org.uk/intensity/2018-01-22/2018-01-23";
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String str;
        if (response.statusCode() == 200) {
            System.out.println("connected to server..✦..Fetching the data.✦..✦..");
            str = response.body();
            System.out.println(str);
            JSONObject jsonObject = new JSONObject(str);
            JSONArray arr = jsonObject.getJSONArray("data");
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (int i = 1; i <17; i=i*2) {
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
                CarbonIntensity example = new CarbonIntensity("CarbonIntensity Line Chart", dataset);
                example.setAlwaysOnTop(true);
                example.pack();
                example.setSize(1200, 800);
                example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                example.setVisible(true);
            });
        }
    }
}
