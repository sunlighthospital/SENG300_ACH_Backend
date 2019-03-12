package com.ach_manager.db;

import java.sql.*;
import java.util.Calendar;
import java.util.TimeZone;

import org.json.*;

public class AppointmentManager {
    // Variable instantiation
    private final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("MST"));
    
    // Gets all appointments associated with a doctor, given their id number
    // Returns:
    //  JSON containing a list of all appointments for the doctor
    //  Titled "schedule"
    //  Each value contains the following:
    //      String "title"
    //      String "description"
    //      String "time" (yyyy-MM-dd)
    //      Int "duration" (in minutes)
    //  Note: Duration of 0 indicates a full day event, null indicates error
    public JSONObject getDocAppointmentsByDocID(int id) throws SQLException {
        // Initialize Connection
        Connection con = ConnectionManager.getConnection();
        // Holds results from query
        Statement stmt = null;
        // JSON representation of the schedule
        JSONObject schedule = new JSONObject();
        JSONArray sched_array = new JSONArray();
        // Holds results from query
        ResultSet rs = null;
        // Query assembly (mySQL format)
        String query = "SELECT * FROM appointment WHERE doctor_id = \'" + id + "\';";
        try {
            // Attempt to query
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            // Add data to the JSON array
            while (rs.next()) {
                JSONObject jo = new JSONObject();
                jo.put("title", rs.getString("title"));
                jo.put("description", rs.getString("desc"));
                jo.put("time", rs.getTimestamp("time").toString());
                jo.put("duration", rs.getInt("duration"));
                sched_array.put(jo);
            }
            // Place all of the data into the JSON object
            schedule.put("schedule", sched_array);
            stmt.close();

        } catch (SQLException e) {
            schedule = null;
            e.printStackTrace();
        }
        return schedule;
    }

    // Gets all appointments associated with a doctor, given their name
    // Returns:
    //	JSON containing a list of all appointments for the doctor
    //	Titled "schedule"
    //	Each value contains the following:
    //		String "title"
    //		String "description"
    //		String "time"
    //		Int "duration" (in minutes)
    //  Note: Duration of 0 indicates a full day event, null indicates error
    public JSONObject getDocAppointmentsByName(String name) throws SQLException {
            // Initialize Connection
            Connection con = ConnectionManager.getConnection();
            // Statement of Intent
            Statement stmt = null;
            // String representation of the schedule
            JSONObject schedule = null;
            // Query to submit to the database
            String query = "SELECT `id` FROM doctor WHERE name = \'" + name + "\';";
            try {
                    // ID identifier
                    stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    rs.next();
                    int doc_id = rs.getInt("id");
                    // Run the id-specific script defined above
                    schedule = getDocAppointmentsByDocID(doc_id);
                    stmt.close();
            } catch (SQLException e) {
                    schedule = null;
                    System.out.println(e);
            }
            return schedule;
    }

    // Test script, does not actually do anything in the server
    public void test(String args[]) throws SQLException {
        getDocAppointmentsByName("Bob B. Bobbin");
    }
}
