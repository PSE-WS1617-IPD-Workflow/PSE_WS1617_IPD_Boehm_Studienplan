package edu.kit.informatik.studyplan.server.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * 
 * @author NiklasUhl
 * Main Resource for checking if REST services are available
 */
@Path("/")
public class MainResource {
	
	/**
	 *
	 * @return returns a Response with status OK
	 */
	@GET
	public Response isServiceAlive() {
		return Response.ok("Service running ...").build();
	}
}
