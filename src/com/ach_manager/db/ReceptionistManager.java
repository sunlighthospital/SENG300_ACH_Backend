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

public class ReceptionistManager {
    /**
     * Fetches all receptionists contained within the database
     * @return A JSON object containing details about all receptionists in the system (in the "receps" tag)
     *  Each entry contains the following elements:
     *      id: The receptionists credential id
     *      role: The receptionists role
     *  Returns null on error
     * @throws SQLException 
     */
    public JSONObject getAllReceptionists() throws SQLException {
        // Initialize Connection
        Connection con = ConnectionManager.getConnection();
        // Holds results from query
        Statement stmt = null;
        // String representation of the schedule
        JSONObject data = new JSONObject();
        JSONArray data_array = new JSONArray();
        // Holds results from query
        ResultSet rs = null;
        // Query assembly (mySQL format)
        String query = "SELECT r.role, r.cred_id FROM receptionist r;";
        try {
            // Attempt the query
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            // While doctors remain, add them to the object
            while (rs.next()) {
                JSONObject jo = new JSONObject();
                jo.put("id", rs.getInt("cred_id"));
                jo.put("role", rs.getString("role"));
                data_array.put(jo);
            }
            // Place all of the data into the JSON object
            data.put("receps", data_array);
            stmt.close();
        } catch (SQLException e) {
            data = null;
            System.out.println(e);
        }
        return data;
    }
    
    /**
     * Update a person's credentials to give them receptionist privileges
     * @param cred_id Credential id of the user being given the role
     * @param role Receptionist role being given
     * @return The result of the function, as a ProgramCode
     * @see com.ach_manager.db.Utils
     * @throws SQLException 
     */
    public Utils.ProgramCode addReceptionist(int cred_id, String role) throws SQLException {
        // Initialize Connection
        Connection con = ConnectionManager.getConnection();
        // Statement of Intent
        Statement stmt = null;
        try {
            // Attempt the query
            stmt = con.createStatement();
            String query = "INSERT INTO `receptionist`(`cred_id`,`role`) VALUES "
                    + "('" + cred_id + "','" + role + "');";
            int vals = stmt.executeUpdate(query);
            // Based on query results, return program code
            if (vals == 1) {
                return Utils.ProgramCode.SUCCESS;
            } else if (vals == 0) {
                return Utils.ProgramCode.DUPLICATE_ENTRY;
            } else {
                return Utils.ProgramCode.UNKNOWN_ERROR;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Utils.ProgramCode.UNKNOWN_ERROR;
        } finally {
            stmt.close();
            con.close();
        }
    }
    
    /**
     * Remove a receptionist from the database
     * @param rec_id The ID of the receptionist to drop (safety checks to make sure the individual is, in fact, a receptionist)
     * @return A ProgramCode describing the result of the function code
     * @throws SQLException 
     */
    public Utils.ProgramCode dropReceptionist(int rec_id) throws SQLException {
        // Setup
        CredentialManager cred_man = new CredentialManager();
        JSONArray arr = getAllReceptionists().getJSONArray("receps");
        int i = 0;
        int id = -1;
        // Make sure the passed credential is a valid receptionist id; if so, drop them
        while (id <= rec_id && i < arr.length()) {
            id = arr.getJSONObject(i).getInt("id");
            System.out.println();
            if (id == rec_id) {
                return cred_man.dropCredentials(rec_id);
            }
            i++;
        }
        return Utils.ProgramCode.NO_ENTRY_FOUND;
    }
}
