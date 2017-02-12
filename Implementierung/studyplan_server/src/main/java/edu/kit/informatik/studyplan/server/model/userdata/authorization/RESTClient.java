package edu.kit.informatik.studyplan.server.model.userdata.authorization;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;

/**
 * Models a rest client which may access the REST API
 */
@Entity
@Table (name = "rest_client")
public class RESTClient {
	
	@Id
	@Column(name = "client_id")
	private int clientId;

	@NaturalId
	@Column(name = "api_key")
	private String apiKey;

	@Column(name = "api_secret")
	private String apiSecret;

	@Column(name = "origin")
	private String origin;
	
	@Column(name = "redirect_url")
	private String redirectUrl;
	
	@Transient
	//TODO find solution
	private List<AuthorizationScope> scopes;

	/**
	 * 
	 * @return returns the unique client id
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * 
	 * @return returns the api secret for this client
	 */
	public String getApiSecret() {
		return apiSecret;
	}

	/**
	 * 
	 * @return returns a regular expression for the URL the client may request from
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * 
	 * @return the redirect URL for this client after the login process
	 */
	public String getRedirectUrl() {
		return redirectUrl;
	}

	/**
	 * 
	 * @return returns a list of all scopes acquirable by this client
	 */
	public List<AuthorizationScope> getScopes() {
		ArrayList<AuthorizationScope> scopes = new ArrayList<AuthorizationScope>();
		scopes.add(AuthorizationScope.STUDENT);
		return scopes;
	}
};
