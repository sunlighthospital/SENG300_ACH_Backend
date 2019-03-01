package com.ach_manager.db;

import java.sql.SQLException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/test")
public class TestScript {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String test() throws SQLException {
		String test_string = "";
		test_string += AppointmentManager.getDocAppointmentsByDocID(1);
		test_string += "----------------------------------------------\n";
		test_string += DoctorManager.getAllDoctorsInDepByID(2);
		return test_string;
	}
}
