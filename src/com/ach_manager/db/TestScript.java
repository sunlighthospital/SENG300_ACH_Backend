package com.ach_manager.db;

import java.sql.SQLException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.json.*;

@Path("/test")
public class TestScript {
    // Variable instantiation
    private final AppointmentManager app_man = new AppointmentManager();
    private final DoctorManager doc_man = new DoctorManager();
    private final CredentialManager cred_man = new CredentialManager();
    
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public String test(){
        JSONObject test_json = new JSONObject();
        try {
            test_json.put("Val1", app_man.getDocAppointmentsByDocID(4));
            test_json.put("Val2", doc_man.getAllDoctorsInDepByID(2));
            test_json.put("Val3", doc_man.getAllDoctors());
            test_json.put("Val4", cred_man.checkCredentials("AndyPants", "pass1"));
            test_json.put("Val5", cred_man.checkCredentials("FakeyMcFakerson", "lolno"));
        } catch (SQLException e) {
            test_json = null; 
            e.printStackTrace();
        }
        return test_json.toString();
    }
}
