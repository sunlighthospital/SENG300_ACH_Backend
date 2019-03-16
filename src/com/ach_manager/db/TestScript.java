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
            test_json.put("getAppointments", app_man.getDocAppointmentsByDocID(4));
            test_json.put("getAllDoctors (Full)", doc_man.getAllDoctors());
            test_json.put("getAllDoctors (Department)", doc_man.getAllDoctorsInDepByID(2));
            test_json.put("checkCredentials (Valid)", cred_man.checkCredentials("AndyPants", "pass1"));
            test_json.put("checkCredentials (Invalid)", cred_man.checkCredentials("FakeyMcFakerson", "lolno"));
            test_json.put("addAppointment (Valid)", app_man.addAppointment("Test_app", "Test Appointment", "2019-03-10 09:30:00.0", 30, 1, 1));
            test_json.put("addAppointment (Invalid)", app_man.addAppointment("Test_app", "Test Appointment", "2019-03-10 09:30:00.0", 30, 1, 1));
            test_json.put("dropAppointment (Valid)", app_man.dropAppointment(1, 1, "2019-03-10 09:30:00.0"));
            test_json.put("dropAppointment (Invalid)", app_man.dropAppointment(1, 1, "2019-03-10 09:30:00.0"));
        } catch (SQLException e) {
            test_json = null; 
            e.printStackTrace();
        }
        return test_json.toString();
    }
}
