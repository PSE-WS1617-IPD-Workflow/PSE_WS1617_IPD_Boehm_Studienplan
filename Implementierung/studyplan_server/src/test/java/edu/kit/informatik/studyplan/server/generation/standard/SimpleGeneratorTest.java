package edu.kit.informatik.studyplan.server.generation.standard;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.powermock.core.classloader.annotations.PrepareForTest;

import edu.kit.informatik.studyplan.server.filter.CategoryFilter;
import edu.kit.informatik.studyplan.server.filter.Filter;
import edu.kit.informatik.studyplan.server.generation.objectivefunction.MinimalECTSAtomObjectiveFunction;
import edu.kit.informatik.studyplan.server.generation.objectivefunction.PartialObjectiveFunction;
import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.CycleType;
import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.RuleGroup;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraint;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.PlanLinkModuleConstraintType;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.PrerequisiteModuleConstraintType;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.SemesterLinkModuleConstraintType;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDao;
import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;
import edu.kit.informatik.studyplan.server.model.userdata.ModulePreference;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.model.userdata.PreferenceType;
import edu.kit.informatik.studyplan.server.model.userdata.Semester;
import edu.kit.informatik.studyplan.server.model.userdata.SemesterType;
import edu.kit.informatik.studyplan.server.model.userdata.User;
import edu.kit.informatik.studyplan.server.model.userdata.VerificationState;

/**
 * This Use Case tests the methods of the SimpleGenerator class.
 * @author Nada_Chatti
 *
 */
@PrepareForTest(ModuleDao.class)
public class SimpleGeneratorTest {
	SimpleGenerator generator;
	Plan plan;
	List<ModuleEntry> moduleEntries;
	List<ModulePreference> modPreferences;
	Module gbi;
	Module swt;
	Module prog;
	Module tse;
	Module pse;
	Module la1;
	Module la2;
	Module ph1;	
	Category category;
	List<Node> nodesToCompareTo;
	User user;
	Discipline discipline;
	Filter filter;
	ModuleDao dao;
	List<Module> byFilter;
	Field field1;
	
