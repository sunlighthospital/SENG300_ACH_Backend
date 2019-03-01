package com.ach_manager.db;
import java.sql.*;

public class AppointmentManager {
	// Current database information
	private static final String db_url = "jdbc:mysql://localhost/SENG300_Hospital";
	 
    private static final String db_user = "client";
 
    private static final String db_password = "password";
	
	// Establish connection to the intended database
	private static Connection establishConnection(String url, String user, String pass) throws SQLException{
		Connection con = null;
		
		// Attempt Connection
		System.out.println("Attempting connection...");
		try {
			con = DriverManager.getConnection(url, user, pass);
			System.out.println("Connected Successfully!");
		}
		catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
		
		return con;
	}
	
	// Find all appointments associated with a given doctor's name
	public static String getDocAppointmentsByName(String name) throws SQLException {
		// Initialize Connection
		Connection con = establishConnection(db_url, db_user, db_password);
		// Holds results from query
		Statement stmt = null;
		// String representation of the schedule
		String schedule = null;
		
		String query = "SELECT `id` FROM doctor WHERE name = \'" + name + "\';";
		try {
			// ID identifier
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			int doc_id = rs.getInt("id");
			
			// Appointment Fetching + Formatting
			query = "SELECT `title`,`desc`,`time`,`duration` FROM appointment WHERE doctor_id = \'" + doc_id + "\';";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				String title = rs.getString("title");
				String desc = rs.getString("desc");
				Timestamp timestamp = rs.getTimestamp("time");
				String datetime = timestamp.toString();
				int duration = rs.getInt("duration");
				System.out.println(title + "\t" + desc + "\t" + datetime + "\t" + duration );
				schedule = schedule + "\n" + title + "\t" + desc + "\t" + datetime + "\t" + duration;
			}
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
