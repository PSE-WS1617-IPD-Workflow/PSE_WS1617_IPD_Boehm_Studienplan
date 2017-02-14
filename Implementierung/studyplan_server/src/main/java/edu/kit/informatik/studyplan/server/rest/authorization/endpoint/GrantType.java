package edu.kit.informatik.studyplan.server.rest.authorization.endpoint;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationScope;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.RESTClient;

/**
 * Representation of Authorization Grants { @see RFC 6749 chapter 1.3}.<br>
 * May be used to login a user.
 */
public interface GrantType {

	/**
	 * returns the authorization endpoint { @see RFC 6749 Kapitel 3.1} as {@link AuthorizationContext}
	 * 
	 * @param client
	 *            the rest client
	 * @param scope
	 *            the acquired scope
	 * @param authorizationHeader
	 *            content of the authorization header of the request
	 * @return the authorization endpoint represented as {@link AuthorizationContext}
	 * 
	 * @throws UnsupportedOperationException if this operation is supported for this grant type
	 */
	AuthorizationContext getLogin(RESTClient client, AuthorizationScope scope, List<String> authorizationHeader)
			throws UnsupportedOperationException;

	/**
	 * sets the token endpoint { @see RFC 6749 chapter 3.1}.
	 * 
	 * @param params
	 *            the query params
	 */
	void postToken(MultivaluedMap<String, String> params);
}
