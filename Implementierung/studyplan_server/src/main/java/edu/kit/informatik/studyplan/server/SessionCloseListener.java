package edu.kit.informatik.studyplan.server;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.hibernate.context.internal.ManagedSessionContext;

import edu.kit.informatik.studyplan.server.model.HibernateUtil;

/**
 * This Listener destroys the database sessions associated with the current
 * context when the HTTP Session is destroyed.
 * 
 * @author NiklasUhl
 *
 */
@WebListener
public class SessionCloseListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		if (ManagedSessionContext.hasBind(HibernateUtil.getModuleDataSessionFactory())) {
			HibernateUtil.getModuleDataSessionFactory().close();
		}
		if (ManagedSessionContext.hasBind(HibernateUtil.getUserDataSessionFactory())) {
			HibernateUtil.getUserDataSessionFactory().close();
		}
	}

}
