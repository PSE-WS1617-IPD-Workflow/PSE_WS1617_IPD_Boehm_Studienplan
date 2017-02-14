package edu.kit.informatik.studyplan.server.model.userdata.authorization;

import java.security.Principal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.ws.rs.core.SecurityContext;

import org.hibernate.annotations.GenericGenerator;

import edu.kit.informatik.studyplan.server.model.userdata.User;

/**
 * This class models an authorization context, which stores authorization data
 * for a user logged in.
 * @author NiklasUhl
 */
@Entity
@Table(name = "authorization_context")
public class AuthorizationContext implements SecurityContext {
	/**
	 * 
	 */
	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid-gen")
	@Column(name = "access_token")
	private String accessToken;
	/**
	 * 
	 */
	@Column(name = "expiry_date")
	private LocalDateTime expiryDate;
	/**
	 * 
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "scope")
	private AuthorizationScope scope;
	/**
	 * 
	 */
	@Column(name = "refresh_token")
	private String refreshToken;
	/**
	 * 
	 */
	@ManyToOne
	@JoinColumn(name = "client_id")
	private RESTClient restClient;
	/**
	 * 
	 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	/**
	 * 
	 * @return returns the user this context belongs to
	 */
	public User getUser() {
		return user;
	}

	/**
	 * 
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 
	 * @return return the access token
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * 
	 * @param accessToken
	 *            the acess token to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * 
	 * @return return the expiry date of the access token
	 */
	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	/**
	 * 
	 * @param date
	 *            the expiry date to set
	 */
	public void setExpiryDate(LocalDateTime date) {
		this.expiryDate = date;
	}

	/**
	 * 
	 * @return returns the user's scope
	 */
	public AuthorizationScope getScope() {
		return scope;
	}

	/**
	 * 
	 * @param scope
	 *            the scope to set
	 */
	public void setScope(AuthorizationScope scope) {
		this.scope = scope;
	}

	/**
	 * 
	 * @return returns the refresh token
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * 
	 * @param refreshToken
	 *            the refresh token to set
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	/**
	 * 
	 * @return returns the REST client this context allows access from
	 */
	public RESTClient getRestClient() {
		return restClient;
	}

	/**
	 * 
	 * @param client
	 *            the rest client
	 */
	public void setRestClient(RESTClient client) {
		this.restClient = client;
	}

	@Override
	public Principal getUserPrincipal() {
		return user;
	}

	@Override
	public boolean isUserInRole(String role) {
		return role.equals(scope.toString());
	}

	@Override
	public boolean isSecure() {
		return false;
	}

	@Override
	public String getAuthenticationScheme() {
		return BASIC_AUTH;
	}
};
