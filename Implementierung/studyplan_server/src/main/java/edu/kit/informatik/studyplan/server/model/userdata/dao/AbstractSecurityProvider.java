package edu.kit.informatik.studyplan.server.model.userdata.dao;

import edu.kit.informatik.studyplan.server.model.userdata.User;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationScope;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.RESTClient;

/************************************************************/
/**
 * This class provides Methods for generating access tokens for users and
 * getting an authorization context for an access token.
 */
public abstract class AbstractSecurityProvider {

	/**
	 * Generates a new AuthorizationContext for the given user and client with
	 * the given scope. This creates a new access token which is only valid for
	 * specified time.
	 * 
	 * @param user
	 *            the user
	 * @param client
	 *            the REST client
	 * @param scope
	 *            the scope aquired
	 * @return the new AuthorizationContext
	 * 
	 * @see AuthorizationContext
	 */
	public abstract AuthorizationContext generateAuthorizationContext(User user, RESTClient client,
			AuthorizationScope scope);

	/**
	 * Searches for an AuthorizationContext by the given access token.
	 * @param accessToken the access token
	 * @return the context or <code>null</code> if no context was found
	 */
	public abstract AuthorizationContext getAuthorizationContext(String accessToken);

	/**
	 * 
	 * @return returns a concrete database specific implementation of this interface
	 */
	public static final AbstractSecurityProvider getSecurityProviderImpl() {
		return new SecurityProvider();
	}
	
	/**
	 * Searches for the REST client with the given API key.
	 * @param clientId the API key
	 * @return the found client or <code>null</code> if nothing was found
	 */
	public abstract RESTClient getClient(String clientId);

};
