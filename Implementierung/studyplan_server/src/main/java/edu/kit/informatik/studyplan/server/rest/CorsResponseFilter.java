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
 * Supplies every response with the CORS headers required by the browser
 * @author NiklasUhl
 */
public class CorsResponseFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
				MultivaluedMap<String, Object> headers = responseContext.getHeaders();
				headers.add("Access-Control-Allow-Origin", "*");
				headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE");
				headers.add("Access-Control-Allow-Headers", "Authorization, Content-Type");
				headers.add("Access-Control-Allow-Credentials", "true");
	}

}
