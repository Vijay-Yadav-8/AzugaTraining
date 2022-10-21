/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training.week5;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class SqlAnalyticsQueries {

    private static final Logger logger = LogManager.getLogger(SqlAnalyticsQueries.class.getName());//used to store the logs for this class
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
    public static void main(String[] args) {
        if(con!=null) {
            try {
                if (!con.isClosed()) {
                    Statement statement = con.createStatement();
                    ResultSet rs = statement.executeQuery("select count(*) from museum");
                    while (rs.next()) {
                        System.out.println("No of rows in museum table are "+rs.getString(1));
                    }
                    rs=statement.executeQuery("select sum(objectID) from museum");
                    while (rs.next()) {
                        System.out.println("sum of objectID in museum table are "+rs.getString(1));
                    }
                    rs=statement.executeQuery("select max(objectID ) from museum");
                    while (rs.next()) {
                        System.out.println("max of objectID in museum table are "+rs.getString(1));
                    }
                    rs=statement.executeQuery("select min(objectID) from museum");
                    while (rs.next()) {
                        System.out.println("min of objectID in museum table are "+rs.getString(1));
                    }
                    rs=statement.executeQuery("select count(objectID) from museum where department=\"The American Wing\"");
                    while (rs.next()) {
                        System.out.println("no of objectID's having department as The American Wing in museum table are "+rs.getString(1));
                    }
                    rs=statement.executeQuery("select count(objectID) from museum where isPublicDomain=\"True\"");
                    while (rs.next()) {
                        System.out.println("no of objectID's having isPublicDomain as True in museum table are "+rs.getString(1));
                    }
                    rs=statement.executeQuery("select count(objectID) from museum where isPublicDomain=\"False\"");
                    while (rs.next()) {
                        System.out.println("no of objectID's having isPublicDomain as False in museum table are "+rs.getString(1));
                    }
                    rs=statement.executeQuery("SELECT * FROM museum WHERE accessionYear=1967 ORDER BY objectId DESC LIMIT 2");
                    while (rs.next()) {
                        System.out.println("no of objectID's having isPublicDomain as False in museum table are "+rs.getString(1));
                    }
                    rs=statement.executeQuery("\"SELECT * FROM ApiInfoBase WHERE accessionYear BETWEEN 1960 AND 2000 AND objectId NOT IN (1,2,3)");
                    while (rs.next()) {
                        System.out.println("no of objectID's having isPublicDomain as False in museum table are "+rs.getString(1));
                    }
                    rs=statement.executeQuery("\"SELECT * FROM ApiInfoBase WHERE objectId IN (SELECT objectId FROM museum WHERE accessionYear > 1970) ORDER BY accessionYear ");
                    while (rs.next()) {
                        System.out.println("no of objectID's having isPublicDomain as False in museum table are "+rs.getString(1));
                    }
                    rs = statement.executeQuery("select ObjectID,isHighLight from museum order by objectID");
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnCount = 3;
                    int limiter =1;
                    while (rs.next()){
                        if(limiter==1) {
                            for (int i = 1; i < columnCount; i++) {
                                System.out.print(rsmd.getColumnLabel(i) + "\t");
                            }
                            limiter++;
                            System.out.println();
                        }

                        for (int j = 1; j < columnCount; j++) {
                            System.out.print(rs.getString(j)+"\t");
                        }
                        System.out.println();
                    }
                    rs = statement.executeQuery("select ObjectID,isPublicDomain from museum group by objectID");
                    rsmd = rs.getMetaData();
                    limiter =1;
                    while (rs.next()){
                        if(limiter==1) {
                            for (int i = 1; i < columnCount; i++) {
                                System.out.print(rsmd.getColumnLabel(i) + "\t");
                            }
                            limiter++;
                            System.out.println();
                        }

                        for (int j = 1; j < columnCount; j++) {
                            System.out.print(rs.getString(j) + "\t");
                        }
                        System.out.println();
                    }
                } else {
                    System.out.println("connection is closed");
                }
            } catch (SQLException e) {
                logger.error("{} occurred while working with database",e.getMessage());
            }
            finally {
                try {
                    con.close();
                } catch (SQLException e) {
                   logger.error("{} occurred while closing the connection",e.getMessage());
                }
            }
        }else {
            logger.error("connection is null");
        }
    }

}
