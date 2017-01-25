package edu.kit.informatik.studyplan.server.model.moduledata.constraint;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import edu.kit.informatik.studyplan.server.model.HibernateUtil;

public class ConstraintTest {

	public static void main(String[] args) {
		SessionFactory sessionFactory = HibernateUtil.getModuleDataSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<ModuleConstraintType> resultList = session
				.createQuery("from ModuleConstraintType", ModuleConstraintType.class).getResultList();
		for (ModuleConstraintType type : resultList) {
			System.out.println(type.getClass().toString());
		}
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
	}

}
