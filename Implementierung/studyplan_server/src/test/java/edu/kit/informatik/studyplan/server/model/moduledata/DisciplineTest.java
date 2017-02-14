package edu.kit.informatik.studyplan.server.model.moduledata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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

}
