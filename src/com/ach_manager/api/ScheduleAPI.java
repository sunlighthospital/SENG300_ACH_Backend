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

    /**
     * Gets the schedule for the doctor, bounded by the start and end times
     * @param id The doctor's ID
     * @param start_string Start date (as a String in "yyyy-MM-dd hh:mm:ss" format)
     * @param end_string End date (as a String in "yyyy-MM-dd hh:mm:ss" format)
     * @return A JSONObject containing a JSONObjectList "schedule", with elements containing the following parameters:
     *  title: Title of the appointment
     *  description: Description of the appointment
     *  time: Time the appointment is schedule to take place
     *  duration: Expected duration of the appointment (in minutes)
     */
    @Path("/bounded")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
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
                // Check to make sure the dates are in the appropriate bounds
                if ( (cur_date.compareTo(start_date) >= 0) 
                        && (cur_date.compareTo(end_date) < 0) ) {
                    JSONObject jo = new JSONObject();
                    jo.put("title", init_ja.getJSONObject(i).get("title"));
                    jo.put("description", init_ja.getJSONObject(i).get("description"));
                    jo.put("time", init_ja.getJSONObject(i).get("time"));
                    jo.put("duration", init_ja.getJSONObject(i).get("duration"));
                    jo.put("patient_id", init_ja.getJSONObject(i).get("patient_id"));
                    fin_ja.put(jo);
                }
                i++;
            }
            // Finalize the JSON object
            fin_schedule.put("schedule", fin_ja);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fin_schedule.toString();
    }
}
