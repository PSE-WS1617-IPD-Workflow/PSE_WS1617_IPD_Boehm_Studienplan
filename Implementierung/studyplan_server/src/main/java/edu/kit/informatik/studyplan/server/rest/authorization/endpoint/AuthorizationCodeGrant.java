package edu.kit.informatik.studyplan.server.rest.authorization.endpoint;

import javax.ws.rs.core.MultivaluedMap;

/**
 * Diese Klasse repräsentiert einen AuthorizationCodeGrant { @see RFC 6749
 * Kapitel 1.3.1}. AuthorizationCodeGrant wird in der ersten Version des Systems
 * nicht benötigt aber zur möglichen Erweiterungen vorgesehen. * Bei dem
 * Versuch, eine Authentifizierung mittels diese Klasse durchzuführen wird eine
 * Fehlermeldung zurückgegeben.
 */
public class AuthorizationCodeGrant implements GrantType {

	/**
	 * Erstellt einen AuthorizationCodeGrant.
	 */
	public AuthorizationCodeGrant() {

	}

	public void getLogin(String clientId, String scope, String state) {

	}

	public void postToken(MultivaluedMap<String, String> params) {

	}

}
