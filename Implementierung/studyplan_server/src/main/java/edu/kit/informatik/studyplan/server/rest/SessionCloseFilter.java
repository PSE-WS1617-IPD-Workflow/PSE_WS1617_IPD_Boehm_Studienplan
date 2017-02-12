package edu.kit.informatik.studyplan.server.rest;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.context.internal.ManagedSessionContext;

import edu.kit.informatik.studyplan.server.model.HibernateUtil;

@Priority(Priorities.AUTHENTICATION)
public class SessionCloseFilter implements ContainerResponseFilter {
	
	private static Logger logger = Logger.getLogger(SessionCloseFilter.class);

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		if (ManagedSessionContext.hasBind(HibernateUtil.getModuleDataSessionFactory())) {
			Session session = HibernateUtil.getModuleDataSessionFactory().getCurrentSession();
			session.close();
			logger.info("Session "+ session.hashCode() + " closed.");
		}
		if (ManagedSessionContext.hasBind(HibernateUtil.getUserDataSessionFactory())) {
			Session session = HibernateUtil.getUserDataSessionFactory().getCurrentSession();
			session.close();
			logger.info("Session "+ session.hashCode() + " closed.");
		}
	}


}
