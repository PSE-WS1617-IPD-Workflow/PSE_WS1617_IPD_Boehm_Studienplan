/**
 * 
 */
package edu.kit.informatik.studyplan.server;

import edu.kit.informatik.studyplan.server.model.HibernateUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Sets up Hibernate SessionFactories at webapp context startup, in other words,
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
