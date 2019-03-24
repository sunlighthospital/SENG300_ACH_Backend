package com.ach_manager.db;

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
}
