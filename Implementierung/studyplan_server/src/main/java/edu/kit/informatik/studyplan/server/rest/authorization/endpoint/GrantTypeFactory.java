package edu.kit.informatik.studyplan.server.rest.authorization.endpoint;

public class GrantTypeFactory {
	
	public GrantType getGrantTypeFor(String responseType) {
		if (responseType == null) {
			return null;
		}
		switch(responseType) {
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
