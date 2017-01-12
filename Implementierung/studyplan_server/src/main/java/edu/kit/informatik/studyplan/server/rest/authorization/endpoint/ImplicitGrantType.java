package edu.kit.informatik.studyplan.server.rest.authorization.endpoint;

import javax.ws.rs.core.MultivaluedMap;

/**
 * Diese Klasse repräsentiert einen ImplicitGrantType { @see RFC 6749 Kapitel
 * 1.3.2}.
 */
public class ImplicitGrantType implements GrantType {
	/**
	 * Erstellt einen ImplicitGrantType.
	 */
	public ImplicitGrantType() {

	}

	public void getLogin(String clientId, String scope, String state) {

	}

	public void postToken(MultivaluedMap<String, String> params) {

	}

}
