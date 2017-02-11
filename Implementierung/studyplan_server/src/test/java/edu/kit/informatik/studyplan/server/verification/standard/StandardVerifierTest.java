package edu.kit.informatik.studyplan.server.verification.standard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.RuleGroup;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraint;
import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.model.userdata.User;
import edu.kit.informatik.studyplan.server.verification.VerificationResult;

public class StandardVerifierTest {
	
	private Plan plan;
	private Discipline discipline;
	private RuleGroup ruleGroup;
	private Field field;
	private Module independentModule1;
	private Module independentModule2;
	private Module module1;
	private Module module2;
	private ModuleConstraint constraint;
	
	@Before
	public void setUp() throws Exception {
		discipline = new Discipline();
		field = new Field();
		ruleGroup = new RuleGroup();
		discipline.getFields().add(field);
		discipline.getRuleGroups().add(ruleGroup);
		User user = new User();
		user.setDiscipline(discipline);
		plan = new Plan();
		plan.setUser(user);
		independentModule1 = new Module();
		independentModule2 = new Module();
		module1 = new Module();
		module1.getFromConstraints().add(constraint);
		module2 = new Module();
		module2.getToConstraints().add(constraint);
	}

	@Test
	public void emptyPlanTest() {
		VerificationResult verify = new StandardVerifier().verify(plan);
		assertTrue(verify.isCorrect());
		assertTrue(verify.getFieldViolations().isEmpty());
		assertTrue(verify.getViolations().isEmpty());
		assertTrue(verify.getRuleGroupViolations().isEmpty());
	}
	
	@Test
	public void emptyPlanFieldViolation() {
		field.setMinEcts(3);
		VerificationResult verify = new StandardVerifier().verify(plan);
		assertTrue(!verify.isCorrect());
		assertEquals(verify.getFieldViolations().size(), 1);
		assertEquals(verify.getFieldViolations().iterator().next(), field);
		assertTrue(verify.getViolations().isEmpty());
		assertTrue(verify.getRuleGroupViolations().isEmpty());
	}
	
	@Test
	public void emptyPlanRuleGroupViolation() {
		ruleGroup.setMinNum(1);
		VerificationResult verify = new StandardVerifier().verify(plan);
		assertTrue(!verify.isCorrect());
		assertEquals(verify.getRuleGroupViolations().size(), 1);
		assertEquals(verify.getRuleGroupViolations().iterator().next(), ruleGroup);
		assertTrue(verify.getViolations().isEmpty());
		assertTrue(verify.getFieldViolations().isEmpty());
	}
	
	@Test
	public void independentModulePlanTest() {
		ModuleEntry entry = new ModuleEntry(independentModule1, 1);
		plan.getModuleEntries().add(entry);
		VerificationResult verify = new StandardVerifier().verify(plan);
		assertTrue(verify.isCorrect());
		assertTrue(verify.getFieldViolations().isEmpty());
		assertTrue(verify.getViolations().isEmpty());
		assertTrue(verify.getRuleGroupViolations().isEmpty());
	}
	
	@Test
	public void independentCompulsoryModulePlanTest() {
		independentModule1.setCompulsory(true);
		discipline.getCompulsoryModules().add(independentModule1);
		ModuleEntry entry = new ModuleEntry(independentModule1, 1);
		plan.getModuleEntries().add(entry);
		VerificationResult verify = new StandardVerifier().verify(plan);
		assertTrue(verify.isCorrect());
		assertTrue(verify.getFieldViolations().isEmpty());
		assertTrue(verify.getViolations().isEmpty());
		assertTrue(verify.getRuleGroupViolations().isEmpty());
		assertTrue(verify.getCompulsoryViolations().isEmpty());
	}
	
	@Test
	public void independentCompulsoryModulePlanViolationTest() {
		independentModule1.setCompulsory(true);
		discipline.getCompulsoryModules().add(independentModule1);
		VerificationResult verify = new StandardVerifier().verify(plan);
		assertTrue(!verify.isCorrect());
		assertTrue(verify.getFieldViolations().isEmpty());
		assertTrue(verify.getViolations().isEmpty());
		assertTrue(verify.getRuleGroupViolations().isEmpty());
		assertEquals(verify.getCompulsoryViolations().size(), 1);
		assertEquals(verify.getCompulsoryViolations().iterator().next(), independentModule1);
	}

		
	@Test
	public void independentModulePlanFieldViolationTest() {
		ModuleEntry entry = new ModuleEntry(independentModule1, 1);
		plan.getModuleEntries().add(entry);
		field.setMinEcts(3);
		VerificationResult verify = new StandardVerifier().verify(plan);
		assertTrue(!verify.isCorrect());
		assertEquals(verify.getFieldViolations().size(), 1);
		assertEquals(verify.getFieldViolations().iterator().next(), field);
		assertTrue(verify.getViolations().isEmpty());
		assertTrue(verify.getRuleGroupViolations().isEmpty());
	}
	
