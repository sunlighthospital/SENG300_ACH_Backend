package com.ach_manager.db;

import java.sql.SQLException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.json.*;

@Path("/test")
public class TestScript {
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public String test(){
		JSONObject test_json = new JSONObject();
		try {
			test_json.put("Val1", AppointmentManager.getDocAppointmentsByDocID(4));
			test_json.put("Val2", DoctorManager.getAllDoctorsInDepByID(2));
		} catch (SQLException e) {
			test_json = null; 
			e.printStackTrace();
		}
		return test_json.toString();
	}
}
