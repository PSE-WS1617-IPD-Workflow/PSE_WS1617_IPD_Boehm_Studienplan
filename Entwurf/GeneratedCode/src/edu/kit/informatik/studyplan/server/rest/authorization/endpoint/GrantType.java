package edu.kit.informatik.studyplan.server.rest.authorization.endpoint;

import javax.ws.rs.core.MultivaluedMap;

public interface GrantType {

	public void getLogin(String clientId, String scope, String state);
	public void postToken(MultivaluedMap<String, String> params);
}
