package com.ach_manager.db;

import java.sql.SQLException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/test")
public class TestScript {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String test(){
		String test_string = "";
		try {
			test_string += AppointmentManager.getDocAppointmentsByDocID(1);
			test_string += "----------------------------------------------\n";
			test_string += DoctorManager.getAllDoctorsInDepByID(2);
		} catch (SQLException e) {
			test_string = "ERROR: Something went wrong. Fuck if we know"; 
			e.printStackTrace();
		}
		return test_string;
	}
}