	@Before
	public void setUp() throws Exception {
		// initialising generator
		generator = new SimpleGenerator();
		// creating modules
		field1 = new Field();
		field1.setFieldId(1);
		
		gbi = new Module();
		gbi.setIdentifier("GBI");
		gbi.setCycleType(CycleType.BOTH);
		gbi.setCreditPoints(2.0);
		gbi.setField(field1);

		swt = new Module();
		swt.setIdentifier("SWT");
		swt.setCycleType(CycleType.SUMMER_TERM);
		swt.setCreditPoints(2.0);
		swt.setField(field1);
		
		prog = new Module();
		prog.setIdentifier("PROG");
		prog.setCycleType(CycleType.BOTH);
		prog.setCreditPoints(2.0);
		prog.setField(field1);

		tse = new Module();
		tse.setIdentifier("TSE");
		tse.setCycleType(CycleType.BOTH);
		tse.setCreditPoints(2.0);
		tse.setField(field1);
		
		pse = new Module();
		pse.setIdentifier("PSE");
		pse.setCycleType(CycleType.BOTH);
		pse.setCreditPoints(2.0);
		pse.setField(field1);

		la1 = new Module();
		la1.setIdentifier("LA1");
		la1.setCycleType(CycleType.BOTH);
		la1.setCreditPoints(2.0);
		la1.setField(field1);

		la2 = new Module();
		la2.setIdentifier("LA2");
		la2.setCycleType(CycleType.BOTH);
		la2.setCreditPoints(2.0);
		la2.setField(field1);

		ph1 = new Module();
		ph1.setIdentifier("PH1");
		ph1.setCycleType(CycleType.BOTH);
		ph1.setCreditPoints(2.0);

		// creating constraints

		ModuleConstraint pre1 = new ModuleConstraint();
		pre1.setFirstModule(gbi);
		pre1.setSecondModule(swt);
		pre1.setConstraintType(new PrerequisiteModuleConstraintType());

		ModuleConstraint both = new ModuleConstraint();
		both.setFirstModule(gbi);
		both.setSecondModule(prog);
		both.setConstraintType(new PlanLinkModuleConstraintType());

		ModuleConstraint pre2 = new ModuleConstraint();
		pre2.setFirstModule(swt);
		pre2.setSecondModule(tse);
		pre2.setConstraintType(new PrerequisiteModuleConstraintType());

		ModuleConstraint pre3 = new ModuleConstraint();
		pre3.setFirstModule(swt);
		pre3.setSecondModule(pse);
		pre3.setConstraintType(new PrerequisiteModuleConstraintType());

		ModuleConstraint sym = new ModuleConstraint();
		sym.setFirstModule(pse);
		sym.setSecondModule(tse);
		sym.setConstraintType(new SemesterLinkModuleConstraintType());

		ModuleConstraint sym2 = new ModuleConstraint();
		sym2.setFirstModule(la1);
		sym2.setSecondModule(la2);
		sym2.setConstraintType(new SemesterLinkModuleConstraintType());

		// adding constraints to modules

		prog.getToConstraints().add(both);
		gbi.getFromConstraints().add(both);
		swt.getToConstraints().add(pre1);
		tse.getToConstraints().add(pre2);
		tse.getToConstraints().add(sym);
		pse.getToConstraints().add(pre3);
		tse.getFromConstraints().add(sym);
		la1.getFromConstraints().add(sym2);
		la1.getToConstraints().add(sym2);

		// creating and adding module entries

		plan = mock(Plan.class);
		ModuleEntry entry1 = new ModuleEntry();
		entry1.setModule(tse);
		entry1.setSemester(3);
		ModuleEntry entry2 = new ModuleEntry();
		entry2.setModule(prog);
		entry2.setSemester(1);
		moduleEntries = new ArrayList<ModuleEntry>();
		when(plan.getModuleEntries()).thenReturn(moduleEntries);
		plan.getModuleEntries().add(entry1);
		plan.getModuleEntries().add(entry2);

		// initialising user and plan
		user = new User();
		discipline = new Discipline();
		discipline.setDisciplineId(0);
		user.setDiscipline(discipline);
		user.setStudyStart(new Semester(SemesterType.WINTER_TERM, 2014));

		// plan.setUser(user);
		when(plan.getUser()).thenReturn(user);
		ModulePreference modPref = new ModulePreference(la1, PreferenceType.POSITIVE, plan);
		modPreferences = new ArrayList<ModulePreference>();
		modPreferences.add(modPref);
		when(plan.getPreferences()).thenReturn(modPreferences);

		// mocking moduleDao

		dao = mock(ModuleDao.class);
		// mocking getModulesbyFilter
		byFilter = new ArrayList<Module>();
		byFilter.add(la1);
		filter = null;
		when(dao.getModulesByFilter(filter, discipline)).thenReturn(byFilter);

		// creating nodes to compare with

		nodesToCompareTo = new ArrayList<Node>();
		nodesToCompareTo.add(new NodeWithOutput(pse, plan, generator));
		nodesToCompareTo.add(new NodeWithOutput(tse, plan, generator));
		nodesToCompareTo.add(new NodeWithOutput(gbi, plan, generator));
		nodesToCompareTo.add(new NodeWithOutput(prog, plan, generator));
		nodesToCompareTo.add(new NodeWithOutput(swt, plan, generator));

	}

	@Test
	public void testPlanToGraph() {
		generator.planToGraph(plan);
		assertTrue(generator.getNodes().containsAll(nodesToCompareTo));
	}

	@Test
	public void testGetModulesWithPreference() {
		List<Module> modlist = new ArrayList<Module>();
		for (Node n : nodesToCompareTo) {
			modlist.add(n.getModule());
		}
		modlist.add(la1);

		for (Module m : modlist) {
			if (!m.equals(la1)) {
				when(plan.getPreferenceForModule(m)).thenReturn(PreferenceType.NEGATIVE);
			} else {
				when(plan.getPreferenceForModule(m)).thenReturn(PreferenceType.POSITIVE);

			}
		}

		assertTrue(generator.getModulesWithPreference(plan, modlist, category, PreferenceType.POSITIVE, dao)
				.containsAll(byFilter));
	}

