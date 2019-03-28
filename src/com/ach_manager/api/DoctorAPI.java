/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ach_manager.api;

import com.ach_manager.db.AppointmentManager;
import com.ach_manager.db.DoctorManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;

/**
 *
 * @author kalum
 */
@Path("/doctor")
public class DoctorAPI {
    DoctorManager doc_man = new DoctorManager();
    
    // Pulls all doctor data for use by front-end
    // Returns: JSON object containing doctor values
    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() {
        try {
            JSONObject jo = doc_man.getAllDoctors();
            return jo.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new JSONObject().toString();
    }
    
    @Path("/getAppointments")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAppointmentByDocID(
            @QueryParam("id") int id){
        
        try{
            JSONObject result = new JSONObject();
            AppointmentManager apptManager = new AppointmentManager();
            result = apptManager.getDocAppointmentsByDocID(id);
             return result.toString();
        }

        catch(Exception e)
        {
            JSONObject result = new JSONObject();
            result.put("Message","An error occurred");
            return result.toString();
        }
    }
    
}
