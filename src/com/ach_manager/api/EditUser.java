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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/removeUser")
    public String removeUser(
            @QueryParam("id") int cred_id){
        
         try {
             JSONObject result = new JSONObject();
             CredentialManager credManager = new CredentialManager();
            // DoctorManager docMan = new DoctorManager();
             Utils.ProgramCode cred_status = credManager.dropCredentials(cred_id);
             // Utils.ProgramCode doc_status = docMan.dropDoctor(cred_id);
             if(cred_status == Utils.ProgramCode.SUCCESS){
                result.put("Message", "User was successfully removed");
             }
             else if(cred_status == Utils.ProgramCode.UNKNOWN_ERROR){ 
                result.put("Message", "An unknown error occured");
             }
             else if(cred_status == Utils.ProgramCode.NO_ENTRY_FOUND){ 
                result.put("Message", "A user with the entered information does not exist");
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
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllUsers")
    /**
     * Gets all credentials in the system (for admin use only)
     * @return A JSONObject containing a JSONArray of objects (labeled "credentials") w/ the following parameters:
     *  id: The ID number of the credential found
     *  name: Name of the user (non-existent if credentials were not found)
     *  department: Department of the doctor, if they are a doctor
     *  reception_role: Role of the user in reception, if they are a receptionist
     *  admin_role: Role of the user in administration, if they are an administrator
     */
    public String getAllUsers(){
        
        try {
            CredentialManager credMan = new CredentialManager();
            JSONObject result = new JSONObject();
            result = credMan.getAllCredentials();
            return result.toString();
            }

        catch (Exception e){
            JSONObject result = new JSONObject();
            result.put("Message", "An  error occured");
            return result.toString();
        }
    }
}
