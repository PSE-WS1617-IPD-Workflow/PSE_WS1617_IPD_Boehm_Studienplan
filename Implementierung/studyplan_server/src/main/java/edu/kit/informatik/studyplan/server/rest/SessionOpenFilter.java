package edu.kit.informatik.studyplan.server.rest;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.context.internal.ManagedSessionContext;

import edu.kit.informatik.studyplan.server.model.HibernateUtil;

@Priority(Priorities.AUTHENTICATION)
public class SessionOpenFilter implements ContainerRequestFilter {
	
	private static Logger logger = Logger.getLogger(SessionOpenFilter.class);

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		Session moduleSession = HibernateUtil.getModuleDataSessionFactory().openSession();
		logger.info("Session "+ moduleSession.hashCode() + " openend.");
		ManagedSessionContext.bind(moduleSession);
		Session userSession = HibernateUtil.getUserDataSessionFactory().openSession();
		logger.info("Session "+ userSession.hashCode() + " openend.");
		ManagedSessionContext.bind(userSession);
	}

}
