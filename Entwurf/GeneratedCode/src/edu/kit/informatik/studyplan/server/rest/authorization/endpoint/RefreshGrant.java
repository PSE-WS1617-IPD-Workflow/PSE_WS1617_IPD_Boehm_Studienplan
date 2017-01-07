package edu.kit.informatik.studyplan.server.rest.authorization.endpoint;

import javax.ws.rs.core.MultivaluedMap;
/**
 * Diese Klasse repräsentiert einen RefreshGrant: beim Ablaufen der Access-Token, schickt dieser Granttype
 * eine Refresh-Token { @see RFC 6749 Kapitel 1.5} an den Klient als Antwort .
 */
public class RefreshGrant implements GrantType {

	/**
	 * Erstellt einen RefreshGrant.
	 */
	public RefreshGrant(){
		
	}
	@Override
	public void getLogin(String clientId, String scope, String state) {

	}
	@Override
	public void postToken(MultivaluedMap<String, String> params) {

	}

}
