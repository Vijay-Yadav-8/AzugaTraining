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
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * This Class creates Pie chart by obtaining json data from cryptocurrency api
 */
public class CryptoPieChart extends JFrame {
    /**
     * This Constructor is used to create Pie chart with the given input
     * @param title-Used to set the title for Output image
     * @param dataset-Data required to create a pie chart
     */
    public CryptoPieChart(String title, PieDataset<String> dataset) {
        super(title);

        // Creating chart
        JFreeChart chart = ChartFactory.createPieChart(
                "Market capital Percentage",
                dataset,
                true,
                true,
                false);

        //Format Label
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                "crypto {0} : ({1})", new DecimalFormat("00.00"), new DecimalFormat("00%"));
        ((PiePlot<?>) chart.getPlot()).setLabelGenerator(labelGenerator);

        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        String url = "https://api.coingecko.com/api/v3/global";
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String str ;
        if (response.statusCode() == 200) {
            str = response.body();
            String[] str1 = str.split("\"market_cap_percentage\"");

            String rem = str1[1].replace(":{", "").replace("}}", "");

            Map<String, Double> map = new HashMap<>();
            String[] str2 = rem.split(",");
            double d;
            for (String value : str2) {
                if (!(value.charAt(value.length() - 1) == '}')) {
                    String[] s = value.split(":");
                    d = Double.parseDouble(s[1]);
                    System.out.println(s[0] + "" + d);
                    map.put(s[0].replace("\"", ""), d);
                } else {
                    break;
                }
            }
            DefaultPieDataset<String> defaultPieDataset = new DefaultPieDataset<>();
            map.forEach(defaultPieDataset::setValue);
            SwingUtilities.invokeLater(() -> {
                CryptoPieChart example = new CryptoPieChart("Crypto PieChart", defaultPieDataset);
                example.setSize(800, 400);
                example.setLocationRelativeTo(null);
                example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                example.setVisible(true);
            });
            System.out.println(str);
        }
    }

}