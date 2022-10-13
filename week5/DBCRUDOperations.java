/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training.week5;

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

    private static final Logger logger = LogManager.getLogger(DatabaseConnect.class.getName());//used to store the logs for this class
    static Connection con = null;//connecting to the database

    /* used to establish connection with database museumdb*/
    static {
        long start = System.currentTimeMillis();
        try {
            logger.info("connecting to the database");
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/museumdb", "root", "Azuga@Mac1");
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("{} occurred while connecting to the database", e.getMessage());
        }
        long end = System.currentTimeMillis();
        logger.info("Connected to the database in {} ms", (end - start));
    }

    /**
     * This method prints the table data to the console
     * @param tableName - used to take the table name
     * @throws SQLException- this exception is thrown when connection to the database is lost.
     */
    private void read(String tableName) throws SQLException {
        long start = System.currentTimeMillis();
        if(tableName!=null && !tableName.isBlank()){
        try {
            if (con != null && !con.isClosed()) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select * from " + tableName);
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rsmd.getColumnLabel(i) + "\t     |");
                }
                System.out.println();
                while (rs.next()) {
                    for (int i = 1; i < columnCount; i++) {
                        System.out.print(rs.getString(i) + "\t |");
                    }
                    System.out.println();
                }
            } else logger.error("connection to the database is not made");
        } catch (SQLException e) {
            logger.error("{} occurred while connecting to the database", e.getMessage());
        }
    }else
            logger.error("given json data is either empty or null");
        long end = System.currentTimeMillis();
        logger.info("querying the database and printing the data to the console took {} ms to complete", (end - start));
    }
    @SuppressWarnings("SameParameterValue")
    private void create(String tableName, String json) {
        long start = System.currentTimeMillis();
        if(json!=null && !json.isBlank() && tableName!=null && !tableName.isBlank()) {
            try {
                if (con != null && !con.isClosed()) {
                    StringBuilder sql = new StringBuilder("CREATE TABLE " + tableName + "(");
                    JSONObject obj = new JSONObject(json);
                    obj.keys().forEachRemaining(s -> sql.append(s).append(" varchar(256),"));
                    sql.deleteCharAt(sql.length() - 1);
                    sql.append(")");
                    logger.debug("sql query created from api call is :{}", sql);
                    PreparedStatement pstmt = con.prepareStatement(sql.toString());
                    logger.debug(pstmt.executeUpdate()+"row/s effected while creating table");
                    pstmt.close();
                } else
                    logger.error("connection to the database is lost");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else
            logger.error("given json or table data are either empty or null");
        long end = System.currentTimeMillis();
        logger.info("creating the table in the database took {} ms to complete", (end - start));
    }
    @SuppressWarnings("SameParameterValue")
    private void update(String tableName, String json,String condition) {
        long start = System.currentTimeMillis();
        if(json!=null && !json.isBlank() && tableName!=null && !tableName.isBlank() && condition!=null && !condition.isBlank()) {
            try {
                if (con != null && !con.isClosed()) {
                    StringBuilder sql = new StringBuilder("update " + tableName + " Set ");
                    JSONObject obj = new JSONObject(json);
                    obj.keys().forEachRemaining(s -> sql.append(s).append("=").append("'").append(obj.get(s)).append("'").append(" ,"));
                    sql.deleteCharAt(sql.length() - 1);
                    sql.append(" where ").append(condition);
                    logger.debug("sql query for updating table is created as :{}", sql);
                    PreparedStatement pstmt = con.prepareStatement(sql.toString());
                    logger.debug(pstmt.executeUpdate()+"row/s updated successfully");
                    pstmt.close();
                } else
                    logger.error("connection to the database is lost");
            } catch (SQLException e) {
                logger.error("{} occurred while updating the data in the database",e.getMessage());
            }
        }else
            logger.error("given json or table data are either empty or null");
        long end = System.currentTimeMillis();
        logger.info("updating the table in the database took {} ms to complete", (end - start));
    }
    @SuppressWarnings("SameParameterValue")
    private void delete(String tableName,String condition){
        long start = System.currentTimeMillis();
        if(condition!=null&&!condition.isBlank()&&tableName!=null&&!tableName.isBlank()) {
        try {
            if(con!=null&&!con.isClosed()){
                String sql = "delete from "+tableName+" where "+condition;
                Statement statement = con.createStatement();
                statement.executeUpdate(sql);
                logger.debug("query created for delete method is {}",sql);
                statement.close();
            }
        } catch (SQLException e) {
            logger.error("{} occurred while dealing with database in delete()",e.getMessage());
        }
        }else
            logger.error("given condition or table Name are either empty or null");
        long end = System.currentTimeMillis();
        logger.info("deleting the table data in the database took {} ms to complete", (end - start));
    }

    @SuppressWarnings("SameParameterValue")
    private void insert(String tableName, String json) {
        long start = System.currentTimeMillis();
        if(json!=null && !json.isBlank()) {
            StringBuilder insertQuery = new StringBuilder("insert into "+tableName+" values(");
            JSONObject obj = new JSONObject(json);
            obj.keys().forEachRemaining(s -> insertQuery.append("'").append(obj.get(s)).append("'").append(","));
            insertQuery.deleteCharAt(insertQuery.length() - 1);
            PreparedStatement pstmt1;
            try {
                if (con != null && !con.isClosed()) {
                    pstmt1 = con.prepareStatement(insertQuery.append(")").toString());
                    logger.debug(pstmt1.executeUpdate()+"row/s got effected while inserting data");
                    pstmt1.close();
                } else {
                    logger.error("connection to the database is lost while inserting data");
                }
            } catch (SQLException e) {
                logger.error("{} occurred while inserting data into table", e.getMessage());
            }
        }else
            logger.error("given json data is either empty or null");
        long end = System.currentTimeMillis();
        logger.info("inserting the data in the table took {} ms to completes", (end - start));
    }

    @SuppressWarnings("SameParameterValue")
    private String apiCaller(String url) throws InterruptedException {
        long start = System.currentTimeMillis();
        if(url!=null && !url.isBlank()) {
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
        }else
            logger.error("given url is either empty or null");
        long end = System.currentTimeMillis();
        logger.info("fetching the json data from the api call took {} ms to complete", (end - start));
        return null;
    }

    public static void main(String[] args) {
        DBCRUDOperations opn = new DBCRUDOperations();
        try {
            System.out.println("Data of the museum1 table");
            opn.read("museum1");
            System.out.println("Data of the museum table");
            opn.read("museum");
            String json = opn.apiCaller("https://collectionapi.metmuseum.org/public/collection/v1/objects/1");
            opn.create("FakeStore",json);
            opn.insert("FakeStore",json);
            System.out.println("Data of the FakeStore table");
            opn.read("FakeStore");
            json = opn.apiCaller("https://collectionapi.metmuseum.org/public/collection/v1/objects/2");
            opn.update("FakeStore",json,"objectID=1");
            System.out.println("Data of the FakeStore table after updation");
            opn.read("FakeStore");
            opn.delete("FakeStore","objectID=2");
            System.out.println("Data of the FakeStore table");
            opn.read("FakeStore");
        } catch (SQLException e) {
            logger.error("{} occurred while closing the connection", e.getMessage());
        } catch (InterruptedException e) {
            logger.error("{} occurred while connecting to the server with url {}", e.getMessage(),"https://collectionapi.metmuseum.org/public/collection/v1/objects/2");
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                logger.error("{} occurred while closing the connection", e.getMessage());
            }
        }

    }
}
