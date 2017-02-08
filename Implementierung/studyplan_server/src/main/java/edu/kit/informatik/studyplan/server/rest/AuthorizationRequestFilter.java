package edu.kit.informatik.studyplan.server.rest;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response.Status;

import edu.kit.informatik.studyplan.server.model.userdata.authorization.AbstractSecurityProvider;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;

/**
 * ContainerRequestFiler for authorizing REST resource access
 */
@AuthorizationNeeded
@PreMatching
public class AuthorizationRequestFilter implements ContainerRequestFilter {
	
	private static String accessTokenHeader = "Authorization";
	private static String accessTokenPrefix = "Bearer";
	

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		List<String> list = requestContext.getHeaders().get(accessTokenHeader);
		if (list == null) {
			// no header element for access token provided
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
		if (!list.isEmpty()) {
			String value = list.get(0);
			String[] split = value.split("\\s+");
			//header value must only consist of prefix and the access token
			if (split.length != 2 || !split[0].matches(accessTokenPrefix)) {
				throw new WebApplicationException(Status.UNAUTHORIZED); 
			}
			String accessToken = split[1];
			AbstractSecurityProvider securityProvider = AbstractSecurityProvider.getSecurityProviderImpl();
			AuthorizationContext context = securityProvider.getAuthorizationContext(accessToken);
			if (context != null) {
				requestContext.setSecurityContext(context);
				AuthorizationContextFactory.setContext(requestContext, context);
			} else {
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
		} else {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
	}
}
