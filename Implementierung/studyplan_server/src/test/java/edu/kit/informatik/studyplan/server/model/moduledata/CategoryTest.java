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
public class CategoryTest {

	/**
	 * tests all getters and setters of class Category
	 */
	@Test
	public void testGettersAndSetters() {
		Category category = new Category();
		assertEquals(-1, category.getCategoryId());
		assertEquals(null, category.getName());
		assertTrue(!category.isSubject());
		category.setCategoryId(42);
		assertEquals(42, category.getCategoryId());
		category.setName("Theoretische Informatik");
		assertEquals("Theoretische Informatik", category.getName());
		category.setSubject(true);
		assertTrue(category.isSubject());
	}

}
