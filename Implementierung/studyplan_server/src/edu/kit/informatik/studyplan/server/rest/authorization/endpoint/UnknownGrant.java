package edu.kit.informatik.studyplan.server.rest.authorization.endpoint;

import javax.ws.rs.core.MultivaluedMap;
/**
 * Diese Klasse repräsentiert einen unbekannten Granttype. Beim Instanziierung von dieser Klasse wird 
 * eine Fehlermeldung zurückgegeben, da der Granttype ungültig ist.
 */
public class UnknownGrant implements GrantType {

	/**
	 * Gibt fehler zurück.
	 */
	public UnknownGrant(){
		
	}
	@Override
	public void getLogin(String clientId, String scope, String state) {

	}
	@Override
	public void postToken(MultivaluedMap<String, String> params) {

	}

}
