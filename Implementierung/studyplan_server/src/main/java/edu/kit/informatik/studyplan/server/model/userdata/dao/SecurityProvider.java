package edu.kit.informatik.studyplan.server.model.userdata.dao;

import java.time.LocalDateTime;

import org.hibernate.Session;

import edu.kit.informatik.studyplan.server.model.HibernateUtil;
import edu.kit.informatik.studyplan.server.model.userdata.User;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationScope;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.RESTClient;

/**
 * Concrete SecurityProvider implementation using Hibernate
 * @author NiklasUhl
 */
class SecurityProvider extends AbstractSecurityProvider {
		
	private static long seconds = 3600;
	
	
	@Override
	public AuthorizationContext generateAuthorizationContext(User user, RESTClient client, AuthorizationScope scope) {
		Session session = HibernateUtil.getUserDataSessionFactory().getCurrentSession();
		session.beginTransaction();
		AuthorizationContext context = new AuthorizationContext();
		context.setRestClient(client);
		context.setScope(scope);
		context.setUser(user);
		LocalDateTime expiryDate = LocalDateTime.now().plusSeconds(seconds);
		context.setExpiryDate(expiryDate);
		session.save(context);
		session.flush();
		session.refresh(context);
		session.getTransaction().commit();
		return context;
	}

	@Override
	public AuthorizationContext getAuthorizationContext(String accessToken) {
		if (accessToken == null) {
			return null;
		}
		Session session = HibernateUtil.getUserDataSessionFactory().getCurrentSession();
		session.beginTransaction();
		AuthorizationContext authorizationContext = session.byId(AuthorizationContext.class).load(accessToken);
		session.getTransaction().commit();
		if (authorizationContext != null) {
			if (authorizationContext.getExpiryDate().isBefore(LocalDateTime.now())) {
				authorizationContext = null;
			}
		}
		return authorizationContext;
	}

	@Override
	public RESTClient getClient(String apiKey) {
		if (apiKey == null) {
			return null;
		}
		Session session = HibernateUtil.getUserDataSessionFactory().getCurrentSession();
		session.beginTransaction();
		RESTClient client = session.bySimpleNaturalId(RESTClient.class).load(apiKey);
		session.getTransaction().commit();
		return client;
	}
};
