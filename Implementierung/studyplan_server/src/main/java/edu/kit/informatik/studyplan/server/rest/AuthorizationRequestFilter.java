package edu.kit.informatik.studyplan.server.rest;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;
import edu.kit.informatik.studyplan.server.model.userdata.dao.AbstractSecurityProvider;
import org.glassfish.jersey.message.filtering.EntityFiltering;

/**
 * Klasse für das Filtern von Authentifizierungs-Anfragen.
 */
//Switch request filtering on/off with these two annotations:
//@Provider
//@PreMatching
public class AuthorizationRequestFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		List<String> list = requestContext.getHeaders().get("access_token");
		if (list == null) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
		if (!list.isEmpty()) {
			String accessToken = list.get(0);
			AbstractSecurityProvider securityProvider = AbstractSecurityProvider.getSecurityProviderImpl();
			AuthorizationContext context = securityProvider.getAuthorizationContext(accessToken);
			if (context != null) {
				AuthorizationContextFactory.setContext(requestContext, context);
			} else {
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
		} else {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
	}
}
