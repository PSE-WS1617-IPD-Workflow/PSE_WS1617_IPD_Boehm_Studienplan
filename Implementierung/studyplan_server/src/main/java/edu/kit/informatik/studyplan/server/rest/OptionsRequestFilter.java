/**
 * 
 */
package edu.kit.informatik.studyplan.server.rest;

import java.io.IOException;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response.Status;

/**
 * Allows OPTION requests for every resource on this server.
 * @author NiklasUhl
 */
@PreMatching
public class OptionsRequestFilter implements ContainerRequestFilter {

	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		if (requestContext.getMethod().equals(HttpMethod.OPTIONS)) {
			throw new WebApplicationException(Status.ACCEPTED);
		}
	}

}
