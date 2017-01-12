package edu.kit.informatik.studyplan.server.rest.authorization.endpoint;

import javax.ws.rs.core.MultivaluedMap;

/**
 * Diese Schnittstelle repräsentiert eine Fabrik zur erstellung von Typen von
 * Authorisation Grant { @see RFC 6749 Kapitel 1.3}
 */
public interface GrantType {

	/**
	 * GET-Anfrage: Gibt den Authorization Endpoint { @see RFC 6749 Kapitel 3.1}
	 * zurück.
	 * 
	 * @see Kapitel 3.2 Tabelle 5.
	 * @param clientID
	 *            den api_key des Klienten.
	 * @param scope
	 *            in den ersten Versionen des Systems immer „student“.
	 * @param state
	 *            ein Schlüssel, der vom REST-Webservice in der Antwort
	 *            mitgesendet wird.
	 */
	public void getLogin(String clientId, String scope, String state);

	/**
	 * POST-Anfrage: Setzt den Token Endpoint { @see RFC 6749 Kapitel 3.1}.
	 * 
	 * @param params
	 *            eine mehrwertige Zuordnung
	 */
	public void postToken(MultivaluedMap<String, String> params);
}
