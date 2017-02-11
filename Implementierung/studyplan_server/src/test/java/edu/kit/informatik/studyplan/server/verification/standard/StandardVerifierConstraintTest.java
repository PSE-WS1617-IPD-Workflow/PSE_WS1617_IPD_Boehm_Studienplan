package edu.kit.informatik.studyplan.server.verification.standard;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraint;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.OverlappingModuleConstraintType;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.PlanLinkModuleConstraintType;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.PrerequisiteModuleConstraintType;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.SemesterLinkModuleConstraintType;
import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.model.userdata.User;
import edu.kit.informatik.studyplan.server.verification.VerificationResult;

/**
 * Test cases for StandardVerifier testing constraint violation detection
 * @author NiklasUhl
 *
 */
public class StandardVerifierConstraintTest {
	
	private Plan plan;
	private Discipline discipline;
	private Module module1;
	private Module module2;
	private ModuleConstraint constraint;
	
	/**
	 * constructs the necessary classes
	 */
	@Before
	public void setUp() {
		discipline = new Discipline();
		User user = new User();
		user.setDiscipline(discipline);
		plan = new Plan();
		plan.setUser(user);
		constraint = new ModuleConstraint();
		module1 = new Module();
		module1.getFromConstraints().add(constraint);
		module2 = new Module();
		module2.getToConstraints().add(constraint);
		constraint.setFirstModule(module1);
		constraint.setSecondModule(module2);
	}
	
	/**
	 * tests verifier with PlanLinkConstraint
	 */
	@Test
	public void planLinkTest() {
		//only module 1
		constraint.setConstraintType(new PlanLinkModuleConstraintType());
		ModuleEntry entry1 = new ModuleEntry(module1, 3);
		ModuleEntry entry2 = new ModuleEntry(module2, 42);
		plan.getModuleEntries().add(entry1);
		assertTrue(violated(new StandardVerifier().verify(plan)));
		
		//only module 2
		plan.getModuleEntries().clear();
		plan.getModuleEntries().add(entry2);
		assertTrue(violated(new StandardVerifier().verify(plan)));
		
		//both in plan
		plan.getModuleEntries().clear();
		plan.getModuleEntries().add(entry1);
		plan.getModuleEntries().add(entry2);
		assertTrue(isCorrect((new StandardVerifier().verify(plan))));
		
		// module 1 passed, module 2 in plan
		plan.getModuleEntries().clear();
		plan.getModuleEntries().add(entry2);
		plan.getUser().getPassedModules().add(entry1);
		assertTrue(isCorrect((new StandardVerifier().verify(plan))));
		
		//module 1 in plan, module 2 passed
		plan.getUser().getPassedModules().clear();
		plan.getModuleEntries().clear();
		plan.getUser().getPassedModules().add(entry2);
		plan.getModuleEntries().add(entry1);
		assertTrue(isCorrect((new StandardVerifier().verify(plan))));
		
		//both passed
		plan.getUser().getPassedModules().clear();
		plan.getModuleEntries().clear();
		plan.getUser().getPassedModules().add(entry1);
		plan.getUser().getPassedModules().add(entry2);
		assertTrue(isCorrect((new StandardVerifier().verify(plan))));
		
		//module 1 passed
		plan.getUser().getPassedModules().clear();
		plan.getModuleEntries().clear();
		plan.getUser().getPassedModules().add(entry1);
		assertTrue(violated(new StandardVerifier().verify(plan)));
		
		//module 2 passed
		plan.getUser().getPassedModules().clear();
		plan.getModuleEntries().clear();
		plan.getUser().getPassedModules().add(entry2);
		assertTrue(violated(new StandardVerifier().verify(plan)));
		
		
	}

