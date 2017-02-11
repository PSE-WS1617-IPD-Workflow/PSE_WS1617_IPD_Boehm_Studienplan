package edu.kit.informatik.studyplan.server.model.userdata.dao;

import static org.junit.Assert.assertEquals;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.kit.informatik.studyplan.server.model.HibernateUtil;

public class HibernatePlanDaoTest {


	@Before
	public void setUp() {
	}

	@Test
	public void test() {
		Session session = HibernateUtil.getUserDataSessionFactory().openSession();
		session.equals(null);
		assertEquals(session, HibernateUtil.getUserDataSessionFactory().getCurrentSession());
	}

	@After
	public void tearDown() {
		
	}

}
