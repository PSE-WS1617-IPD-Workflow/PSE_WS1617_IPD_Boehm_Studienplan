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
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraint;
import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/**
 * @author Nada_Chatti
 *
 */
public class SimpleGeneratorTest {
	SimpleGenerator generator = new SimpleGenerator();
	Plan plan = new Plan();
	List<ModuleEntry> moduleEntries;
	Module gbi;
	Module swt;
	@Before
	public void setUp() throws Exception {
		moduleEntries = new ArrayList<ModuleEntry>();
		gbi= new Module();
		
		when(gbi.getIdentifier()).thenReturn("GBI");
		gbi= mock(Module.class);
		when(gbi.getIdentifier()).thenReturn("GBI");
		List<ModuleConstraint> constraints = new ArrayList<ModuleConstraint>();
		ModuleConstraint pre1 = new ModuleConstraint();
//		pre1.setFirstModule(gbi);
//		ModuleConstraint pre2 = new ModuleConstraintDummy("Prerequisite", prog, swt);
//		ModuleConstraint pre3 = new ModuleConstraint("Prerequisite", swt, tse);
//		ModuleConstraint both1 = new ModuleConstraint("Plan_link", m2, m5);
//		ModuleConstraint both2 = new ModuleConstraint("Plan_link", m4, m7);
//		ModuleConstraint sub1 = new ModuleConstraint("Semester_link", m4, m3);
//		ModuleConstraint sub2 = new ModuleConstraint("Semester_link", m6, m7);
//		ModuleConstraint sub3 = new ModuleConstraint("Semester_link", m4, m5);
				
	}

	@Test
	public void test() {
		
	}

}
