/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ach_manager.api;

import com.ach_manager.db.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;

/**
 *
 * @author Atika
 */
@Path("/editUser")
public class EditUser{

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("/addDoctor")
    // Returns:
    //	JSONObject containing a message to be displayed on screen after adding a new doctor 
    public String addDoctor(
            @QueryParam("username") String user,       
            @QueryParam("password") String password,  
            @QueryParam("phone") String phone,        
            @QueryParam("name") String name,                // Name of user
            @QueryParam("departmentID") int dep_id,
            @QueryParam("isSurgeon") boolean isSurgeon){          
        
        try {
             JSONObject result = new JSONObject();
             CredentialManager userManager = new CredentialManager();
             int cred_id = userManager.addCredentials(user,password,phone,name);
             DoctorManager doctorManager = new DoctorManager();
             Utils.ProgramCode status = doctorManager.addDoctor(cred_id, isSurgeon, dep_id);
             if(status == Utils.ProgramCode.SUCCESS){
                result.put("Message", "Doctor was successfully added");
            }
             else if(status == Utils.ProgramCode.UNKNOWN_ERROR){ 
                result.put("Message", "An unknown error occured");
            }
             else if(status == Utils.ProgramCode.DUPLICATE_ENTRY){ 
                result.put("Message", "A doctor with the entered information already exists");
            }
            return result.toString();
        }

        catch (Exception e){
            JSONObject result = new JSONObject();
            result.put("Message", "An  error occured");
            return result.toString();
        }

    }
/**
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("/removeDoctor")
    public String removeDoctor(
            @QueryParam("username") String user,       
            @QueryParam("password") String password){
        
         try {
             JSONObject result = new JSONObject();
             CredentialManager userManager = new CredentialManager();
             JSONObject cred_id = userManager.checkCredentials(user,password);
             if(cred_id.has("name"))
             {
                 Utils.ProgramCode status = userManager.dropCredentials(cred_id);
                 
                 
             }
         }
             
        catch (Exception e){
            JSONObject result = new JSONObject();
            result.put("Message", "An  error occured");
            return result.toString();
        }  
         
    }
    **/

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("/addReceptionist")
    // Returns:
    //	JSONObject containing a message to be displayed on screen after adding a new doctor 
    public String addReceptionist(
            @QueryParam("username") String user,       
            @QueryParam("password") String password,  
            @QueryParam("phone") String phone,        
            @QueryParam("name") String name){          
        
        try {
             JSONObject result = new JSONObject();
             CredentialManager userManager = new CredentialManager();
             int cred_id = userManager.addCredentials(user,password,phone,name);
             ReceptionistManager recepManager = new ReceptionistManager();
             Utils.ProgramCode status = recepManager.addReceptionist(cred_id, "");
             if(status == Utils.ProgramCode.SUCCESS){
                result.put("Message", "Receptionist was successfully added");
            }
             else if(status == Utils.ProgramCode.UNKNOWN_ERROR){ 
                result.put("Message", "An unknown error occured");
            }
             else if(status == Utils.ProgramCode.DUPLICATE_ENTRY){ 
                result.put("Message", "A receptionist with the entered information already exists");
            }
            return result.toString();
        }

        catch (Exception e){
            JSONObject result = new JSONObject();
            result.put("Message", "An  error occured");
            return result.toString();
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllReceptionists")
    /**
     * Fetches all receptionists contained within the database
     * @return A JSON object containing details about all receptionists in the system (in the "receps" tag)
     *  Each entry contains the following elements:
     *      id: The receptionists credential id
     *      role: The receptionists role
     *  Returns message on error
     */
    public String getAllRecep(){
        
        try {
            ReceptionistManager recepMan = new ReceptionistManager();
            JSONObject result = new JSONObject();
            result = recepMan.getAllReceptionists();
            return result.toString();
            }

        catch (Exception e){
            JSONObject result = new JSONObject();
            result.put("Message", "An  error occured");
            return result.toString();
        }
    }
}
