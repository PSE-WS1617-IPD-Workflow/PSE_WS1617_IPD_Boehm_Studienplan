package edu.kit.informatik.studyplan.server.rest.authorization.endpoint;

import org.stuff.MultivaluedMap;

import javax.ws.rs.core.MultivaluedMap;
/**
 * Diese Klasse repräsentiert die Authentifizierung-Ressource.
 * Der Resource owner wird zurück zu dieser Ressource weitergeleitet, nachdem er 
 * den Zugang zur Anwendung gewährt hat.
 */
public class AuthResource {

	/**
	 * Eine GrantType instanz.
	 */
	private GrantType grantType;
	/**
	 * Erstellt eine Authentifizierungs-Ressource.
	 */
	public AuthResource(){
		
	}
	/**
	 * GET-Anfrage:
	 * Gibt den Authorization Endpoint {@see RFC 6749 Kapitel 3.1} zurück. 
	 * @see Kapitel 3.2 Tabelle 5.
	 * @param clientID den api_key des Klienten.
	 * @param scope in den ersten Versionen des Systems immer „student“. 
	 * @param state ein Schlüssel, der vom REST-Webservice in der Antwort mitgesendet wird.
	 */
	public void getLogin(String clientID, String scope, String state){
		
	}
	/**
	 * POST-Anfrage:
	 * Setzt den Token Endpoint {@see RFC 6749 Kapitel 3.1}. 
	 * @param params eine mehrwertige Zuordnung
	 */
	public void postToken(MultivaluedMap<String,String> params){
		
	}
	/**
	 * Gibt den GrantType zurück.
	 * @return den grantType 
	 */
	public GrantType getGrantType() {
		return grantType;
	}
}
