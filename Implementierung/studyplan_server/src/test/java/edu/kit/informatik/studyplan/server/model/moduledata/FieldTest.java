/**
 * 
 */
package edu.kit.informatik.studyplan.server.model.moduledata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author NiklasUhl
 *
 */
public class FieldTest {

	/**
	 * Tests all getters and setters of class Field
	 */
	@Test
	public void testGettersAndSetters() {
		Field field = new Field();
		assertEquals(0, field.getFieldId());
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
	}

}
