/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;

/**
 * This class performs crud operations
 */
public class DBCRUDOperations {

    public static final Logger logger = LogManager.getLogger(DBCRUDOperations.class.getName());//used to store the logs for this class

    /**
     * This method prints the table data to the console
     *
     * @param tableName - used to take the table name
     */
    public String read(String tableName) {
        long start = System.currentTimeMillis();
        Connection con = null;
        try {
            logger.info("connecting to the database");
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/museumdb", "root", "Azuga@Mac1");
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("{} occurred while connecting to the database", e.getMessage());
        }
        StringBuilder output = new StringBuilder();
        if (tableName != null && !tableName.isBlank()) {
            try {
                if (con != null && !con.isClosed()) {
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("select * from " + tableName);
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnCount = rsmd.getColumnCount();
                    output.append("[");
                    while (rs.next()) {
                        output.append("{");
                        for (int i = 1; i < columnCount; i++) {
                            output.append("\"").append(rsmd.getColumnLabel(i)).append("\"").append(":");
                            if (rs.getString(i).startsWith("[") || rs.getString(i).startsWith("{")) {
                                output.append(rs.getString(i)).append(",");
                            } else {
                                output.append("\"").append(rs.getString(i)).append("\"").append(",");
                            }
                        }
                        output.deleteCharAt(output.length() - 1);
                        output.append("},");
                    }
                    output.deleteCharAt(output.length() - 1);
                    output.append("]");
                } else logger.error("connection to the database is not made");
            } catch (SQLException e) {
                logger.error("{} occurred while connecting to the database", e.getMessage());
            }
        } else
            logger.error("given json data is either empty or null");
        long end = System.currentTimeMillis();
        logger.info("querying the database and printing the data to the console took {} ms to complete", (end - start));
        return output.toString();
    }

    /**
     * @param tableName -
     * @param json-
     */
    @SuppressWarnings("SameParameterValue")
    public String create(String tableName, StringBuilder json) throws SQLException {
        String output=null;
        long start = System.currentTimeMillis();
        Connection con = null;
        try {
            logger.info("connecting to the database");
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/museumdb", "root", "Azuga@Mac1");
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("{} occurred while connecting to the database", e.getMessage());
        }
        if (json != null && tableName != null && !tableName.isBlank()) {
            try {
                if (con != null && !con.isClosed()) {
                    if (json.charAt(0) == '[') {
                        json.replace(0, 1, "");
                        json.replace(json.length() - 1, json.length(), "");
                    }
                    StringBuilder sql = new StringBuilder("CREATE TABLE " + tableName + "(");
//                    StringBuilder primaryKey = new StringBuilder(" Primary Key(");
                    JSONObject obj = new JSONObject(json.toString());
//                    AtomicInteger i= new AtomicInteger(1);
                    obj.keys().forEachRemaining(s ->{
                        switch (obj.get(s).getClass().getSimpleName()) {
                            case "Integer":
                                sql.append(s).append(" int,");
//                                if(i.get() <=3){
//                                    primaryKey.append(s).append(",");
//                                    i.getAndIncrement();
//                                }
                                break;
                            case "Double":
                                sql.append(s).append(" Double,");
                                break;
                            case "BigDecimal":
                                sql.append(s).append(" varchar(256),");
                                break;
                            case "Boolean":
                                    sql.append(s).append(" varchar(20),");
                                break;
                            case "String":
                            case "Null":
                            case "JSONArray":
                            case "JSONObject":
                            default:
                                sql.append(s).append(" text,");
                        }
                    });
//                    sql.append(primaryKey.deleteCharAt(primaryKey.length()-1)).append(")");
//                    System.out.println(primaryKey);
                    sql.deleteCharAt(sql.length()-1);
                    sql.append(")");
                    System.out.println(sql);
                    logger.debug("sql query created from api call is :{}", sql);
                    PreparedStatement pstmt = con.prepareStatement(sql.toString());
                    logger.debug(pstmt.executeUpdate() + "row/s effected while creating table");
                    output = "table created successfully";
                    pstmt.close();
                } else
                    logger.error("connection to the database is lost");
            } catch (SQLException e) {
                logger.error("{} occurred while connecting to the database",e.getMessage());
            }
        } else
            logger.error("given json or table data are either empty or null");
        long end = System.currentTimeMillis();
        logger.info("creating the table in the database took {} ms to complete", (end - start));
        return output;
    }

