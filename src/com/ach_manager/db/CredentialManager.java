/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ach_manager.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

/**
 *
 * @author kalum
 */
public class CredentialManager {
    // Checks if a user's credentials are in the database
    // If they are, returns a JSON file containing their name, phone number, and department (if any)
    public JSONObject checkCredentials(String user, String pass) {
        // Initialize Connection
        Connection con = ConnectionManager.getConnection();
        // Holds results from query
        Statement stmt = null;
        // JSON representation of the schedule
        JSONObject results = new JSONObject();
        // Holds results from query
        ResultSet rs = null;
        // Query assembly (mySQL format)
        String query = "SELECT c.name, p.name AS dep_name "
                + "FROM credential c "
                + "LEFT JOIN doctor d ON c.id = d.cred_id "
                + "LEFT JOIN department p ON d.dep_id = p.id "
                + "WHERE c.username = '" + user + "' "
                + "AND c.password = '" + pass + "';";
        try {
            // Attempt the query
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            // Add the results to the json object parametes
            if (rs.next()) {
                results.put("name", rs.getString("name"));
                results.put("department", rs.getString("dep_name"));
            } else {
                results.put("ERROR", "CREDENTIALS NOT FOUND (RESPONSE.NOT_FOUND)");
            }
            // Close the connections to avoid memory leaks
            stmt.close();
            con.close();
        } catch (SQLException e) {
            results = null;
            System.out.println(e);
        }
        return results;
    }
}
