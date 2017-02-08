package edu.kit.informatik.studyplan.server.rest.authorization.endpoint;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationScope;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.RESTClient;

/**
 * Diese Klasse repräsentiert einen RefreshGrant: beim Ablaufen der
 * Access-Token, schickt dieser Granttype eine Refresh-Token { @see RFC 6749
 * Kapitel 1.5} an den Klient als Antwort .
 */
public class RefreshGrant implements GrantType {

	/**
	 * Erstellt einen RefreshGrant.
	 */
	public RefreshGrant() {

	}

	@Override
	public AuthorizationContext getLogin(RESTClient client, AuthorizationScope scope,
			List<String> authorizationHeader) {
		throw new UnsupportedOperationException("grant type not supported");
	}

	@Override
	public void postToken(MultivaluedMap<String, String> params) {
		// TODO Auto-generated method stub
		
	}

}
