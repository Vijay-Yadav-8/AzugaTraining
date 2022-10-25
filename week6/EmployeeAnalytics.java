/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training.week6;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class EmployeeAnalytics {
    private static final Logger logger = LogManager.getLogger(EmployeeAnalytics.class.getName());//used to store the logs for this class

    /**
     * This method is used to print the table data to the console
     * @param rs - object of Result set which is passed to fetch the data
     * @throws SQLException - it is thro
     */
    private void queryPrinter(ResultSet rs) throws SQLException {
        long start = System.currentTimeMillis();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        int limiter =1;
        while (rs.next()){
            if(limiter==1) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rsmd.getColumnLabel(i) + "  ");
                }
                limiter++;
                System.out.println();
            }
            for (int j = 1; j <= columnCount; j++) {
                System.out.print(rs.getString(j) + "    ");
            }
            System.out.println();
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
        long end = System.currentTimeMillis();
        logger.info("printing the data from the database table {} ms to complete", (end - start));
    }
    public static void main(String[] args) {
        Connection con = null;
        try {
            EmployeeAnalytics analytics = new EmployeeAnalytics();
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees","root","Azuga@Mac1");
            Statement stmt = con.createStatement();
            ResultSet rs =stmt.executeQuery("select employees.emp_no, employees.first_name, employees.last_name, salaries.salary from employees right join salaries on employees.emp_no=salaries.emp_no limit 10");
            analytics.queryPrinter(rs);
            rs =stmt.executeQuery("select count(*) from employees");
            analytics.queryPrinter(rs);
            rs =stmt.executeQuery("select count(emp_no) from employees union select count(emp_no) from salaries");
            analytics.queryPrinter(rs);
            rs =stmt.executeQuery("select count(distinct emp_no) from employees where emp_no in (select emp_no from titles)");
            analytics.queryPrinter(rs);
            rs =stmt.executeQuery("select e.emp_no,e.first_name,e.last_name from employees e where e.first_name like 'vish%'");
            analytics.queryPrinter(rs);
            rs =stmt.executeQuery("select e.emp_no,e.first_name,e.last_name from employees e where e.first_name like 'ar%' and e.last_name like 'Gen%'");
            analytics.queryPrinter(rs);
            rs =stmt.executeQuery("select e.first_name,e.last_name from employees e order by first_name limit 10");
            analytics.queryPrinter(rs);
            rs =stmt.executeQuery("select count(e.emp_no) from employees e inner join salaries s on e.emp_no=s.emp_no where s.salary>67043");
            analytics.queryPrinter(rs);
            rs =stmt.executeQuery("select count(e.emp_no) from employees e inner join salaries s on e.emp_no=s.emp_no where s.salary between 67043 and 100856");
            analytics.queryPrinter(rs);
            rs =stmt.executeQuery("select * from employees left join salaries on employees.emp_no=salaries.emp_no order by employees.emp_no desc limit 10");
            analytics.queryPrinter(rs);
            rs =stmt.executeQuery("select e.emp_no,e.first_name,e.gender,s.from_date,s.to_date,t.title,d.dept_no from employees e inner join salaries s on e.emp_no=s.emp_no inner join titles t on e.emp_no=t.emp_no inner join dept_manager d on e.emp_no=d.emp_no order by e.emp_no desc limit 10");
            analytics.queryPrinter(rs);
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("{} occurred while fetching from database",e.getMessage());
            System.exit(1);
        }
        finally {
            try {
                if (con != null) {
                    con.close();
                }
                else
                    logger.error("connection is not initialised");
            } catch (SQLException e) {
                logger.error("{} occurred while closing connection",e.getMessage());
            }
        }

    }
}
