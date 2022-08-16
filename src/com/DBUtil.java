package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DBUtil {
    public static Connection getConnection()
    {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn  = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms?autoReconnect=true&useSSL=false","root","<your_password>");
            System.out.println("success");
            return conn;
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Driver Not Found");

        }
        catch(SQLException e)
        {
            System.out.println("Could not connect to database");
            e.printStackTrace();

        }
        return null;

    }
}
