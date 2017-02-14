package edu.kit.informatik.studyplan.server.rest.authorization.endpoint;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationScope;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.RESTClient;
import edu.kit.informatik.studyplan.server.model.userdata.dao.AbstractSecurityProvider;

/**
 * REST resource for authorization.<br>
 * The user must call it to acquire an access token
 */
@Path("/auth")
public class AuthResource {

	private static final String IVN_SCOPE = "invalid_scope";
	private static final String UNSUPPORTED_RESPONSE_TYPE = "unsupported_response_type";

	/**
	 * GET-Request: returns the authorization endpoint {@see RFC 6749 chapter
	 * 3.1}
	 * 
	 * @param clientId
	 *            the client's api key
	 * @param scope
	 *            acquired scope
	 * @param state
	 *            a key which should not be modified
	 * @param responseType
	 *            the aquired response type
	 * @param headers
	 *            the request headers
	 * @return a redirect to the client's redirect url with the login
	 *          information in the fragment or an error if authorization was not
	 *          possible
	 */
	@GET
	@Path("/login")
	public Response getLogin(@QueryParam("client_id") String clientId, @QueryParam("scope") AuthorizationScope scope,
			@QueryParam("state") String state, @QueryParam("response_type") String responseType,
			@Context HttpHeaders headers) {
		if (clientId == null) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		//check for valid client
		AbstractSecurityProvider provider = AbstractSecurityProvider.getSecurityProviderImpl();
		RESTClient client = provider.getClient(clientId);
		if (client == null) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		//check for valid scope
		if (!client.getScopes().contains(scope)) {
			String uri = client.getRedirectUrl() + "#" + "error=" + IVN_SCOPE;
			uri += "&state=" + state;
			return Response.temporaryRedirect(URI.create(uri)).build();
		}
		// check for vaild response type
		List<String> authorizationHeader = headers.getRequestHeader("Authorization");
		GrantType grantType = new GrantTypeFactory().getGrantTypeFor(responseType);
		if (grantType == null) {
			String uri = client.getRedirectUrl() + "#" + "error=" + UNSUPPORTED_RESPONSE_TYPE;
			uri += "&state=" + state;
			return Response.temporaryRedirect(URI.create(uri)).build();
		}
		AuthorizationContext context;
		//try acquiring login
		try {
			context = grantType.getLogin(client, scope, authorizationHeader);
		} catch (UnsupportedOperationException e) {
			String uri = client.getRedirectUrl() + "#" + "error=" + UNSUPPORTED_RESPONSE_TYPE;
			uri += "&state=" + state;
			return Response.temporaryRedirect(URI.create(uri)).build();
		}
		//build the redirect URL
		String uri = client.getRedirectUrl() + "#";
		uri += "access_token=" + context.getAccessToken();
		uri += "&token_type=Bearer";
		long seconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), context.getExpiryDate());
		uri += "&expires_in=" + seconds;
		uri += "&scope=" + context.getScope().toString().toLowerCase();
		uri += "&state=" + state;
		return Response.temporaryRedirect(URI.create(uri)).build();
	}
}
