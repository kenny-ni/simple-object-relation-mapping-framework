package com.jiageng.sorm.core;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Stack;

/**
 * manage the connections
 */
public class DBManager {
    private static Properties configuration = new Properties();
    private static Stack<Connection> pool = new Stack<>();
    private static int minConn;
    private static int maxConn;

    static {//load configuration file
        try {
            configuration.load(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("db.properties")); //load configuration
            Class.forName(configuration.getProperty("driver")); //load driver
            minConn = Integer.parseInt(configuration.getProperty("minConn"));
            maxConn = Integer.parseInt(configuration.getProperty("maxConn"));
            int count = 0;
            while (pool.size() < minConn){ //init connection pool
                pool.add(establishConnection());
                ++count;
            }
            System.out.println("established connection num: " + count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DBManager(){}

    public static Properties getConfiguration(){return configuration;}

    /**
     * establish connection with DB
     * @return Connection object
     */
    private static Connection establishConnection(){
        try {
            return DriverManager.getConnection(configuration.getProperty("url"), configuration.getProperty("user"),
                    configuration.getProperty("pwd"));
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public synchronized static Connection getConnection(){
        if(pool.size() > 0){
            System.out.println("get from pool");
            return pool.pop();
        } else{
            System.out.println("new establish");
          return establishConnection();
        }
    }

    public synchronized static void closeConnection(ResultSet rs, Statement stmt, Connection conn){
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
                if (pool.size() >= maxConn){
                    System.out.println("connection closed");
                    conn.close();
                }else{
                    System.out.println("add back to pool");
                    pool.add(conn);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public synchronized static void closeConnection(Statement stmt, Connection conn){
        try{
            if (stmt != null){
                stmt.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            if (conn != null){
                if (pool.size() >= maxConn){
                    System.out.println("connection closed");
                    conn.close();
                }else{
                    System.out.println("add back to pool");
                    pool.add(conn);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public synchronized static void closeConnection(Connection conn){
        try{
            if (conn != null){
                if (pool.size() >= maxConn){
                    System.out.println("connection closed");
                    conn.close();
                }else{
                    System.out.println("add back to pool");
                    pool.add(conn);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
