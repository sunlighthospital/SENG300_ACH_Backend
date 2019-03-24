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
    /**
     * Check the user's credentials against those which already exist in the database
     * @param user The user's username
     * @param pass The user's password
     * @return A JSONObject containing the following parameters:
     *  name: Name of the user (non-existent if credentials were not found)
     *  department: Department of the doctor, if they are a doctor
     *  reception_role: Role of the user in reception, if they are a receptionist
     *  admin_role: Role of the user in administration, if they are an administrator
     */
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
        String query = "SELECT c.name, p.name AS dep_name, r.role as rec_role, a.role as adm_role "
                + "FROM credential c "
                + "LEFT JOIN doctor d ON c.id = d.cred_id "
                + "LEFT JOIN department p ON d.dep_id = p.id "
                + "LEFT JOIN receptionist r ON c.id = r.cred_id "
                + "LEFT JOIN administrator a ON c.id = a.cred_id "
                + "WHERE c.username = '" + user + "' "
                + "AND c.password = '" + pass + "';";
        try {
            // Attempt the query
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            // Add the results to the json object parameters
            if (rs.next()) {
                results.put("name", rs.getString("name"));
                results.put("department", rs.getString("dep_name"));
                results.put("reception_role", rs.getString("rec_role"));
                results.put("admin_role", rs.getString("adm_role"));
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
