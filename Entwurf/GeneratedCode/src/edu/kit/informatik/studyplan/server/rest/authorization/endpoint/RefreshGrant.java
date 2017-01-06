package edu.kit.informatik.studyplan.server.rest.authorization.endpoint;

import javax.ws.rs.core.MultivaluedMap;

public class RefreshGrant implements GrantType {

	public RefreshGrant(){
		
	}
	
	public void getLogin(String clientId, String scope, String state) {

	}

	public void postToken(MultivaluedMap<String, String> params) {

	}

}
