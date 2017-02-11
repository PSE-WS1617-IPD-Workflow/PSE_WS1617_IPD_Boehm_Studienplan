package edu.kit.informatik.studyplan.server.rest;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import org.hibernate.Session;
import org.hibernate.context.internal.ManagedSessionContext;

import edu.kit.informatik.studyplan.server.model.HibernateUtil;

public class SessionOpenFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		Session moduleSession = HibernateUtil.getModuleDataSessionFactory().openSession();
		ManagedSessionContext.bind(moduleSession);
		Session userSession = HibernateUtil.getUserDataSessionFactory().openSession();
		ManagedSessionContext.bind(userSession);
	}

}