    @SuppressWarnings("SameParameterValue")
    public String update(String tableName, String json, String condition) {
        long start = System.currentTimeMillis();
        String output =null;
        Connection con = null;
        try {
            logger.info("connecting to the database");
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/museumdb", "root", "Azuga@Mac1");
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("{} occurred while connecting to the database", e.getMessage());
        }
        System.out.println(json);
        System.out.println(tableName);
        System.out.println(condition);
        if (json != null && !json.isBlank() && tableName != null && !tableName.isBlank() && condition != null && !condition.isBlank()) {
            try {
                if (con != null && !con.isClosed()) {
                    StringBuilder sql = new StringBuilder("update " + tableName + " Set ");
                    JSONObject obj = new JSONObject(json);
                    obj.keys().forEachRemaining(s -> sql.append(s).append("=").append("'").append(obj.get(s)).append("'").append(" ,"));
                    sql.deleteCharAt(sql.length() - 1);
                    sql.append(" where ").append(condition);
                    System.out.println(sql);
                    logger.debug("sql query for updating table is created as :{}", sql);
                    PreparedStatement pstmt = con.prepareStatement(sql.toString());
                    logger.debug(pstmt.executeUpdate() + "row/s updated successfully");
                    output="updated successfully";
                    pstmt.close();
                } else
                    logger.error("connection to the database is lost");
            } catch (SQLException e) {
                logger.error("{} occurred while updating the data in the database", e.getMessage());
            }
        } else
            logger.error("given json or table data are either empty or null");
        long end = System.currentTimeMillis();
        logger.info("updating the table in the database took {} ms to complete", (end - start));
        return output;
    }

    public String delete(String tableName, String condition) {
        long start = System.currentTimeMillis();
        String output = null;
        if (condition != null && !condition.isBlank() && tableName != null && !tableName.isBlank()) {
        Connection con = null;
        try {
            logger.info("connecting to the database");
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/museumdb", "root", "Azuga@Mac1");
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("{} occurred while connecting to the database", e.getMessage());
        }
        try {
            if (con != null && !con.isClosed()) {
                    String sql = "delete from " + tableName + " where " + condition;
                    Statement statement = con.createStatement();
                    System.out.println(sql);
                    System.out.println(statement.executeUpdate(sql));
                    logger.debug("query created for delete method is {}", sql);
                    output = "deleted successfully";
                    statement.close();
                }
            } catch (SQLException e) {
                logger.error("{} occurred while dealing with database in delete()", e.getMessage());
            }
        } else
            logger.error("given condition or table Name are either empty or null");
        long end = System.currentTimeMillis();
        logger.info("deleting the table data in the database took {} ms to complete", (end - start));
        return output;
    }

    @SuppressWarnings("SameParameterValue")
    public String insert(String tableName, StringBuilder json) {
        String output = null;
        System.out.println(tableName + " " + json);
        long start = System.currentTimeMillis();
        Connection con = null;
        try {
            logger.info("connecting to the database");
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/museumdb", "root", "Azuga@Mac1");
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("{} occurred while connecting to the database", e.getMessage());
        }
        if (json != null) {
            if (json.charAt(0) == '[') {
                json.replace(0, 1, "");
                json.replace(json.length() - 1, json.length(), "");
            }
            System.out.println(json);
            StringBuilder insertQuery = new StringBuilder("insert into " + tableName + "(");
            JSONObject obj = new JSONObject(json.toString());
            obj.keys().forEachRemaining(s -> insertQuery.append(s).append(","));
            insertQuery.deleteCharAt(insertQuery.length() - 1);
            insertQuery.append(") values(");
            obj.keys().forEachRemaining(s -> {
                insertQuery.append("'").append(String.valueOf(obj.get(s)).replaceAll("'", "")).append("'").append(",");
            });
            insertQuery.deleteCharAt(insertQuery.length() - 1);
            insertQuery.append(")");
            logger.debug(insertQuery);
            PreparedStatement pstmt1;
            try {
                if (con != null && !con.isClosed()) {
                    pstmt1 = con.prepareStatement(insertQuery.toString());
                    System.out.println((pstmt1.executeUpdate() + "row/s got effected while inserting data"));
                    output="inserted successfully";
                    pstmt1.close();
                } else {
                    System.out.println("connection to the database is lost while inserting data");
                    logger.error("connection to the database is lost while inserting data");
                }
            } catch (SQLException e) {
                System.out.println(e);
                logger.error("{} occurred while inserting data into table", e.getMessage());
            }
        } else
            logger.error("given json data is either empty or null");
        long end = System.currentTimeMillis();
        logger.info("inserting the data in the table took {} ms to completes", (end - start));
        return output;
    }

