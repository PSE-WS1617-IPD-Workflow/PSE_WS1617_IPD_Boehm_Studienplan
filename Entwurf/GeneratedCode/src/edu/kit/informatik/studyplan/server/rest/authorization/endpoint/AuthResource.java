package edu.kit.informatik.studyplan.server.rest.authorization.endpoint;

import javax.ws.rs.core.MultivaluedMap;

public class AuthResource {

	private GrantType grantType;
	
	public AuthResource(){
		
	}
	public void getLogin(String client_id, String scope, String state){
		
	}
	public void postToken(MultivaluedMap<String,String> params){
		
	}
	/**
	 * @return the grantType
	 */
	public GrantType getGrantType() {
		return grantType;
	}
	/**
	 * @param grantType the grantType to set
	 */
	public void setGrantType(GrantType grantType) {
		this.grantType = grantType;
	}
}