	/**
	 * test verifier with SemesterLinkConstraint
	 */
	@Test
	public void semesterLinkTest() {
		//only module 1
		constraint.setConstraintType(new SemesterLinkModuleConstraintType());
		ModuleEntry entry1 = new ModuleEntry(module1, 3);
		ModuleEntry entry2 = new ModuleEntry(module2, 3);
		plan.getModuleEntries().add(entry1);
		assertTrue(violated(new StandardVerifier().verify(plan)));
		
		//only module 2
		plan.getModuleEntries().clear();
		plan.getModuleEntries().add(entry2);
		assertTrue(violated(new StandardVerifier().verify(plan)));
		
		//both in plan
		plan.getModuleEntries().clear();
		plan.getModuleEntries().add(entry1);
		plan.getModuleEntries().add(entry2);
		assertTrue(isCorrect((new StandardVerifier().verify(plan))));
		
		//both passed
		plan.getUser().getPassedModules().clear();
		plan.getModuleEntries().clear();
		plan.getUser().getPassedModules().add(entry1);
		plan.getUser().getPassedModules().add(entry2);
		assertTrue(isCorrect((new StandardVerifier().verify(plan))));
		
		//module 1 passed
		plan.getUser().getPassedModules().clear();
		plan.getModuleEntries().clear();
		plan.getUser().getPassedModules().add(entry1);
		assertTrue(violated(new StandardVerifier().verify(plan)));
		
		//module 2 passed
		plan.getUser().getPassedModules().clear();
		plan.getModuleEntries().clear();
		plan.getUser().getPassedModules().add(entry2);
		assertTrue(violated(new StandardVerifier().verify(plan)));
		
		
	}
	
	/**
	 * test verifier with OverlappingConstraint
	 */
	@Test
	public void overlappingTest() {
		//only module 1
		constraint.setConstraintType(new OverlappingModuleConstraintType());
		ModuleEntry entry1 = new ModuleEntry(module1, 3);
		ModuleEntry entry2 = new ModuleEntry(module2, 3);
		plan.getModuleEntries().add(entry1);
		assertTrue(isCorrect(new StandardVerifier().verify(plan)));
		
		//only module 2
		plan.getModuleEntries().clear();
		plan.getModuleEntries().add(entry2);
		assertTrue(isCorrect(new StandardVerifier().verify(plan)));
		
		//both in plan, same semester
		plan.getModuleEntries().clear();
		plan.getModuleEntries().add(entry1);
		plan.getModuleEntries().add(entry2);
		assertTrue(violated((new StandardVerifier().verify(plan))));
		
		//both in plan, different semester
		plan.getModuleEntries().clear();
		plan.getModuleEntries().add(entry1);
		plan.getModuleEntries().add(new ModuleEntry(module2, 2));
		assertTrue(isCorrect((new StandardVerifier().verify(plan))));
		
		//both passed
		plan.getUser().getPassedModules().clear();
		plan.getModuleEntries().clear();
		plan.getUser().getPassedModules().add(entry1);
		plan.getUser().getPassedModules().add(entry2);
		assertTrue(isCorrect((new StandardVerifier().verify(plan))));
		
		//module 1 passed
		plan.getUser().getPassedModules().clear();
		plan.getModuleEntries().clear();
		plan.getUser().getPassedModules().add(entry1);
		assertTrue(isCorrect(new StandardVerifier().verify(plan)));
		
		//module 2 passed
		plan.getUser().getPassedModules().clear();
		plan.getModuleEntries().clear();
		plan.getUser().getPassedModules().add(entry2);
		assertTrue(isCorrect(new StandardVerifier().verify(plan)));
		
		
	}