    @SuppressWarnings("SameParameterValue")
    public String apiCaller(String url) throws InterruptedException {
        long start = System.currentTimeMillis();

        if (url != null && !url.isBlank()) {
            logger.info("Making the call on url: {}", url);
            HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = null;
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (InterruptedException | IOException e) {
                logger.error("{} occurred while getting a response museum api url {}", e, url);
            }
            if (response != null) {
                if (response.statusCode() == 200) {
                    long end = System.currentTimeMillis();
                    logger.info("fetching the json data from the api call took {} ms to complete", (end - start));
                    return response.body();
                } else {
                    logger.error("connection to the server with specified url is lost i.e, {}", url);
                    long end = System.currentTimeMillis();
                    logger.info("fetching the json data from the api call took {} ms to complete", (end - start));
                    throw new InterruptedException("server responded with error code " + response.statusCode());
                }
            } else
                logger.error("server is not responding for url as {}", url);
        } else
            logger.error("given url is either empty or null");
        long end = System.currentTimeMillis();
        logger.info("fetching the json data from the api call took {} ms to complete", (end - start));
        return null;
    }

    public static void main(String[] args) {

        DBCRUDOperations opn = new DBCRUDOperations();
//        try {
//            System.out.println("Data of the museum1 table");
//            System.out.println(opn.read("museum1"));
//            System.out.println("Data of the museum table");
//            System.out.println(opn.read("museum"));
//            StringBuilder json1=new StringBuilder();
//            String jso = (opn.apiCaller("https://collectionapi.metmuseum.org/public/collection/v1/objects/2"));
//            opn.create("FakeStore", new StringBuilder(jso));
//            for (int i = 1; i <= 5; i++) {
//                String json=(opn.apiCaller("https://collectionapi.metmuseum.org/public/collection/v1/objects/"+i));
//                if(json!=null)
//                    opn.insert("FakeStore", new StringBuilder(json));
//            }
//            json.deleteCharAt(json.length() - 1);
//            System.out.println(json1);
//            opn.insert("FakeStore",json1);
//            System.out.println("Data of the FakeStore table");
//            System.out.println(opn.read("FakeStore"));
//            String json = opn.apiCaller("https://collectionapi.metmuseum.org/public/collection/v1/objects/3");
//        System.out.println(json);
//            System.out.println(opn.update("FakeStore",json,"objectID=31"));
//            System.out.println("Data of the FakeStore table after updation");
//            System.out.println(opn.read("FakeStore"));
//            opn.delete("FakeStore","jso");
//            System.out.println("Data of the FakeStore table after deleting");
//            System.out.println(opn.read("FakeStore"));
//        } catch (InterruptedException e) {
//            logger.error("{} occurred while connecting to the server with url {}", e.getMessage(), "https://collectionapi.metmuseum.org/public/collection/v1/objects/2");
//        }
//        catch (SQLException e) {
//            System.err.println(e.getMessage());
//        }
//        finally {
//            try {
//                con.close();
//            } catch (SQLException e) {
//                logger.error("{} occurred while closing the connection", e.getMessage());
//            }
//        }
//
    }
}
