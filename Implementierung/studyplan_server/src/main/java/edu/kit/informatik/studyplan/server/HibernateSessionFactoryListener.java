/**
 * 
 */
package edu.kit.informatik.studyplan.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import edu.kit.informatik.studyplan.server.model.HibernateUtil;

/**
 * Sets up Hibernate SessionFactories at qebapp context startup, in other words,
 * boots Hibernate at webapp start.
 * 
 * @author NiklasUhl
 *
 */
@WebListener
public class HibernateSessionFactoryListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		HibernateUtil.getModuleDataSessionFactory().close();
		HibernateUtil.getUserDataSessionFactory().close();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		HibernateUtil.getModuleDataSessionFactory();
		HibernateUtil.getUserDataSessionFactory();
	}

}
