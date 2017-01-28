package edu.kit.informatik.studyplan.server.model;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Factory for obtaining singleton Hibernate SessionFactories.
 * 
 * @author NiklasUhl
 * @version 1.0
 */
public final class HibernateUtil {

	private static SessionFactory moduleDataSessionFactory;

	private static SessionFactory userDataSessionFactory;

	private HibernateUtil() {
	}

	/**
	 * @return returns a {@linkplain SessionFactory} singleton for accessing
	 *         module database
	 */
	public static SessionFactory getModuleDataSessionFactory() {
		if (moduleDataSessionFactory == null) {
			StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
					.configure("moduledata.cfg.xml").build();
			Metadata metadata = new MetadataSources(standardRegistry).buildMetadata();
			moduleDataSessionFactory = metadata.buildSessionFactory();
		}

		return moduleDataSessionFactory;
	}

	/**
	 * @return returns a {@linkplain SessionFactory} singleton for accessing
	 *         userdata database
	 */
	public static SessionFactory getUserDataSessionFactory() {
		if (userDataSessionFactory == null) {
			StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
					.configure("userdata.cfg.xml").build();
			Metadata metadata = new MetadataSources(standardRegistry).buildMetadata();
			userDataSessionFactory = metadata.buildSessionFactory();
		}

		return userDataSessionFactory;
	}

}
