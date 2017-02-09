/**
 * 
 */
package edu.kit.informatik.studyplan.server.rest;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;

/**
 * @author NiklasUhl
 *
 */
@Path("/dummy")
public class RestDummy {

	@Inject
	Provider<AuthorizationContext> context;

	/**
	 * 
	 * 
	 * @return The Response
	 */
	@GET
	@Path("hello")
	@AuthorizationNeeded
	public Response getHello() {
		String output = "Hello " + context.get().getUserPrincipal().getName();
		return Response.status(200).entity(output).build();
	}
	
	/**
	 * 
	 * @return bye
	 */
	@GET
	@Path("bye")
	public Response getBye() {
		String output = "Bye!";
		return Response.status(200).entity(output).build();
	} 
}
