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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test_string += "----------------------------------------------\n";
		try {
			test_string += DoctorManager.getAllDoctorsInDepByID(2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return test_string;
	}
	
	public static void main(String args[]) {
		System.out.println("Fuck this");
	}
}
