/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training.week5;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;

/**
 * This class is used to fetch the data from museum api as json string and appends to database
 */
public class DatabaseConnect {
    private static final Logger logger = LogManager.getLogger(DatabaseConnect.class.getName());

    /**
     * This method is used to fill the data into database by making a call to museum api
     * @param url -used to specify museum api url
     * @throws InterruptedException - this exception is thrown when connection is lost
     */

    public static void apiCallMaker(String url) throws InterruptedException {
        if (url != null && !(url.isBlank())) {
            logger.trace("apiCallMaker() is invoked");
            long start = System.currentTimeMillis();
            logger.info("Making the call on url: {}", url);
            HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = null;
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (InterruptedException | IOException e) {
                logger.error("{} occurred while getting a response museum api url {}", e, url);
                long end = System.currentTimeMillis();
                logger.info("MuseumApi() is executed in {} ms", (end - start));
            }
            if (response != null) {
                if (response.statusCode() == 200) {
                    logger.debug("Response from server is :{}", response.body());
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/museumdb", "root", "Azuga@Mac1");
                        PreparedStatement pstmt = con.prepareStatement("INSERT INTO museum values (?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?)");
                        JSONObject record = new JSONObject(response.body());
                        pstmt.setInt(1, Integer.parseInt(String.valueOf(record.get("objectID"))));
                        pstmt.setBoolean(2, Boolean.getBoolean(String.valueOf(record.get("isHighlight"))));
                        pstmt.setString(3, (String) record.get("accessionNumber"));
                        pstmt.setString(4, (String) record.get("accessionYear"));
                        pstmt.setBoolean(5, Boolean.getBoolean(String.valueOf(record.get("isPublicDomain"))));
                        pstmt.setString(6, (String) record.get("primaryImage"));
                        pstmt.setString(7, (String) record.get("primaryImageSmall"));
                        pstmt.setString(8, (String) record.get("department"));
                        pstmt.setString(9, (String) record.get("objectName"));
                        pstmt.setString(10, (String) record.get("title"));
                        pstmt.setString(11, (String) record.get("culture"));
                        pstmt.setString(12, (String) record.get("period"));
                        pstmt.setString(13, (String) record.get("dynasty"));
                        pstmt.setString(14, (String) record.get("reign"));
                        pstmt.setString(15, (String) record.get("portfolio"));
                        pstmt.setString(16, (String) record.get("artistRole"));
                        pstmt.setString(17, (String) record.get("artistPrefix"));
                        pstmt.setString(18, (String) record.get("artistDisplayName"));
                        pstmt.setString(19, (String) record.get("artistDisplayBio"));
                        pstmt.setString(20, (String) record.get("artistSuffix"));
                        pstmt.setString(21, (String) record.get("artistAlphaSort"));
                        pstmt.setString(22, (String) record.get("artistNationality"));
                        pstmt.setString(23, (String) record.get("artistBeginDate"));
                        pstmt.setString(24, (String) record.get("artistEndDate"));
                        pstmt.setString(25, (String) record.get("artistGender"));
                        pstmt.setString(26, (String) record.get("artistWikidata_URL"));
                        pstmt.setString(27, (String) record.get("artistULAN_URL"));
                        pstmt.setString(28, (String) record.get("objectDate"));
                        pstmt.setString(29, "\"" + record.get("objectBeginDate") + "\"");
                        pstmt.setString(30, "\"" + record.get("objectEndDate") + "\"");
                        pstmt.setString(31, (String) record.get("medium"));
                        pstmt.setString(32, (String) record.get("dimensions"));
                        pstmt.setString(33, (String) record.get("creditLine"));
                        pstmt.setString(34, (String) record.get("geographyType"));
                        pstmt.setString(35, (String) record.get("city"));
                        pstmt.setString(36, (String) record.get("state"));
                        pstmt.setString(37, (String) record.get("county"));
                        pstmt.setString(38, (String) record.get("country"));
                        pstmt.setString(39, (String) record.get("region"));
                        pstmt.setString(40, (String) record.get("subregion"));
                        pstmt.setString(41, (String) record.get("locale"));
                        pstmt.setString(42, (String) record.get("locus"));
                        pstmt.setString(43, (String) record.get("excavation"));
                        pstmt.setString(44, (String) record.get("river"));
                        pstmt.setString(45, (String) record.get("classification"));
                        pstmt.setString(46, (String) record.get("rightsAndReproduction"));
                        pstmt.setString(47, (String) record.get("linkResource"));
                        pstmt.setString(48, (String) record.get("metadataDate"));
                        pstmt.setString(49, (String) record.get("repository"));
                        pstmt.setString(50, (String) record.get("objectURL"));
                        pstmt.setString(51, (String) record.get("objectWikidata_URL"));
                        pstmt.setBoolean(52, Boolean.getBoolean(String.valueOf(record.get("isTimelineWork"))));
                        pstmt.setString(53, (String) record.get("GalleryNumber"));
                        pstmt.executeUpdate();
                        if (String.valueOf(record.get("tags")).equals("null")) {
                            System.out.println("tags has no data");
                            PreparedStatement statement = con.prepareStatement("insert into tags(objectID) values(" + record.get("objectID") + ")");
                            statement.executeUpdate();
                        } else {
                            JSONArray array = record.getJSONArray("tags");
                            PreparedStatement statement = con.prepareStatement("insert into tags(objectID,term,AAT_URL,Wikidata_URL) values(?,?,?,?)");
                            for (Object ob : array) {
                                JSONObject record1 = (JSONObject) ob;
                                System.out.println(record1.get("term"));
                                statement.setInt(1, Integer.parseInt(String.valueOf(record.get("objectID"))));
                                statement.setString(2, (String) record1.get("term"));
                                statement.setString(3, (String) record1.get("AAT_URL"));
                                statement.setString(4, (String) record1.get("Wikidata_URL"));
                                statement.executeUpdate();
                            }
                        }
                        if (String.valueOf(record.get("constituents")).equals("null")) {
                            System.out.println("constituents has no data");
                            PreparedStatement statement = con.prepareStatement("insert into constituents(objectID) values(" + record.get("objectID") + ")");
                            statement.executeUpdate();
                        } else {
                            JSONArray array = record.getJSONArray("constituents");
                            PreparedStatement statement = con.prepareStatement("insert into constituents(objectID,constituentID,role,name,constituentULAN_URL,constituentWikidata_URL,gender) values(?,?,?,?,?,?,?)");
                            for (Object ob : array) {
                                JSONObject record1 = (JSONObject) ob;
                                statement.setInt(1, Integer.parseInt(String.valueOf(record.get("objectID"))));
                                statement.setInt(2, Integer.parseInt(String.valueOf(record1.get("constituentID"))));
                                statement.setString(3, (String) record1.get("role"));
                                statement.setString(4, (String) record1.get("name"));
                                statement.setString(5, (String) record1.get("constituentULAN_URL"));
                                statement.setString(6, (String) record1.get("constituentWikidata_URL"));
                                statement.setString(7, (String) record1.get("gender"));
//                                statement.executeUpdate();
                            }
                        }
                        if (String.valueOf(record.get("measurements")).equals("null")) {
                            System.out.println("measurements has no data");
                            PreparedStatement statement = con.prepareStatement("insert into measurements(objectID) values(" + record.get("objectID") + ")");
                            statement.executeUpdate();
                        } else {
                            JSONArray array = record.getJSONArray("measurements");
                            PreparedStatement statement = con.prepareStatement("insert into measurements(objectID,elementName,elementDescription) values(?,?,?)");
                            for (Object ob : array) {
                                JSONObject record1 = (JSONObject) ob;
                                statement.setInt(1, Integer.parseInt(String.valueOf(record.get("objectID"))));
                                statement.setString(2, (String) record1.get("elementName"));
                                statement.setString(3, "\""+record1.get("elementDescription")+"\"");
                                if (String.valueOf(record1.get("elementMeasurements")).equals("null")) {
                                    System.out.println("elementMeasurements has no data");
                                    PreparedStatement statement1 = con.prepareStatement("insert into elementMeasurements(objectID) values(" + record.get("objectID") + ")");
                            statement1.executeUpdate();
                                } else {
                                    JSONObject record2 = new JSONObject(String.valueOf(record1.get("elementMeasurements")));
                                    PreparedStatement statement1 = con.prepareStatement("insert into elementMeasurements(objectID,Height,Width,Length) values(?,?,?)");
                                    statement1.setInt(1, Integer.parseInt(String.valueOf(record.get("objectID"))));
                                    statement1.setString(2, record2.has("Height")?record2.get("Height").toString():"null");
                                    statement1.setString(3, record2.has("Width")?record2.get("Width").toString():"null");
                                    statement1.setString(3, record2.has("Length")?record2.get("Length").toString():"null");
                                statement.executeUpdate();
                                }
                                statement.executeUpdate();
                            }
                        }
                        if (String.valueOf(record.get("additionalImages")).equals("null")) {
                            System.out.println("additionalImages has no data");
                            PreparedStatement statement = con.prepareStatement("insert into additionalImages(objectID) values(" + record.get("objectID") + ")");
                            statement.executeUpdate();
                        } else {
                            JSONArray array = record.getJSONArray("additionalImages");
                            PreparedStatement statement = con.prepareStatement("insert into additionalImages(objectID,additional_images) values(?,?)");
                            for (Object ob : array) {
                                statement.setInt(1, Integer.parseInt(String.valueOf(record.get("objectID"))));
                                statement.setString(2, ob.toString());
                                statement.executeUpdate();
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        logger.error("{} occurred while dealing with database", e.getMessage());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    logger.error("server thrown {} error while fetching with url:{}", response.statusCode(), url);
                    throw new InterruptedException("server is not responding......... " + response.statusCode() + " error");
                }
            } else {
                logger.error("No response from the server for url as {}", url);
            }
            long end = System.currentTimeMillis();
            logger.info("MuseumApi() is executed in {} ms", (end - start));
            logger.trace("apiCallMaker() is executed");
        } else {
            logger.error("url is either empty or null");
            throw new NullPointerException("url is empty");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        apiCallMaker("https://collectionapi.metmuseum.org/public/collection/v1/objects/525");
    }
}
