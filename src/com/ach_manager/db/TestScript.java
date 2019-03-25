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
            test_json.put("getAllDoctors", doc_man.getAllDoctors());
            test_json.put("getAllDoctorsInDep", doc_man.getAllDoctorsInDep(2));
            test_json.put("checkCredentials (Valid)", cred_man.checkCredentials("AndyPants", "pass1"));
            test_json.put("checkCredentials (Admin)", cred_man.checkCredentials("admin_01", "a_good_password"));
            test_json.put("checkCredentials (Invalid)", cred_man.checkCredentials("FakeyMcFakerson", "lolno"));
            test_json.put("addAppointment (Valid)", app_man.addAppointment("Test_app", "Test Appointment", "2019-03-10 09:30:00.0", 30, 1, 1));
            test_json.put("addAppointment (Redundant)", app_man.addAppointment("Test_app", "Test Appointment", "2019-03-10 09:30:00.0", 30, 1, 1));
            test_json.put("dropAppointment (Valid)", app_man.dropAppointment(1, 1, "2019-03-10 09:30:00.0"));
            test_json.put("dropAppointment (Missing)", app_man.dropAppointment(1, 1, "2019-03-10 09:30:00.0"));
            int cred_val = cred_man.addCredentials("Test", "Test_Pass", "113-527-9983", "Test Credential");
            test_json.put("addCredential (Valid)", cred_val);
            test_json.put("dropCredential (Valid)", cred_man.dropCredentials(cred_val));
        } catch (SQLException e) {
            test_json = null; 
            e.printStackTrace();
        }
        return test_json.toString();
    }
}
