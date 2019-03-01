package com.ach_manager.db;

import java.sql.*;

public class DoctorManager {	
	// Gets all doctors in the system
		// Returns:
		//	A string of newline delineated values representing doctors in the DB
		//	Each value is separated by a tab character '\t'
		//	String data in the following order
		//		id -> name -> is_surgeon
		//	Null on Failure
	public static String getAllDoctors() throws SQLException {
		// Initialize Connection
		Connection con = ConnectionManager.getConnection();
		// Holds results from query
		Statement stmt = null;
		// String representation of the schedule
		String data = "";
		// Holds results from query
		ResultSet rs = null;
		// Query assembly (mySQL format)
		String query = "SELECT doctor.id,doctor.name,doctor.is_surgeon,department.name AS dep_name "
					 + "FROM doctor LEFT JOIN (department) "
					 + "ON (doctor.dep_id = department.id);";
		try {
			// Attempt the query
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			// While appointments remain, add their data to the string
			while (rs.next()) {
				// Pull data from each result
				int id = rs.getInt("id");
				String name = rs.getString("name");
				boolean is_surgeon = rs.getBoolean("is_surgeon");
				String dep_name = rs.getString("dep_name");
				// Add it to the string
				System.out.println(id + "\t" + name + "\t" + is_surgeon + "\t" + dep_name);
				data = data + id + "\t" + name + "\t" + is_surgeon + "\t" + dep_name + "\n";
			}
			stmt.close();
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return data;
	}

	// Gets all doctors in the system
	// Returns:
	//	A string of newline delineated values representing doctors in the given department
	//	Each value is separated by a tab character '\t'
	//	String data in the following order
	//		id -> name -> is_surgeon -> department name
	//	Null on Failure
	public static String getAllDoctorsInDepByID(int dep_id) throws SQLException {
		// Initialize Connection
		Connection con = ConnectionManager.getConnection();
		// Holds results from query
		Statement stmt = null;
		// String representation of the schedule
		String data = "";
		// Holds results from query
		ResultSet rs = null;
		// Query assembly (mySQL format)
		String query = "SELECT doctor.id,doctor.name,doctor.is_surgeon "
					 + "FROM doctor LEFT JOIN (department) "
					 + "ON (doctor.dep_id = department.id) "
					 + "WHERE dep_id = " + dep_id + ";";
		try {
			// Attempt the query
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			// While appointments remain, add their data to the string
			while (rs.next()) {
				// Pull data from each result
				int id = rs.getInt("id");
				String name = rs.getString("name");
				boolean is_surgeon = rs.getBoolean("is_surgeon");
				// Add it to the string
				System.out.println(id + "\t" + name + "\t" + is_surgeon + "\n");
				data = data + id + "\t" + name + "\t" + is_surgeon + "\n";
			}
			stmt.close();
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return data;
	}
}
