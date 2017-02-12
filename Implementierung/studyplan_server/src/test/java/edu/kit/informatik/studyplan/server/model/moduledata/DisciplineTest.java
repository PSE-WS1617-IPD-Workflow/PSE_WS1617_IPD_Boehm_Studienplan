package edu.kit.informatik.studyplan.server.model.moduledata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.hibernate.context.internal.ManagedSessionContext;
import org.junit.Assert;
import org.junit.Test;

import edu.kit.informatik.studyplan.server.model.HibernateUtil;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDao;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDaoFactory;

/**
 * 
 * @author NiklasUhl
 *
 */
public class DisciplineTest {

	/**
	 * Tests all getters and setters of Class Discipline
	 */
	@Test
	public void testGettersAndSetters() {
		Discipline discipline = new Discipline();
		assertEquals(-1, discipline.getDisciplineId());
		assertEquals(null, discipline.getDescription());
		discipline.setDisciplineId(42);
		assertEquals(42, discipline.getDisciplineId());
		discipline.setDescription("Bachelor Informatik");
		assertEquals("Bachelor Informatik", discipline.getDescription());
		assertTrue(discipline.getFields().isEmpty());
		Field field = new Field();
		discipline.getFields().add(field);
		assertTrue(discipline.getFields().get(0) == field);
	}
	
	/**
	 * tests if getCompulsoryModules() only returns compulsory modules
	 */
	@Test
	public void getCompulsoryModulesTest() {
		ManagedSessionContext.bind(HibernateUtil.getModuleDataSessionFactory().openSession());
		ModuleDao dao = ModuleDaoFactory.getModuleDao();
		Discipline discipline = dao.getDisciplineById(1);
		discipline.getCompulsoryModules().stream().map(module -> module.isCompulsory()).forEach(Assert::assertTrue);
		HibernateUtil.getModuleDataSessionFactory().getCurrentSession().close();
	}

}
