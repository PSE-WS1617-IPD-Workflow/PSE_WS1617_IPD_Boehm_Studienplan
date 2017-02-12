package edu.kit.informatik.studyplan.server.rest.resources;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import edu.kit.informatik.studyplan.server.model.userdata.Semester;
import edu.kit.informatik.studyplan.server.rest.AuthorizationNeeded;

@Path("/util")
@AuthorizationNeeded
public class UtilResource {
	
	@GET
	@Path("/distance")
	public int getDistanceToCurrentSemester(@BeanParam Semester semester) {
		if (semester.getSemesterType() == null || semester.getYear() < 0) {
			throw new BadRequestException();
		}
		return semester.getDistanceToCurrentSemester();
	}
}
