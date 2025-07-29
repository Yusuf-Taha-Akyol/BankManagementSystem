package config;


import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/bank_system";
    public static final String DB_USER = "postgres";
    public static final String DB_PASSWORD = "12345678";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Successfully connected to the database");
        } catch (Exception e){
            System.out.println("Connection error :" + e);
        }

        return conn;
    }
}
