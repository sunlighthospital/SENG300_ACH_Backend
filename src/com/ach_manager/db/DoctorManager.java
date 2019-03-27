package com.ach_manager.db;

import com.ach_manager.db.Utils.ProgramCode;
import java.sql.*;
import org.json.*;

public class DoctorManager {	
    /**
     * Fetches all doctors contained within the database
     * @return A JSON object containing details about all doctors in the system (in the "doctors" tag)
     *  Each entry contains the following elements:
     *      id: The doctor's ID number
     *      name: The doctor's name
     *      is_surgeon: Whether the doctor is a surgeon or not
     *      department: Department the doctor is associated with
     *  Returns null on error
     * @throws SQLException 
     */
    public JSONObject getAllDoctors() throws SQLException {
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
        String query = "SELECT c.id, c.name, d.is_surgeon, p.name AS dep_name "
                                 + "FROM doctor d "
                                 + "INNER JOIN credential c ON d.cred_id = c.id "
                                 + "INNER JOIN department p ON d.dep_id = p.id;";
        try {
            // Attempt the query
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            // While doctors remain, add them to the object
            while (rs.next()) {
                JSONObject jo = new JSONObject();
                jo.put("id", rs.getInt("id"));
                jo.put("name", rs.getString("name"));
                jo.put("is_surgeon", rs.getBoolean("is_surgeon"));
                jo.put("department", rs.getString("dep_name"));
                data_array.put(jo);
            }
            // Place all of the data into the JSON object
            data.put("doctors", data_array);
            stmt.close();
        } catch (SQLException e) {
            data = null;
            System.out.println(e);
        }
        return data;
    }

    /**
     * 
     * @param dep_id Department in which to look within
     * @return A JSON object containing details about all doctors in the specified department (in the "doctors" tag)
     *  Each entry contains the following elements:
     *      id: The doctor's ID number
     *      name: The doctor's name
     *      is_surgeon: Whether the doctor is a surgeon or not
     *  Returns null on error
     * @throws SQLException 
     */
    public JSONObject getAllDoctorsInDep(int dep_id) throws SQLException {
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
        String query = "SELECT c.id, c.name, d.is_surgeon "
                                 + "FROM doctor d "
                                 + "INNER JOIN credential c ON d.cred_id = c.id "
                                 + "INNER JOIN department p ON d.dep_id = p.id "
                                 + "WHERE p.id = " + dep_id + ";";
        try {
            // Attempt the query
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                JSONObject jo = new JSONObject();
                jo.put("id", rs.getInt("id"));
                jo.put("name", rs.getString("name"));
                jo.put("is_surgeon", rs.getBoolean("is_surgeon"));
                //jo.put("department", rs.getString("dep_name"));
                data_array.put(jo);
            }
            // Place all of the data into the JSON object
            data.put("schedule", data_array);
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e);
        }

        return data;
    }
    
    /**
     * Update a person's credentials to give them doctor privileges w/ a given department
     * @param cred_id ID# of the person to add privileges too
     * @param is_surgeon If they should be marked as a surgeon or not
     * @param dep_id What department they should be part of (its ID#)
     * @return The result of the function, as a ProgramCode
     * @see com.ach_manager.db.Utils
     * @throws SQLException 
     */
    public ProgramCode addDoctor(int cred_id, boolean is_surgeon, int dep_id) throws SQLException {
        // Initialize Connection
        Connection con = ConnectionManager.getConnection();
        // Statement of Intent
        Statement stmt = null;
        try {
            // Attempt the query
            stmt = con.createStatement();
            String query = "INSERT INTO `doctor`(`cred_id`,`dep_id`,`is_surgeon`) VALUES "
                    + "('" + cred_id + "','" + dep_id + "','" + is_surgeon + "');";
            int vals = stmt.executeUpdate(query);
            // Based on results, return program code
            if (vals == 1) {
                return ProgramCode.SUCCESS;
            } else if (vals == 0) {
                return ProgramCode.DUPLICATE_ENTRY;
            } else {
                return ProgramCode.UNKNOWN_ERROR;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ProgramCode.UNKNOWN_ERROR;
        } finally {
            stmt.close();
            con.close();
        }
    }
    
    /**
     * Remove a doctor from the database
     * @param doc_id The ID of the doctor to drop (safety checks to make sure the individual is, in fact, a doctor)
     * @return A ProgramCode describing the result of the function code
     * @throws SQLException 
     */
    public ProgramCode dropDoctor(int doc_id) throws SQLException {
        // Setup
        CredentialManager cred_man = new CredentialManager();
        JSONArray arr = getAllDoctors().getJSONArray("doctors");
        int i = 0;
        int id = -1;
        // Make sure the passed credential is a valid doctor id; if so, drop them
        while (id <= doc_id && i < arr.length()) {
            id = arr.getJSONObject(i).getInt("id");
            System.out.println();
            if (id == doc_id) {
                return cred_man.dropCredentials(doc_id);
            }
            i++;
        }
        return ProgramCode.NO_ENTRY_FOUND;
    }
}
