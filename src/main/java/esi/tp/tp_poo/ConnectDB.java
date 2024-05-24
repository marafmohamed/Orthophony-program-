package esi.tp.tp_poo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    // Single instance of the class
    private static ConnectDB instance;
    private Connection connection;
    private String url = "jdbc:sqlite:TPdb.sqlite"; // Specify your database URL

    // Private constructor to prevent instantiation from outside
    private ConnectDB() {
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Connection Successful");
        } catch (SQLException e) {
            System.out.println("Error Connecting to Database");
            e.printStackTrace();
        }
    }

    // Public method to provide access to the single instance of the class
    public static ConnectDB getInstance() {
        if (instance == null) {
            instance = new ConnectDB();
        }
        return instance;
    }

    // Method to get the connection
    public Connection getConnection() {
        return connection;
    }

    // Method to close the connection
    public void closeConnection() {
        try {
            if (connection != null) {
                System.out.println("Connection Closed");
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
