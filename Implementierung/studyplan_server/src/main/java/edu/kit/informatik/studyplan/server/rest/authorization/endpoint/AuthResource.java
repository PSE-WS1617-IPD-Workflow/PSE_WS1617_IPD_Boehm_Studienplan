package edu.kit.informatik.studyplan.server.rest.authorization.endpoint;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edu.kit.informatik.studyplan.server.model.userdata.authorization.AbstractSecurityProvider;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationScope;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.RESTClient;

/**
 * Diese Klasse repräsentiert die Authentifizierung-Ressource. Der Resource
 * owner wird zurück zu dieser Ressource weitergeleitet, nachdem er den Zugang
 * zur Anwendung gewährt hat.
 */
@Path("/auth")
public class AuthResource {

	private static final String IVN_SCOPE = "invalid_scope";
	private static final String UNSUPPORTED_RESPONSE_TYPE = "unsupported_response_type";
	/**
	 * Eine GrantType instanz.
	 */
	private GrantType grantType;

	/**
	 * Erstellt eine Authentifizierungs-Ressource.
	 */
	public AuthResource() {

	}

	/**
	 * GET-Anfrage: Gibt den Authorization Endpoint {@see RFC 6749 Kapitel 3.1}
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
	 * @throws URISyntaxException 
	 */
	@GET
	@Path("/login")
	public Response getLogin(
			@QueryParam("client_id") String clientId, 
			@QueryParam("scope") AuthorizationScope scope, 
			@QueryParam("state") String state,
			@QueryParam("response_type") String responseType,
			@Context HttpHeaders headers) {
		if (clientId == null) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		AbstractSecurityProvider provider = AbstractSecurityProvider.getSecurityProviderImpl();
		RESTClient client = provider.getClient(clientId);
		if (client == null) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		if (!client.getScopes().contains(scope)) {
			String uri = client.getRedirectUrl() + "#" + "error=" + IVN_SCOPE;
			uri += "&state=" + state;
			return Response.temporaryRedirect(URI.create(uri)).build();
		}
		List<String> authorizationHeader = headers.getRequestHeader("Authorization");
		grantType = new GrantTypeFactory().getGrantTypeFor(responseType);
		if (grantType == null) {
			String uri = client.getRedirectUrl() + "#" + "error=" + UNSUPPORTED_RESPONSE_TYPE;
			uri += "&state=" + state;
			return Response.temporaryRedirect(URI.create(uri)).build();
		}
		AuthorizationContext context;
		try {
			context = grantType.getLogin(client, scope, authorizationHeader);
		} catch (UnsupportedOperationException e) {
			String uri = client.getRedirectUrl() + "#" + "error=" + UNSUPPORTED_RESPONSE_TYPE;
			uri += "&state=" + state;
			return Response.temporaryRedirect(URI.create(uri)).build();
		}
		String uri = client.getRedirectUrl() + "#";
		uri += "access_token=" + context.getAccessToken();
		uri += "&token_type=Bearer";
		long seconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), context.getExpiryDate());
		uri += "&expires_in=" + seconds;
		uri += "&scope=" + context.getScope().toString().toLowerCase();
		uri += "&state=" + state;
		provider.cleanUp();
		context.cleanUp();
		return Response.temporaryRedirect(URI.create(uri)).build();
	}

	/**
	 * POST-Anfrage: Setzt den Token Endpoint {@see RFC 6749 Kapitel 3.1}.
	 * 
	 * @param params
	 *            eine mehrwertige Zuordnung
	 */
	@POST
	public void postToken(MultivaluedMap<String, String> params) {

	}

	/**
	 * Gibt den GrantType zurück.
	 * 
	 * @return den grantType
	 */
	public GrantType getGrantType() {
		return grantType;
	}
}
