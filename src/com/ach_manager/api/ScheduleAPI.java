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

// Path by which to access this classes api
@Path("/schedule")
public class ScheduleAPI {
    // Wrapper calls
    AppointmentManager app_man = new AppointmentManager();
    
    // ----- PRIVATE FUNCTIONS; INACCESSABLE BY THE URL -----
    // Converts input date string into usable datetime format
    private Date convertDateString(String val) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(val);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @Path("/bounded")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    // Pulls the next weeks worth of appointments for the doctor specified
    // Returns null if an error occurred
    public String getFullSchedule(
            @QueryParam("id") int id, 
            @QueryParam("start") String start_string,
            @QueryParam("end") String end_string
    ) {
        JSONObject fin_schedule = new JSONObject();
        try {
            // Pull relevant data
            JSONObject init_schedule = app_man.getDocAppointmentsByDocID(id);
            JSONArray init_ja = (JSONArray) init_schedule.get("schedule");
            // Prepare storage variable for appointments
            JSONArray fin_ja = new JSONArray();
            // Set up loop managing variables
            int i = 0;
            Date cur_date = convertDateString(init_ja.getJSONObject(i).get("time").toString());
            Date start_date = convertDateString(start_string);
            Date end_date = convertDateString(end_string);
            // Keep adding appointments as long as appointments remain to be tested
            while ( (i < init_ja.length()) ) {
                cur_date = convertDateString(init_ja.getJSONObject(i).get("time").toString());
                i++;
                // Check to make sure the dates are in the appropriate bounds
                if ( (cur_date.compareTo(start_date) >= 0) 
                        && (cur_date.compareTo(end_date) < 0) ) {
                    fin_ja.put(cur_date);
                }
            }
            // Finalize the JSON object
            fin_schedule.put("schedule", fin_ja);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fin_schedule.toString();
    }
}
