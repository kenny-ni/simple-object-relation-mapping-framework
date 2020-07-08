package com.jiageng;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
	    try{
	        Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb?serverTimezone=UTC","kenny", "njG87440656");
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO employee (Name, Contact, Age) VALUES ('TU', 1234, 22)";
            stmt.execute(sql);
            sql = "knj";
            stmt.execute(sql);

            conn.commit();
            stmt.close();
            conn.close();
        }catch (Exception e){
	        e.printStackTrace();
        }
    }
}
