package edu.kit.informatik.studyplan.server.rest.authorization.endpoint;

import javax.ws.rs.core.MultivaluedMap;
/**
 * Diese Klasse repräsentiert einen PasswordCredentialsGrant  { @see RFC 6749 Kapitel 1.3.3}.
 * PasswordCredentialsGrant wird in der ersten Version des Systems nicht benötigt aber zur möglichen 
 * Erweiterungen vorgesehen.
 *  * Bei dem Versuch, eine Authentifizierung mittels diese Klasse durchzuführen wird eine Fehlermeldung
 * zurückgegeben.
 */
public class PasswordCredentialsGrant implements GrantType {
	/**
	 * Erstellt einen PasswordCredentialsGrant.
	 */
	public PasswordCredentialsGrant(){
		
	}
	public void getLogin(String clientId, String scope, String state) {

	}
	public void postToken(MultivaluedMap<String, String> params) {

	}

}
