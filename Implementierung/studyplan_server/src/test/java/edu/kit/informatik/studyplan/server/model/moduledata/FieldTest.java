/**
 * 
 */
package edu.kit.informatik.studyplan.server.model.moduledata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDao;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDaoFactory;

/**
 * @author NiklasUhl
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ModuleDaoFactory.class)
public class FieldTest {
	
	/**
	 * sets up the mocks required for getSubjects
	 */
	@Before
	public void setUp() {
		PowerMockito.mockStatic(ModuleDaoFactory.class);
		List<Category> answer = new LinkedList<Category>();
		Category category = new Category();
		category.setName("Kartoffel");
		answer.add(category);
		ModuleDao dao = mock(ModuleDao.class);
		when(dao.getSubjects(any(Field.class))).thenReturn(answer);
		when(ModuleDaoFactory.getModuleDao()).thenReturn(dao);
	}
	/**
	 * Tests all getters and setters of class Field
	 */
	@Test
	public void testGettersAndSetters() {
		Field field = new Field();
		assertEquals(-1, field.getFieldId());
		assertEquals(null, field.getName());
		assertEquals(null, field.getDiscipline());
		assertEquals(0, field.getMinEcts(), 0);
		assertTrue(field.getModules().isEmpty());
		field.setFieldId(42);
		assertEquals(42, field.getFieldId());
		field.setName("Grundstudium");
		assertEquals("Grundstudium", field.getName());
		field.setMinEcts(3);
		assertEquals(3, field.getMinEcts(), 0);
		Discipline discipline = new Discipline();
		field.setDiscipline(discipline);
		assertTrue(discipline == field.getDiscipline());
		field.setChoosable(true);
		assertEquals(true, field.isChoosable());
		Module module = new Module();
		field.getModules().add(module);
		assertEquals(1, field.getModules().size());
		assertTrue(field.getModules().get(0) == module);
		assertEquals(1, field.getSubjects().size());
		assertEquals("Kartoffel", field.getSubjects().get(0).getName());
		field.setChoosable(false);
		assertEquals(null, field.getSubjects());
	}

}
