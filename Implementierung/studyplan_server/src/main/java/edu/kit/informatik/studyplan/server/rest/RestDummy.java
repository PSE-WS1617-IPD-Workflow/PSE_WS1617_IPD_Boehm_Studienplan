/**
 * 
 */
package edu.kit.informatik.studyplan.server.rest;

import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * @author NiklasUhl
 *
 */
@Path("/hello")
public class RestDummy {

	@Inject
	AuthorizationContext context;

	/**
	 * 
	 * 
	 * @return The Response
	 */
	@GET
	public Response getMsg() {
		String output = "It works!";
		return Response.status(200).entity(output).build();
	}
}