	/**
	 * test verifier with PrerequisiteConstraint
	 */
	@Test
	public void prerequisiteTest() {
		
		//module 1 is prerequisite of module 2
		constraint.setConstraintType(new PrerequisiteModuleConstraintType());
		
		ModuleEntry entry1sem3 = new ModuleEntry(module1, 3);
		ModuleEntry entry1sem5 = new ModuleEntry(module1, 5);
		ModuleEntry entry2sem3 = new ModuleEntry(module2, 3);
		ModuleEntry entry2sem5 = new ModuleEntry(module2, 5);
		
		//only module 1
		clear();
		plan.getModuleEntries().add(entry1sem3);
		assertTrue(isCorrect(new StandardVerifier().verify(plan)));
		
		//only module 2
		clear();
		plan.getModuleEntries().add(entry2sem3);
		assertTrue(violated(new StandardVerifier().verify(plan)));
		
		//only module 1 passed
		clear();
		plan.getUser().getPassedModules().add(entry1sem3);
		assertTrue(isCorrect(new StandardVerifier().verify(plan)));
		
		//only module 2 passed
		clear();
		plan.getUser().getPassedModules().add(entry2sem3);
		assertTrue(isCorrect(new StandardVerifier().verify(plan)));
		
		//module 1 and 2, same semester
		clear();
		plan.getModuleEntries().add(entry1sem3);
		plan.getModuleEntries().add(entry2sem3);
		assertTrue(violated(new StandardVerifier().verify(plan)));
		
		//module 1 and 2, 1 before 2
		clear();
		plan.getModuleEntries().add(entry1sem3);
		plan.getModuleEntries().add(entry2sem5);
		assertTrue(isCorrect(new StandardVerifier().verify(plan)));
		
		//module 1 and 2, 1 after 2
		clear();
		plan.getModuleEntries().add(entry1sem5);
		plan.getModuleEntries().add(entry2sem3);
		assertTrue(violated(new StandardVerifier().verify(plan)));
		
		//module 1 and 2 passed, same semester
		clear();
		plan.getUser().getPassedModules().add(entry1sem3);
		plan.getUser().getPassedModules().add(entry2sem3);
		assertTrue(isCorrect(new StandardVerifier().verify(plan)));
		
		//module 1 and 2 passed, 1 before 2
		clear();
		plan.getUser().getPassedModules().add(entry1sem3);
		plan.getUser().getPassedModules().add(entry2sem5);
		assertTrue(isCorrect(new StandardVerifier().verify(plan)));
		
		//module 1 and 2 passed, 1 after 2
		clear();
		plan.getUser().getPassedModules().add(entry1sem5);
		plan.getUser().getPassedModules().add(entry2sem3);
		assertTrue(isCorrect(new StandardVerifier().verify(plan)));
		
		
		//module 1 passed, module 2 in plan, same semester
		clear();
		plan.getUser().getPassedModules().add(entry1sem3);
		plan.getModuleEntries().add(entry2sem3);
		assertTrue(isCorrect(new StandardVerifier().verify(plan)));
		
		//module 1 passed, module 2 in plan, 1 before 2
		clear();
		plan.getUser().getPassedModules().add(entry1sem3);
		plan.getModuleEntries().add(entry2sem5);
		assertTrue(isCorrect(new StandardVerifier().verify(plan)));
		
		//module 1 passed, module 2 in plan, 1 after 2
		clear();
		plan.getUser().getPassedModules().add(entry1sem5);
		plan.getModuleEntries().add(entry2sem3);
		assertTrue(isCorrect(new StandardVerifier().verify(plan)));
		
		//module 2 passed, module 1 in plan, same semester
		clear();
		plan.getUser().getPassedModules().add(entry2sem3);
		plan.getModuleEntries().add(entry1sem3);
		assertTrue(isCorrect(new StandardVerifier().verify(plan)));
				
		//module 2 passed, module 1 in plan, 1 before 2
		clear();
		plan.getUser().getPassedModules().add(entry2sem5);
		plan.getModuleEntries().add(entry1sem3);
		assertTrue(isCorrect(new StandardVerifier().verify(plan)));
				
		//module 2 passed, module 1 in plan, 1 after 2
		clear();
		plan.getUser().getPassedModules().add(entry2sem3);
		plan.getModuleEntries().add(entry1sem5);
		assertTrue(isCorrect(new StandardVerifier().verify(plan)));
		
		
	}

	private void clear() {
		plan.getModuleEntries().clear();
		plan.getUser().getPassedModules().clear();
	}

	private boolean violated(VerificationResult result) {
		return !result.isCorrect() 
				&& result.getCompulsoryViolations().isEmpty()
				&& result.getFieldViolations().isEmpty()
				&& result.getRuleGroupViolations().isEmpty()
				&& result.getViolations().size() == 1
				&& result.getViolations().iterator().next().equals(constraint);
	}
	
	private boolean isCorrect(VerificationResult result) {
		return result.isCorrect() 
				&& result.getCompulsoryViolations().isEmpty()
				&& result.getFieldViolations().isEmpty()
				&& result.getRuleGroupViolations().isEmpty()
				&& result.getViolations().isEmpty();
	}


}
