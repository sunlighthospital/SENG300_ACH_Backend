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
    private final PatientManager pat_man = new PatientManager();
    private final ReceptionistManager rec_man = new ReceptionistManager();
    
    /**
     * Test all (sic) wrapper functionality
     * @return A string with test results
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public String test(){
        JSONObject test_json = new JSONObject();
        try {
            // View all credentials in the system
            test_json.put("ALL", cred_man.getAllCredentials());
            // Doctors
            test_json.put("getAppointments", app_man.getDocAppointmentsByDocID(4));
            test_json.put("getAllDoctors", doc_man.getAllDoctors());
            test_json.put("getAllDoctorsInDep", doc_man.getAllDoctorsInDep(2));
            test_json.put("dropDoctor (Invalid)", doc_man.dropDoctor(20));
            // Credential checks
            test_json.put("checkCredentials (Valid)", cred_man.checkCredentials("AndyPants", "pass1"));
            test_json.put("checkCredentials (Admin)", cred_man.checkCredentials("admin_01", "a_good_password"));
            test_json.put("checkCredentials (Invalid)", cred_man.checkCredentials("FakeyMcFakerson", "lolno"));
            // Appointments
            test_json.put("addAppointment (Valid)", app_man.addAppointment("Test_app", "Test Appointment", "2019-03-10 09:30:00.0", 30, 1, 1));
            test_json.put("addAppointment (Redundant)", app_man.addAppointment("Test_app", "Test Appointment", "2019-03-10 09:30:00.0", 30, 1, 1));
            test_json.put("dropAppointment (Valid)", app_man.dropAppointment(1, 1, "2019-03-10 09:30:00.0"));
            test_json.put("dropAppointment (Missing)", app_man.dropAppointment(1, 1, "2019-03-10 09:30:00.0"));
            // Credentials
            int cred_val = cred_man.addCredentials("Test", "Test_Pass", "113-527-9983", "Test Credential");
            test_json.put("addCredential (Valid)", cred_val);
            test_json.put("dropCredential (Valid)", cred_man.dropCredentials(cred_val));
            int pat_val = pat_man.addPatient("TestPat", 1, "135-763-9567");
            // Patients
            test_json.put("addPatient (Valid)", pat_val);
            test_json.put("getPatient (Valid)", pat_man.getPatient(pat_val));
            test_json.put("getAllPatients w/ test value", pat_man.getAllPatients());
            test_json.put("dropPatient (Valid)", pat_man.dropPatient(pat_val));
            test_json.put("getAllPatients w/o test value", pat_man.getAllPatients());
            // Receptionist
            int rec_val = cred_man.addCredentials("TestRec", "Test_Pass_Rec", "113-527-9983", "Test Receptionist Credential");
            test_json.put("addReceptionist (Valid)", rec_man.addReceptionist(rec_val, "Test Reception Role"));
            test_json.put("getAllReceptionists w/ test value", rec_man.getAllReceptionists());
            test_json.put("dropReceptionist (Valid)", rec_man.dropReceptionist(rec_val));
            test_json.put("getAllReceptionists w/o test value", rec_man.getAllReceptionists());
        } catch (SQLException e) {
            test_json = null; 
            e.printStackTrace();
        }
        return test_json.toString();
    }
}
