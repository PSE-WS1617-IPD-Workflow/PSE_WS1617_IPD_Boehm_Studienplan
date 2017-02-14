package edu.kit.informatik.studyplan.server.rest.resources;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import edu.kit.informatik.studyplan.server.model.userdata.Semester;
import edu.kit.informatik.studyplan.server.rest.AuthorizationNeeded;

/**
 * Util resource for server side calculations
 * @author NiklasUhl
 *
 */
@Path("/util")
@AuthorizationNeeded
public class UtilResource {
	
	/**
	 * Calculates the semester passed since this semester
	 * @param semester the semester
	 * @return the distance
	 */
	@GET
	@Path("/distance")
	public int getDistanceToCurrentSemester(@BeanParam Semester semester) {
		if (semester.getSemesterType() == null || semester.getYear() < 0) {
			throw new BadRequestException();
		}
		return semester.getDistanceToCurrentSemester();
	}
}
