/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ach_manager.api;

/**
 *
 * @author Atika
 */
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.json.*;

import com.ach_manager.db.*;

// Path for access to user login verification api
@Path("/loginUser")
public class LoginAPI {
    CredentialManager loginManager = new CredentialManager();
    
    // Input parameters: username and password
    // Checks if login is valid by verifying username and password
    // Returns: boolean (true for valid login and false otherwise)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject verifyLogin(
        @QueryParam("username") String username, 
        @QueryParam("password") String password){
    
        try {
            JSONObject result = new JSONObject(); 
            // CheckCredentials checks whether username and password exist in database
            JSONObject user_details = loginManager.checkCredentials(username, password);
            //if user exists then login valid/true
            if(user_details.isNull("name")){  
                result.put("loginValid", false);
                return result; 
            }
            else if(user_details.has("admin_role")){  
                result.put("loginValid", true);
                result.put("role", "Admin");
                return result; 
            }
            else if(user_details.has("reception_role")){  
                result.put("loginValid", true);
                result.put("role", "Receptionist");
                return result; 
            } 
        }
        catch (Exception e){
        }
        return null;
    }  
}
