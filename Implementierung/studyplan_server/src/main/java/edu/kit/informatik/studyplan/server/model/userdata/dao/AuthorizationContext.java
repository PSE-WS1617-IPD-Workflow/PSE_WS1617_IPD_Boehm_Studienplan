// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.server.model.userdata.dao;

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
import javax.persistence.Transient;
import javax.ws.rs.core.SecurityContext;

import org.hibernate.annotations.GenericGenerator;

import edu.kit.informatik.studyplan.server.model.userdata.User;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationScope;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.RESTClient;

/************************************************************/
/**
 * Modelliert einen Authorisierungskontext.<br>
 * Er enthält die benötigten Informationen für einen authorisierten Benutzer
 * siehe Kapitel ???
 */
@Entity
@Table (name = "authorization_context")
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
	
	@Transient
	@Deprecated
	private AbstractSecurityProvider provider;

	/**
	 * 
	 * @return gibt den Nutzer zurück
	 */
	public User getUser() {
		return user;
	}

	/**
	 * 
	 * @param user
	 *            der Nutzer
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 
	 * @return gibt den Access-Token zurück
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * 
	 * @param accessToken
	 *            der Access-Token
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * 
	 * @return gibt das Verfallsdatum des Access-Tokens zurück
	 */
	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	/**
	 * 
	 * @param date
	 *            das Verfallsdatum
	 */
	public void setExpiryDate(LocalDateTime date) {
		this.expiryDate = date;
	}

	/**
	 * 
	 * @return gibt die Berechtigung des Nutzers zurück
	 */
	public AuthorizationScope getScope() {
		return scope;
	}

	/**
	 * 
	 * @param scope
	 *            die Berechtigung
	 */
	public void setScope(AuthorizationScope scope) {
		this.scope = scope;
	}

	/**
	 * 
	 * @return gibt den Refresh-Token zurück
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * 
	 * @param refreshToken
	 *            der Refresh-Token
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	/**
	 * 
	 * @return gibt den zugehörigen REST-Client zurück
	 */
	public RESTClient getRestClient() {
		return restClient;
	}

	/**
	 * 
	 * @param client
	 *            der REST-Client
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
