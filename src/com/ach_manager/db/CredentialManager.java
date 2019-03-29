/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ach_manager.db;

import com.ach_manager.db.Utils.ProgramCode;
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
    
    /**
     * Add a new credential to the database
     * @param user Username
     * @param pass Password
     * @param phone Phone number
     * @param name Name of the user
     * @return an integer representing the id of the new credential
     */
    public int addCredentials(String user, String pass, String phone, String name) {
        // Initialize Connection
        Connection con = ConnectionManager.getConnection();
        // Statement of Intent
        Statement stmt = null;
        // Initialize id number
        int cred_id = -1;
        // Initial inser query string
        String in_query = "INSERT INTO "
                + "`credential`(`username`,`password`,`phone`,`name`) "
                + "VALUES ('" + user + "','" + pass + "','"
                + phone + "','" + name + "');";
        String id_query = "SELECT `id` FROM credential WHERE "
                + "username = '" + user + "' AND "
                + "password = '" + pass + "';";
        // New entry insertion into the database
        try {
            // Insert the new credential into the database
            stmt = con.createStatement();
            int vals = stmt.executeUpdate(in_query);
            if (vals != 1) { // Error check before proceeding
                System.out.println("ERROR; values effect not 1 (" + vals + ")");
                stmt.close();
                con.close();
                return -1;
            }
            // Get the ID of the new credential
            ResultSet rs = stmt.executeQuery(id_query);
            if (rs != null) {
                rs.next();
                cred_id = rs.getInt("id");
            } else {
                cred_id = -1;
            }
            // Close connections to avoid memory leaks
            stmt.close();
            con.close();
        } catch (Exception e) {
            cred_id = -1;
            e.printStackTrace();
        }
        return cred_id;
    }
    
    /**
     * Drop a user from the database
     * @param cred_id The ID of the user
     * @return A ProgramCode describing the outcome of the function
     * @throws SQLException 
     */
    ProgramCode dropCredentials(int cred_id) throws SQLException {
        // Initialize Connection
        Connection con = ConnectionManager.getConnection();
        // Holds results from query
        Statement stmt = null;
        // Program code for return
        ProgramCode code = ProgramCode.UNKNOWN_ERROR;
        // Query string
        String query = "DELETE FROM credential WHERE "
                + "id = " + cred_id + ";";
        try {
            // Attempt to drop the designated value
            stmt = con.createStatement();
            int vals = stmt.executeUpdate(query);
            if (vals == 1) {
                code = ProgramCode.SUCCESS;
            }
            else if (vals == 0) {
                code = ProgramCode.NO_ENTRY_FOUND;
            }
            else {
                System.out.println("ERROR; Value of " + vals + " returned (should be 0 or 1)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stmt.close();
            con.close();
        }
        return code;
    }
}
