/**
 * 
 */
package edu.kit.informatik.studyplan.server.rest;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;

/**
 * @author NiklasUhl
 *
 */
public class CorsResponseFilter implements ContainerResponseFilter{

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
			if (requestContext.getSecurityContext().getUserPrincipal() != null) {
				MultivaluedMap<String, Object> headers = responseContext.getHeaders();
				headers.add("Access-Control-Allow-Origin", "*");
				headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE");
				headers.add("Access-Control-Allow-Headers", "Authorization");
				headers.add("Access-Control-Allow-Credentials", "true");
			}
	}

}
