package edu.kit.informatik.studyplan.server.rest.authorization.endpoint;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationScope;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.RESTClient;

/**
 * Represents RefreshGrant { @see RFC 6749
 * chapter 1.5}. Not supported by this system, calls to this will fail
 */
public class RefreshGrant implements GrantType {

	@Override
	public AuthorizationContext getLogin(RESTClient client, AuthorizationScope scope,
			List<String> authorizationHeader) {
		throw new UnsupportedOperationException("grant type not supported");
	}

	@Override
	public void postToken(MultivaluedMap<String, String> params) {

	}

}
