/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ach_manager.api;

import com.ach_manager.db.DoctorManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
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
    public JSONObject getAll() {
        try {
            JSONObject jo = doc_man.getAllDoctors();
            return jo;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
