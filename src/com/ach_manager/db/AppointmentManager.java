package com.ach_manager.db;

import com.ach_manager.db.Utils.ProgramCode;

import java.sql.*;

import org.json.*;

public class AppointmentManager {
    /**
     * Adds an appointment to the 
     * @param id Doctor ID to search for the appointments under
     * @return A JSONObject containing a JSONObjectList "schedule", with elements containing the following parameters:
     *  title: Title of the appointment
     *  description: Description of the appointment
     *  time: Time the appointment is schedule to take place
     *  duration: Expected duration of the appointment (in minutes)
     * @throws java.sql.SQLException
    **/
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
        String query = "SELECT * FROM appointment WHERE doc_id = \'" + id + "\';";
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
        } finally {
            // Close the connection to avoid memory leaks
            con.close();
        }
        return schedule;
    }

    /**
     * Gets all appointments associated with a doctor's name
     * Defaults to the first entry if doctor's with identical names exist
     * @param name The name of the doctor to search for
     * @return A JSONObject containing a JSONObjectList "schedule":
     *  Each element contains the following parameters:
     *      title: Title of the appointment
     *      description: Description of the appointment
     *      time: Time the appointment is schedule to take place
     *      duration: Expected duration of the appointment (in minutes)
     *      Returns null on error
     * @throws java.sql.SQLException
    **/
    public JSONObject getDocAppointmentsByDocName(String name) throws SQLException {
            // Initialize Connection
            Connection con = ConnectionManager.getConnection();
            // Statement of Intent
            Statement stmt = null;
            // String representation of the schedule
            JSONObject schedule = null;
            // Query to submit to the database
            String query = "SELECT `id` FROM credential WHERE name = \'" + name + "\';";
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
            finally {
                // Close the connection to avoid memory leaks
                con.close();
            }
            return schedule;
    }

    /**
     * Adds an appointment to the 
     * @param title Title to give the appointment 
     * @param description Description of the appointment (why its being done)
     * @param time Time the appointment starts (yyyy-MM-dd hh:mm:ss)
     * @param duration Expected duration (in minutes) for the appointment
     * @param pat_id The Patient's id number
     * @param doc_id The Doctor's id number
     * @return The ProgramCode which indicates the result of the execution
     * @see com.ach_manager.db.Utils
    **/
    public ProgramCode addAppointment(String title, String description, String time, int duration, int pat_id, int doc_id) {
        // Initialize Connection
        Connection con = ConnectionManager.getConnection();
        // Statement of Intent
        Statement stmt = null;
        // String representation of the schedule
        ProgramCode code = null;
        // Query to submit to the database
        String query = "INSERT INTO "
                + "`appointment`(`title`,`desc`,`time`,`duration`,`doc_id`,`pat_id`) "
                + "VALUES ('" + title + "','" + description + "','"
                + time + "'," + duration + "," + doc_id + "," + pat_id + ");";
        try {
            // ID identifier
            stmt = con.createStatement();
            int vals = stmt.executeUpdate(query);
            if (vals == 1) {
                code = ProgramCode.SUCCESS;
            } 
            // If it returns a non-1 value, its erronous, and needs to be addressed
            else {
                code = ProgramCode.UNKNOWN_ERROR;
                System.out.println("ERROR; values effect greater than 1 (" + vals + ")");
            }
            stmt.close();
            con.close();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("");
            return ProgramCode.DUPLICATE_ENTRY;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return code;
    }
    
    /**
     * Removes an appointment from the database
     * @param time Time the appointment starts (yyyy-MM-dd hh:mm:ss)
     * @param pat_id The Patient's id number
     * @param doc_id The Doctor's id number
     * @return The ProgramCode which indicates the result of the execution
    **/
    public ProgramCode dropAppointment(int pat_id, int doc_id, String time) {
        // Initialize Connection
        Connection con = ConnectionManager.getConnection();
        // Statement of Intent
        Statement stmt = null;
        // String representation of the schedule
        ProgramCode code = ProgramCode.UNKNOWN_ERROR;
        // Query to submit to the database
        String query = "DELETE FROM appointment WHERE "
                + "time = '" + time + "' AND "
                + "doc_id = " + doc_id + " AND "
                + "pat_id = " + pat_id + ";";
        try {
            // ID identifier
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
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return code;
    }
}
