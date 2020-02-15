/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alexandra
 */
import java.sql.*;

public class JDBCConnection {

    public static void main(String[] argv) {
        System.out.println("------Testing Database Connection------");
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("Error: MySQL driver not found.");
            return;
        }
        System.out.println("Registered SQL Lite Driver.");
        Connection connection = null; 
        try { 
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/TelemetryDatabase", "root", "root");
            System.out.println("Established database connection");
        
        } catch (SQLException e) { 
            System.out.println("Error: Connection failed, check console output."); 
        } finally { 
            try
            { 
                if(connection != null) 
                    connection.close(); 
                System.out.println("Connection has been closed.");
            
            } catch (SQLException e) { 
                e.printStackTrace();
            }
        }
    }
}

