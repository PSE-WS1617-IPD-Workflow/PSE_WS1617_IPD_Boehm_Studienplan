/**
 * 
 */
package edu.kit.informatik.studyplan.server.generation.standard;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import edu.kit.informatik.studyplan.server.generation.Generator;
import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/**
 * @author Nada_Chatti
 *
 */
public class SimpleGeneratorTest {
	Generator generator = new SimpleGenerator();
	Plan plan = new Plan();
	List<ModuleEntry> moduleEntries;
//	moduleEnties.add(new ModuleEntry());
	@Before
	public void setUp() throws Exception {
		
		moduleEntries = new ArrayList<ModuleEntry>();
		
	}

	@Test
	public void test() {
		Plan spy = spy(plan);
		when(spy.getModuleEntries()).thenReturn(moduleEntries);
	}

}
