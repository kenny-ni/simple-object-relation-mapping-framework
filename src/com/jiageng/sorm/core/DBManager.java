package com.jiageng.sorm.core;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * manage the connections
 */
public class DBManager {
    private static Properties configuration = new Properties();
    private static List<Connection> pool = new ArrayList<>();
    private static int minConn;
    private static int maxConn;

    static {//load configuration file
        try {
            configuration.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
            minConn = Integer.parseInt(configuration.getProperty("minConn"));
            maxConn = Integer.parseInt(configuration.getProperty("maxConn"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DBManager(){}

    public static Properties getConfiguration(){return configuration;}

    public static Connection getConnection(){
        try {
            Class.forName(configuration.getProperty("driver"));
            return DriverManager.getConnection(configuration.getProperty("url"), configuration.getProperty("user"),
                    configuration.getProperty("pwd"));
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection(ResultSet rs, Statement stmt, Connection conn){
        try{
            if(rs != null){
                rs.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            if (stmt != null){
                stmt.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            if (conn != null){
                conn.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void closeConnection(Statement stmt, Connection conn){
        try{
            if (stmt != null){
                stmt.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            if (conn != null){
                conn.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void closeConnection(Connection conn){
        try{
            if (conn != null){
                conn.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
