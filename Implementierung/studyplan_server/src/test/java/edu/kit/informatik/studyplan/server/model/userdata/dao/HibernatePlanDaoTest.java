package edu.kit.informatik.studyplan.server.model.userdata.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.kit.informatik.studyplan.server.model.HibernateUtil;

public class HibernatePlanDaoTest {

	@Before
	public void setUp() {
		HibernateUtil.getUserDataSessionFactory();
	}

	@Test
	public void test() {

	}

	@After
	public void tearDown() {
		HibernateUtil.getUserDataSessionFactory().close();
	}

}
