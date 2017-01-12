/**
 * 
 */
package edu.kit.informatik.studyplan.server.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * @author NiklasUhl
 *
 */
@Path("/hello")
public class RestDummy {

	/**
	 * 
	 * @param msg
	 *            Hallo
	 * @return The Response
	 */
	@GET
	public Response getMsg() {

		String output = "It works!";
		return Response.status(200).entity(output).build();

	}
}
