package edu.kit.informatik.studyplan.server.model.userdata.dao;

import org.hibernate.Session;

import edu.kit.informatik.studyplan.server.model.HibernateUtil;
import edu.kit.informatik.studyplan.server.model.userdata.User;

/**
 * Concrete UserDao implementation using Hibernate
 */
class HibernateUserDao implements UserDao {
	
	
	@Override
	public void deleteUser(User user) {
		Session session = HibernateUtil.getUserDataSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(user);
		session.getTransaction().commit();
	}

	@Override
	public void updateUser(User user) {
		Session session = HibernateUtil.getUserDataSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(user);
		session.getTransaction().commit();
	}

	@Override
	public User getUserByName(String name) {
		Session session = HibernateUtil.getUserDataSessionFactory().getCurrentSession();
		session.beginTransaction();
		User user = session.bySimpleNaturalId(User.class).load(name);
		session.getTransaction().commit();
		return user;
	}

};
