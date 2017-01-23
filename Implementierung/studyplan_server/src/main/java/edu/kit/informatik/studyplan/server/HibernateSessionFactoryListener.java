/**
 * 
 */
package edu.kit.informatik.studyplan.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import edu.kit.informatik.studyplan.server.model.HibernateUtil;

/**
 * @author NiklasUhl
 *
 */
// @WebListener
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
