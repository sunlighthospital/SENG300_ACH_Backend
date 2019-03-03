package com.ach_manager.db;

import java.sql.*;
import org.json.*;

public class DoctorManager {	
	// Gets all doctors in the system
	// Returns:
	//	JSON containing a list of all doctors registered in the system
	//	Contains "id", "name", "is_surgeon", and "department"
	//  Note: null indicates error
	public static JSONObject getAllDoctors() throws SQLException {
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
		String query = "SELECT doctor.id,doctor.name,doctor.is_surgeon,department.name AS dep_name "
					 + "FROM doctor LEFT JOIN (department) "
					 + "ON (doctor.dep_id = department.id);";
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
			data.put("schedule", data_array);
			stmt.close();
			
		} catch (SQLException e) {
			data = null;
			System.out.println(e);
		}
		return data;
	}

	// Gets all doctors in the system in a given department by the latter's id number
	// Returns:
	//	JSON containing a list of all doctors registered in the system in the department
	//	Contains "id", "name", "is_surgeon", and "department"
	//  Note: null indicates error
	public static JSONObject getAllDoctorsInDepByID(int dep_id) throws SQLException {
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
		String query = "SELECT doctor.id,doctor.name,doctor.is_surgeon "
					 + "FROM doctor LEFT JOIN (department) "
					 + "ON (doctor.dep_id = department.id) "
					 + "WHERE dep_id = " + dep_id + ";";
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
