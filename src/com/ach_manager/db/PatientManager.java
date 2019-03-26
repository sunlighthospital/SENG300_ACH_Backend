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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author kalum
 */
public class PatientManager {
    /**
     * Add a new patient to the database
     * @param name Name of the patient
     * @param sex Gender (0 for unknown, 1 for female, 2 for male)
     * @param phone Phone number
     * @return The id of the patient added to the database (-1 if the addition failed)
     */
    public int addPatient(String name, int sex, String phone) {
        // Initialize Connection
        Connection con = ConnectionManager.getConnection();
        // Statement of Intent
        Statement stmt = null;
        // Initialize id number
        int pat_id = -1;
        // Initial insert query string
        String in_query = "INSERT INTO "
                + "`patient`(`name`,`phone`) "
                + "VALUES ('" + name + "','" + phone + "');";
        // Get the id of the new entry
        String id_query = "SELECT `id` FROM patient WHERE "
                + "name = '" + name + "' AND "
                + "phone = '" + phone + "';";
        // New entry insertion into the database
        try {
            // Insert the new patient into the database
            stmt = con.createStatement();
            int vals = stmt.executeUpdate(in_query);
            if (vals != 1) { // Error check before proceeding
                System.out.println("ERROR; values effect not 1 (" + vals + ")");
                stmt.close();
                con.close();
                return -1;
            }
            // Get the ID of the new patient
            ResultSet rs = stmt.executeQuery(id_query);
            // Try to add the gender, if provided
            if (rs != null) {
                rs.next();
                pat_id = rs.getInt("id");
                // Gender query
                if (sex == 1 || sex == 2) {
                    String add_query = "UPDATE patient SET `sex` = " + (sex-1)
                            + " WHERE `id` = " + pat_id + ";";
                    vals = stmt.executeUpdate(add_query);
                }
                if (vals != 1) {
                    System.out.println("ERROR; values effect not 1 (" + vals + ")");
                    stmt.close();
                    con.close();
                    return -1;
                }
            } else {
                pat_id = -1;
            }
            // Close connections to avoid memory leaks
            stmt.close();
            con.close();
        } catch (Exception e) {
            pat_id = -1;
            e.printStackTrace();
        }
        return pat_id;
    }
    
    /**
     * Drop a patient from the database
     * @param pat_id The ID of the patient
     * @return A ProgramCode describing the outcome of the function
     * @throws SQLException 
     */
    Utils.ProgramCode dropPatient(int pat_id) throws SQLException {
        // Initialize Connection
        Connection con = ConnectionManager.getConnection();
        // Holds results from query
        Statement stmt = null;
        // Program code for return
        Utils.ProgramCode code = Utils.ProgramCode.UNKNOWN_ERROR;
        // Query string
        String query = "DELETE FROM patient WHERE "
                + "id = " + pat_id + ";";
        try {
            // Attempt to drop the designated value
            stmt = con.createStatement();
            int vals = stmt.executeUpdate(query);
            if (vals == 1) {
                code = Utils.ProgramCode.SUCCESS;
            }
            else if (vals == 0) {
                code = Utils.ProgramCode.NO_ENTRY_FOUND;
            }
            else {
                System.out.println("ERROR; Value of " + vals + " returned (should be 0 or 1)");
                return code;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stmt.close();
            con.close();
            return code;
        }
    }
    
    /**
     * Fetch a JSON object containing all patient record data
     * @return A JSONObject containing a JSONObjectList "patients", with elements containing the following parameters:
     *  id: The patient's ID number
     *  name: The patient's name
     *  phone: The patient's phone number (as a string)
     *  sex: The patients sex: 0 for female, 1 for male
     */
    public JSONObject getAllPatients() throws SQLException {
        // Initialize Connection
        Connection con = ConnectionManager.getConnection();
        // Holds results from query
        Statement stmt = null;
        // JSON representation of the schedule
        JSONObject pat_record = new JSONObject();
        JSONArray pat_array = new JSONArray();
        // Holds results from query
        ResultSet rs = null;
        // Query assembly (mySQL format)
        String query = "SELECT * FROM patient;";
        try {
            // Attempt to query
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            // Add data to the JSON array
            while (rs.next()) {
                JSONObject jo = new JSONObject();
                jo.put("id", rs.getInt("id"));
                jo.put("name", rs.getString("name"));
                jo.put("phone", rs.getString("phone"));
                jo.put("sex", rs.getInt("sex"));
                pat_array.put(jo);
            }
            // Place all of the data into the JSON object
            pat_record.put("patients", pat_array);
            stmt.close();

        } catch (SQLException e) {
            pat_record = null;
            e.printStackTrace();
        } finally {
            // Close the connection to avoid memory leaks
            con.close();
        }
        return pat_record;
    }
    
    /**
     * Fetch all info related to a patient
     * @param pat_id The Patients ID number
     * @return A JSONObject containing the following parameters:
     *  name: The patient's name
     *  phone: The patient's phone number (as a string)
     *  sex: The patients sex: 0 for female, 1 for male
     */
    public JSONObject getPatient(int pat_id) {
        // Initialize Connection
        Connection con = ConnectionManager.getConnection();
        // Holds results from query
        Statement stmt = null;
        // JSON representation of the schedule
        JSONObject results = new JSONObject();
        // Holds results from query
        ResultSet rs = null;
        // Query assembly (mySQL format)
        String query = "SELECT p.name, p.sex, p.phone "
                + "FROM patient p "
                + "WHERE p.id = " + pat_id + ";";
        try {
            // Attempt the query
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            // Add the results to the json object parameters
            if (rs.next()) {
                results.put("name", rs.getString("name"));
                results.put("sex", rs.getString("sex"));
                results.put("phone", rs.getString("phone"));
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