	@Test
	public void testAddRuleGroupModules() {
		RuleGroup rule = new RuleGroup();
		rule.getModules().add(la1);
		rule.setMinNum(1);
		rule.setMaxNum(2);
		generator.planToGraph(plan);
		generator.addRuleGroupModules(rule, plan, category, dao);
		nodesToCompareTo.add(new NodeWithOutput(la1, plan, generator));
		nodesToCompareTo.add(new NodeWithOutput(la2, plan, generator));
		assertTrue(generator.getNodes().containsAll(nodesToCompareTo));

	}

	@Test
	public void testAddFieldModules() {
		Module ph2;
		Module hm;
		Module numerik;
		
		hm = new Module();
		hm.setIdentifier("HM");
		hm.setCycleType(CycleType.BOTH);
		hm.setCreditPoints(2.0);
		
		numerik = new Module();
		numerik.setIdentifier("NUMERIK");
		numerik.setCycleType(CycleType.BOTH);
		numerik.setCreditPoints(3.0);
		
		ph2 = new Module();
		ph2.setIdentifier("PH2");
		ph2.setCycleType(CycleType.BOTH);
		ph2.setCreditPoints(2.0);
		
		Field field = new Field();
		field.getModules().add(ph1);
		ph1.setField(field);
		field.getModules().add(ph2);
		ph2.setField(field);
		field.getModules().add(hm);
		hm.setField(field);
		field.getModules().add(numerik);
		numerik.setField(field);
		field.getModules().add(tse);
		tse.setField(field);
		category = new Category();
		List<Module> modulesByCategory = new ArrayList<Module>();
		modulesByCategory.add(hm);
		modulesByCategory.add(numerik);
		when(dao.getModulesByFilter(Matchers.any(Filter.class), Matchers.any(Discipline.class))).
			thenReturn(modulesByCategory);
		field.setMinEcts(8.0);
		generator.planToGraph(plan);
		generator.addFieldModules(field, category, plan, dao);
		nodesToCompareTo.add(new NodeWithOutput(hm, plan, generator));
		nodesToCompareTo.add(new NodeWithOutput(numerik, plan, generator));
		assertTrue(generator.getNodes().containsAll(nodesToCompareTo));
		assertTrue(generator.getNodes().contains(new NodeWithOutput(ph1, plan, generator))
				|| generator.getNodes().contains(new NodeWithOutput(ph2, plan, generator)));
		assertTrue(generator.getNodes().getCreditPoints(field) == 9.0);
	}

	@Test
	public void testParallelize() {
		generator.planToGraph(plan);
		int[] result = generator.parallelize(generator.getNodes().sort(), 4);
		int[] compareTo = new int[] { 1, 1, 2, 3 };
		assertArrayEquals(compareTo, result);
	}

	@Test
	public void testCreatePlan() {
		generator.planToGraph(plan);
		Plan newPlan = generator.createPlan(generator.getNodes().sort(),
				generator.parallelize(generator.getNodes().sort(), 4), plan.getUser());
		Plan compareTo = new Plan();
		ModuleEntry entry1 = new ModuleEntry(prog, 1);
		ModuleEntry entry2 = new ModuleEntry(gbi, 1);
		ModuleEntry entry3 = new ModuleEntry(swt, 2);
		ModuleEntry entry4 = new ModuleEntry(pse, 3);
		ModuleEntry entry5 = new ModuleEntry(tse, 3);
		compareTo.getModuleEntries().add(entry1);
		compareTo.getModuleEntries().add(entry2);
		compareTo.getModuleEntries().add(entry3);
		compareTo.getModuleEntries().add(entry4);
		compareTo.getModuleEntries().add(entry5);
		assertTrue(newPlan.getModuleEntries().size() == compareTo.getModuleEntries().size());
	}


