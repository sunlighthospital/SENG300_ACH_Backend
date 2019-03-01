package com.ach_manager.db;

import java.sql.SQLException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/test")
public class TestScript {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String testDBQueries() throws SQLException {
		String tsv = "";
		tsv += AppointmentManager.getDocAppointmentsByDocID(1);
		tsv += "----------------------------------------------\n";
		tsv += DoctorManager.getAllDoctorsInDepByID(2);
		return tsv;
	}
}
