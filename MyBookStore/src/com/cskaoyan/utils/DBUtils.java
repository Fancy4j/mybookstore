package com.cskaoyan.utils;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtils {

    static DataSource dataSource =null;

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static void setDataSource(DataSource dataSource) {
        DBUtils.dataSource = dataSource;
    }

    static {

       InputStream resourceAsStream = DBUtils.class.getClassLoader().getResourceAsStream("db.properties");

       Properties properties = new Properties();
       try {
           properties.load(resourceAsStream);

           BasicDataSourceFactory factory = new BasicDataSourceFactory();
           dataSource = factory.createDataSource(properties);


       } catch (IOException e) {
           e.printStackTrace();
       } catch (Exception e) {
           e.printStackTrace();
       }


   }


    public static Connection getConnection() {


        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  conn;
    }

    public static void release(ResultSet rs, Statement st, Connection conn) {


        try {
            if (null!=rs){
                rs.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (null!=st){
                st.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (null!=conn){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