	@Test
	public void independentModulePlanRuleGroupViolationTest() {
		ModuleEntry entry = new ModuleEntry(independentModule1, 1);
		plan.getModuleEntries().add(entry);
		ruleGroup.setMinNum(1);
		VerificationResult verify = new StandardVerifier().verify(plan);
		assertTrue(!verify.isCorrect());
		assertEquals(verify.getRuleGroupViolations().size(), 1);
		assertEquals(verify.getRuleGroupViolations().iterator().next(), ruleGroup);
		assertTrue(verify.getViolations().isEmpty());
		assertTrue(verify.getFieldViolations().isEmpty());
	}

	@Test
	public void independentModulePlanFieldTest() {
		ModuleEntry entry = new ModuleEntry(independentModule1, 1);
		field.getModules().add(independentModule1);
		independentModule1.setField(field);
		independentModule1.setCreditPoints(3);
		plan.getModuleEntries().add(entry);
		field.setMinEcts(3);
		VerificationResult verify = new StandardVerifier().verify(plan);
		assertTrue(verify.isCorrect());
		assertTrue(verify.getFieldViolations().isEmpty());
		assertTrue(verify.getViolations().isEmpty());
		assertTrue(verify.getRuleGroupViolations().isEmpty());
		assertTrue(verify.getCompulsoryViolations().isEmpty());
	}

	@Test
	public void independentModulePlanRuleGroupTest() {
		ModuleEntry entry = new ModuleEntry(independentModule1, 1);
		ruleGroup.getModules().add(independentModule1);
		plan.getModuleEntries().add(entry);
		ruleGroup.setMinNum(1);
		VerificationResult verify = new StandardVerifier().verify(plan);
		assertTrue(verify.isCorrect());
		assertTrue(verify.getFieldViolations().isEmpty());
		assertTrue(verify.getViolations().isEmpty());
		assertTrue(verify.getRuleGroupViolations().isEmpty());
		assertTrue(verify.getCompulsoryViolations().isEmpty());
	}
	
	@Test
	public void independentModulesPlanFieldTest() {
		ModuleEntry entry1 = new ModuleEntry(independentModule1, 1);
		ModuleEntry entry2 = new ModuleEntry(independentModule2, 1);
		field.getModules().add(independentModule1);
		field.getModules().add(independentModule2);
		independentModule1.setField(field);
		independentModule1.setCreditPoints(3);
		independentModule2.setField(field);
		independentModule2.setCreditPoints(3);
		plan.getModuleEntries().add(entry1);
		plan.getModuleEntries().add(entry2);
		field.setMinEcts(6);
		VerificationResult verify = new StandardVerifier().verify(plan);
		assertTrue(verify.isCorrect());
		assertTrue(verify.getFieldViolations().isEmpty());
		assertTrue(verify.getViolations().isEmpty());
		assertTrue(verify.getRuleGroupViolations().isEmpty());
		assertTrue(verify.getCompulsoryViolations().isEmpty());
	}

	@Test
	public void independentModulesPlanRuleGroupTest() {
		ModuleEntry entry1 = new ModuleEntry(independentModule1, 1);
		ModuleEntry entry2 = new ModuleEntry(independentModule2, 1);
		ruleGroup.getModules().add(independentModule1);
		ruleGroup.getModules().add(independentModule2);
		plan.getModuleEntries().add(entry1);
		plan.getModuleEntries().add(entry2);
		ruleGroup.setMinNum(2);
		VerificationResult verify = new StandardVerifier().verify(plan);
		assertTrue(verify.isCorrect());
		assertTrue(verify.getFieldViolations().isEmpty());
		assertTrue(verify.getViolations().isEmpty());
		assertTrue(verify.getRuleGroupViolations().isEmpty());
		assertTrue(verify.getCompulsoryViolations().isEmpty());
	}
	
	@Test
	public void independentModulesPlanRuleGroupMaxValueTest() {
		ModuleEntry entry1 = new ModuleEntry(independentModule1, 1);
		ModuleEntry entry2 = new ModuleEntry(independentModule2, 1);
		ruleGroup.getModules().add(independentModule1);
		ruleGroup.getModules().add(independentModule2);
		plan.getModuleEntries().add(entry1);
		plan.getModuleEntries().add(entry2);
		ruleGroup.setMinNum(1);
		ruleGroup.setMaxNum(1);
		VerificationResult verify = new StandardVerifier().verify(plan);
		assertTrue(!verify.isCorrect());
		assertTrue(verify.getFieldViolations().isEmpty());
		assertTrue(verify.getViolations().isEmpty());
		assertTrue(!verify.getRuleGroupViolations().isEmpty());
		assertTrue(verify.getCompulsoryViolations().isEmpty());
	}


}