	@Test
	public void testRandomlyGeneratedPlan() {
		Field field = new Field();
		field.getModules().add(ph1);
		field.setMinEcts(2.0);
		ph1.setField(field);
		discipline.getFields().add(field);
		RuleGroup rule = new RuleGroup();
		rule.getModules().add(la1);
		rule.setMinNum(1);
		rule.setMaxNum(2);
		discipline.getRuleGroups().add(rule);
		Map<Field, Category> map = new HashMap<Field, Category>();
		map.put(field, category);
		generator.planToGraph(plan);
		assertTrue(generator.randomlyGeneratedPlan(generator.getNodes(), plan, 
				map, 4, dao).getNodesList().contains(new NodeWithOutput(ph1, plan, generator)));
		assertTrue(generator.randomlyGeneratedPlan(generator.getNodes(), plan, 
				map, 4, dao).getNodesList().contains(new NodeWithOutput(la1, plan, generator)));
	}
	
	@Test
	public void testRandomlyGeneratedFamilyOfPlans() {
		Field field = new Field();
		field.getModules().add(ph1);
		field.setMinEcts(2.0);
		ph1.setField(field);
		
		Module ph2 = new Module();
		ph2.setIdentifier("PH2");
		ph2.setCycleType(CycleType.BOTH);
		ph2.setCreditPoints(2.0);
		field.getModules().add(ph2);
		ph2.setField(field);
		
		discipline.getFields().add(field);
		RuleGroup rule = new RuleGroup();
		rule.getModules().add(la1);
		rule.setMinNum(1);
		rule.setMaxNum(2);
		
		
		rule.getModules().add(ph2);

		
		discipline.getRuleGroups().add(rule);
		Map<Field, Category> map = new HashMap<Field, Category>();
		map.put(field, category);
		generator.planToGraph(plan);
		
		assertTrue(generator.randomlyGeneratedFamilyOfPlans(generator.getNodes(), plan, 
				map, -1, 4, dao).size() == 10);
		System.out.println("NOT NULL BABE");
		for(NodesList l : generator.randomlyGeneratedFamilyOfPlans(generator.getNodes(), plan, 
				map, -1, 4, dao).values()) {
			assertTrue(l.contains(new NodeWithOutput(ph1, plan, generator))
					|| l.contains(new NodeWithOutput(ph2, plan, generator)));
			assertTrue((l.contains(new NodeWithOutput(la1, plan, generator)) 
					&& l.contains(new NodeWithOutput(la2, plan, generator)))
					|| l.contains(new NodeWithOutput(ph2, plan, generator)));		}
	}

	@Test
	public void testGenerate() {
		Field field = new Field();
		field.setFieldId(0);
		field.getModules().add(ph1);
		field.setMinEcts(2.0);
		ph1.setField(field);
		
		RuleGroup rule = new RuleGroup();
		rule.getModules().add(la1);
		rule.setMinNum(1);
		rule.setMaxNum(2);
		discipline.getRuleGroups().add(rule);
		discipline.getFields().add(field);
		Map<Field, Category> map = new HashMap<Field, Category>();
		map.put(field, category);
		PartialObjectiveFunction obFunction = new MinimalECTSAtomObjectiveFunction();
		Plan newPlan = generator.generate(obFunction, plan,
				dao, map, 4);
		for(ModuleEntry m : newPlan.getAllModuleEntries()){
			System.out.println(m.getModule().getIdentifier());
		}
		assertTrue(newPlan.getUser().equals(plan.getUser()));
		assertFalse(newPlan.getVerificationState().equals(VerificationState.INVALID));
		List<Module> modules = new ArrayList<Module>();
		modules.add(pse);
		modules.add(tse);
		modules.add(gbi);
		modules.add(la1);
		modules.add(la2);
		modules.add(ph1);
		modules.add(prog);
		modules.add(swt);
		List<Module> newModules = new ArrayList<Module>();
		for(ModuleEntry entry : newPlan.getModuleEntries()) {
			newModules.add(entry.getModule());
		}
		assertTrue(newModules.containsAll(newModules));
//		for(Node n: generator.getNodes()) {
//			System.out.println(n.getModule().getIdentifier());
//		}
	}

	
}
