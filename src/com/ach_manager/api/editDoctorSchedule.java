/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ach_manager.api;

import com.ach_manager.db.*;
import com.ach_manager.db.Utils.ProgramCode;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;

/**
 *
 * @author Atika
 */

// This API handles updating doctor's schedule, which involves adding or dropping appointments

// Path by which to access this API for editing doctor's schedule
@Path("/editSchedule")
public class editDoctorSchedule{
    AppointmentManager apptManager = new AppointmentManager();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    // Following path is for adding new appoinitments to doctor's schedule
    @Path("/add")
    // Returns:
    //	JSONObject containing a message to be displayed on screen after adding a new appointment 
    public JSONObject addAppt(
            @QueryParam("title") String Title,              // Title of appointment
            @QueryParam("description") String description,  // Description of appointment
            @QueryParam("time") String time,                // Time of appointment
            @QueryParam("duration") int duration,           // duration of appointment
            @QueryParam("patientID")int patientID,         // Patient's ID
            @QueryParam("doctorID") int doc_id){           // Doctor's ID
        
        try {
            JSONObject result = new JSONObject();
            // Status contains result of adding a new appointment to the database
            Utils.ProgramCode status = apptManager.addAppointment(Title, description,time,duration,patientID,doc_id);
            if(status == ProgramCode.SUCCESS){
                result.put("Message", "Appointment was successfully added");
            }
            else if(status == ProgramCode.UNKNOWN_ERROR){ 
                result.put("Message", "c");
            }
            else if(status == ProgramCode.DUPLICATE_ENTRY){ 
                result.put("Message", "An appointment with the entered information already exists");
            }
            return result;
        }
        catch (Exception e){
        }
        return null;
    }
    
    // Following path is for dropping appointment and subsequently updating the database
    @Path("/drop")
    // Returns:
    //	JSONObject containing a message to be displayed on screen after dropping appointment 
    public JSONObject dropAppt(
             @QueryParam("patientID") int patientID,       // ID of patient dropping the appointment
             @QueryParam("doctorID") int docID,            // Doctor's ID 
             @QueryParam("time") String time){              // Time of appointment
               
        try {
            JSONObject result = new JSONObject();
            // Status contains result of dropping the appointment and updating the database
            Utils.ProgramCode status = apptManager.dropAppointment(patientID, docID,time);
            if(status == ProgramCode.SUCCESS){
                result.put("Message", "Appointment was successfully dropped");
            }
            else if(status == ProgramCode.UNKNOWN_ERROR){ 
                result.put("Message", "An unknown error ocurred during dropping appointment");
            }
            else if(status == ProgramCode.NO_ENTRY_FOUND){ 
                result.put("Message", "An appointment with the entered information does not exist");
            }
            return result;
        }
        catch (Exception e){
        }
        return null;
        
    }
}
