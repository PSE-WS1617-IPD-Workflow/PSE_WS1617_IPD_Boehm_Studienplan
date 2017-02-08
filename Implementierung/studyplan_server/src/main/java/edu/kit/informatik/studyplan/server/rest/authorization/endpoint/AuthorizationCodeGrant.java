package edu.kit.informatik.studyplan.server.rest.authorization.endpoint;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationScope;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.RESTClient;

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
