package edu.kit.informatik.studyplan.server.rest;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.hibernate.context.internal.ManagedSessionContext;

import edu.kit.informatik.studyplan.server.model.HibernateUtil;

public class SessionCloseFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		if (ManagedSessionContext.hasBind(HibernateUtil.getUserDataSessionFactory())) {
			HibernateUtil.getUserDataSessionFactory().getCurrentSession().close();
		}
		if (ManagedSessionContext.hasBind(HibernateUtil.getModuleDataSessionFactory())) {
			HibernateUtil.getModuleDataSessionFactory().getCurrentSession().close();
		}
	}

}
