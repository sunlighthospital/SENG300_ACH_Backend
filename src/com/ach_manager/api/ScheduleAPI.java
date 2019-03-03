package com.ach_manager.api;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.json.*;

import com.ach_manager.db.*;

@Path("/schedule")
public class ScheduleAPI {
	// Converts input date string into usable datetime format
	private Date convertDateString(String val) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(val);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	@Path("/{dr_id}/{date_string}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	// Pulls the next weeks worth of appointments for the doctor specified
	// Returns null if an error occurred
	public String getFullScheduleSet(@PathParam("dr_id") int id, @PathParam("date_string") String date_string){
		JSONObject fin_schedule = new JSONObject();
		try {
			// Pull relevant data
			JSONObject init_schedule = AppointmentManager.getDocAppointmentsByDocID(id);
			JSONArray init_ja = (JSONArray) init_schedule.get("schedule");
			// Prepare storage variable for appointments
			JSONArray fin_ja = new JSONArray();
			// Set up loop managing variables
			int i = 0;
			Date cur_date = convertDateString(init_ja.getJSONObject(i).get("time").toString());
			Date end_date = convertDateString(date_string);
			// Keep adding appointments as long as appointments remain to be tested and they are before the cut-off
			while ( (cur_date.compareTo(end_date) < 0) && (i < init_ja.length()) ) {
				fin_ja.put(cur_date);
				i++;
				if (i < init_ja.length()) {
					cur_date = convertDateString(init_ja.getJSONObject(i).get("time").toString());
				}
			}
			// Finalize the JSON object
			fin_schedule.put("schedule", fin_ja);
		} catch (SQLException e) {
			fin_schedule = null;
			e.printStackTrace();
		}
		return fin_schedule.toString();
	}
}
