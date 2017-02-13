package edu.kit.informatik.studyplan.server.generation.standard;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;

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

/**
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
	Module ph;
	Category category;
	List<Node> nodesToCompareTo;
	User user;
	Discipline discipline;
	Filter filter;
	ModuleDao dao;
	List<Module> byFilter;

	@Before
	public void setUp() throws Exception {
		// initialising generator
		generator = new SimpleGenerator();
		// creating modules

		gbi = new Module();
		gbi.setIdentifier("GBI");
		gbi.setCycleType(CycleType.BOTH);
		gbi.setCreditPoints(2.0);

		swt = new Module();
		swt.setIdentifier("SWT");
		swt.setCycleType(CycleType.SUMMER_TERM);
		swt.setCreditPoints(2.0);

		prog = new Module();
		prog.setIdentifier("PROG");
		prog.setCycleType(CycleType.BOTH);
		prog.setCreditPoints(2.0);

		tse = new Module();
		tse.setIdentifier("TSE");
		tse.setCycleType(CycleType.BOTH);
		tse.setCreditPoints(2.0);

		pse = new Module();
		pse.setIdentifier("PSE");
		pse.setCycleType(CycleType.BOTH);
		pse.setCreditPoints(2.0);

		la1 = new Module();
		la1.setIdentifier("LA1");
		la1.setCycleType(CycleType.BOTH);
		la1.setCreditPoints(2.0);

		la2 = new Module();
		la2.setIdentifier("LA2");
		la2.setCycleType(CycleType.BOTH);
		la2.setCreditPoints(2.0);

		ph = new Module();
		ph.setIdentifier("PH");
		ph.setCycleType(CycleType.BOTH);
		ph.setCreditPoints(2.0);

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

		// creating and adding module entry

		plan = mock(Plan.class);
		ModuleEntry entry = new ModuleEntry();
		entry.setModule(tse);
		entry.setSemester(3);

		// plan.getModuleEntries().add(entry);

		moduleEntries = new ArrayList<ModuleEntry>();
		when(plan.getModuleEntries()).thenReturn(moduleEntries);
		plan.getModuleEntries().add(entry);

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
		nodesToCompareTo.add(new NodeWithOutput(pse, generator));
		nodesToCompareTo.add(new NodeWithOutput(tse, generator));
		nodesToCompareTo.add(new NodeWithOutput(gbi, generator));
		nodesToCompareTo.add(new NodeWithOutput(prog, generator));
		nodesToCompareTo.add(new NodeWithOutput(swt, generator));

	}

	@Test
	public void testPlanToGraph() {
		generator.planToGraph(plan);
		assertTrue(generator.getNodes().getAllNodes().containsAll(nodesToCompareTo));
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
		nodesToCompareTo.add(new NodeWithOutput(la1, generator));
		nodesToCompareTo.add(new NodeWithOutput(la2, generator));
		assertTrue(generator.getNodes().getAllNodes().containsAll(nodesToCompareTo));

	}

	@Test
	public void testAddFieldModules() {
		Field field = new Field();
		field.getModules().add(ph);
		field.setMinEcts(2.0);
		generator.planToGraph(plan);
		generator.addFieldModules(field, category, plan, dao);
		nodesToCompareTo.add(new NodeWithOutput(ph, generator));
		assertTrue(generator.getNodes().getAllNodes().containsAll(nodesToCompareTo));
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
		generator.parallelize(generator.getNodes().sort(), 4);
		Plan newPlan = generator.createPlan(generator.getNodes().sort(),
				generator.parallelize(generator.getNodes().sort(), 4), plan);
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
	public void testRandomlyGeneratedFamilyOfPlans() {
		Field field = new Field();
		field.getModules().add(ph);
		field.setMinEcts(2.0);
		RuleGroup rule = new RuleGroup();
		rule.getModules().add(la1);
		rule.setMinNum(1);
		rule.setMaxNum(2);
		Map<Field, Category> map = new HashMap<Field, Category>();
		map.put(field, category);
		generator.planToGraph(plan);
		assertTrue(generator.randomlyGeneratedFamilyOfPlans(generator.getNodes(), plan, map, -1, 4, dao).size() == 10);

	}

	@Test
	public void testGenerate() {
		Field field = new Field();
		field.getModules().add(ph);
		field.setMinEcts(2.0);
		RuleGroup rule = new RuleGroup();
		rule.getModules().add(la1);
		rule.setMinNum(1);
		rule.setMaxNum(2);
		Map<Field, Category> map = new HashMap<Field, Category>();
		map.put(field, category);
		PartialObjectiveFunction obFunction = new MinimalECTSAtomObjectiveFunction();
		generator.generate(obFunction, plan, dao, map, 4);
	}

}
