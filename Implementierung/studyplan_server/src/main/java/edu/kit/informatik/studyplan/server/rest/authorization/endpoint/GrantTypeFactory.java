package edu.kit.informatik.studyplan.server.rest.authorization.endpoint;

/**
 * Factory for retrieving the right GrantType for a given responseType
 * 
 * @author NiklasUhl
 *
 * @see GrantType
 */
public class GrantTypeFactory {
	
	/**
	 * Creates an instance of the grant type specified by the response type
	 * @param responseType the response type string
	 * @return the GrantType, <code>null</code> if not found
	 */
	public GrantType getGrantTypeFor(String responseType) {
		if (responseType == null) {
			return null;
		}
		switch (responseType) {
		case "token":
			return new ImplicitGrantType();
		case "code":
			return new AuthorizationCodeGrant();
		case "password":
			return new PasswordCredentialsGrant();
		default:
			return null;
		}
	}
}
