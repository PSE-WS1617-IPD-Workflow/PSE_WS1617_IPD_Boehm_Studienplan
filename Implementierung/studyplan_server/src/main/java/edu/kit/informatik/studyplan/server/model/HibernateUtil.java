// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.server.model;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.ModuleDescription;
import edu.kit.informatik.studyplan.server.model.moduledata.ModuleType;

/************************************************************/
/**
 * Fabrik zur Erzeugung von SessionFactories. Diese sind das
 * Hibernate-Äquivalent zu Datenbankverbindungen.
 */
final class HibernateUtil {
	
	private static SessionFactory moduleDataSessionFactory;
	
	private HibernateUtil() { }
	
	
	/**
	 * Erzeugt die SessionFactory zum Zugriff auf die Modul-Datenbank aus der
	 * entsprechenden Konfigurationsdatei
	 * 
	 * @return die SessionFactory
	 */
	static SessionFactory getModuleDataSessionFactory() {		
		if (moduleDataSessionFactory == null) {
			 // loads configuration and mappings
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(Discipline.class);
            configuration.addAnnotatedClass(Module.class);
            configuration.addAnnotatedClass(ModuleDescription.class);
            configuration.addAnnotatedClass(ModuleType.class);
            configuration.addAnnotatedClass(Category.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();     
            // builds a session factory from the service registry
            moduleDataSessionFactory = configuration.buildSessionFactory(serviceRegistry); 
        }
         
        return moduleDataSessionFactory;
	}

	/**
	 * Erzeugt die SessionFactory zum Zugriff auf die Nutzer-Datenbank aus der
	 * entsprechenden Konfigurationsdatei
	 * 
	 * @return die SessionFactory
	 */
	static SessionFactory getUserDataSessionFactory() {
		return null;
	}
	
};