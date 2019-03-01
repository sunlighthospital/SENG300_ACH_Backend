package com.ach_manager.db;
import java.sql.*;

public class AppointmentManager {
	// Gets all appointments associated with a doctor, given their id number
	// Returns:
	//	A string of newline delineated values representing appoints
	//	Each value is separated by a tab character '\t'
	//	String data in the following order
	//		title -> description -> date time -> duration
	//	Null on Failure
	public static String getDocAppointmentsByDocID(int id) throws SQLException {
		// Initialize Connection
		Connection con = ConnectionManager.getConnection();
		// Holds results from query
		Statement stmt = null;
		// String representation of the schedule
		String schedule = "";
		// Holds results from query
		ResultSet rs = null;
		// Query assembly (mySQL format)
		String query = "SELECT * FROM appointment WHERE doctor_id = \'" + id + "\';";
		try {
			// Attempt to query
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			// While appointments remain, add their data to the string
			while (rs.next()) {
				String title = rs.getString("title");
				String desc = rs.getString("desc");
				Timestamp timestamp = rs.getTimestamp("time");
				String datetime = timestamp.toString();
				int duration = rs.getInt("duration");
				System.out.println(title + "\t" + desc + "\t" + datetime + "\t" + duration );
				schedule = schedule + title + "\t" + desc + "\t" + datetime + "\t" + duration + "\n";
			}
			stmt.close();
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return schedule;
	}
	
	// Find all appointments associated with a given doctor's name
	// Returns:
	//	A string of newline delineated values representing appoints
	//	Each value is separated by a tab character '\t'
	//	String data in the following order
	//		title -> description -> date time -> duration
	// 	Null on Failure
	public static String getDocAppointmentsByName(String name) throws SQLException {
		// Initialize Connection
		Connection con = ConnectionManager.getConnection();
		// Statement of Intent
		Statement stmt = null;
		// String representation of the schedule
		String schedule = "";
		// Query to submit to the database
		String query = "SELECT `id` FROM doctor WHERE name = \'" + name + "\';";
		try {
			// ID identifier
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			int doc_id = rs.getInt("id");
			
			schedule = getDocAppointmentsByDocID(doc_id);
			stmt.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return schedule;
	}
	
    public static void main(String args[]) throws SQLException {
        getDocAppointmentsByName("Bob B. Bobbin");
    }
}
