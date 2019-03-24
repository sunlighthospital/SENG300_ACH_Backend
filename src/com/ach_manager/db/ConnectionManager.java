package com.ach_manager.db;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Properties;

class ConnectionManager {
    // Establish the Connection
    static Connection makeConnection() {
        
        System.out.println("Loading driver...");

        // Driver loading (usually done by default, this just double checks)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }

        // Constants used to tell where to attempt connection
        final String db_url = "jdbc:mysql://localhost:3306/SENG300_Hospital";

        Properties props = new Properties();
        
        props.setProperty("user", "client");
        props.setProperty("password", "password");
        props.setProperty("serverTimezone", "MST");

        java.sql.Connection con = null;

        // Attempt Connection
        System.out.println("Attempting connection...");
        try {
            Properties info;
            con = DriverManager.getConnection(db_url, props);
            System.out.println("Connected Successfully!");
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection Failed!");
        }
        return con;
    }

    // Fetch the connection to the DB (for use with other wrapper functions)
    static Connection getConnection() {
        return makeConnection();
    }
}
