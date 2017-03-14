package edu.kit.informatik.studyplan.server.generation.standard;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
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
	User user;
	Discipline discipline;
	Filter filter;
	ModuleDao dao;
	List<Module> byFilter;
	Field field1;
	
	@Before
	public void setUp() throws Exception {
		// initializing generator
		generator = new SimpleGenerator();
		plan = mock(Plan.class);
		generator.setCurrentPlan(plan);
		generator.setMaxECTSperSemester(4.0);
		generator.setMaxSemesterNum(10);
		generator.setMinSemesterNum(2);
		generator.setMinECTSperSemester(2.0);
		// creating modules
		field1 = new Field();
		field1.setFieldId(1);
		field1.setMinEcts(14.0);
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

		ModuleEntry entry1 = new ModuleEntry();
		entry1.setModule(tse);
		entry1.setSemester(3);
		ModuleEntry entry2 = new ModuleEntry();
		entry2.setModule(prog);
		entry2.setSemester(1);
		moduleEntries = new ArrayList<ModuleEntry>();
		when(plan.getAllModuleEntries()).thenReturn(moduleEntries);
		plan.getAllModuleEntries().add(entry1);
		plan.getAllModuleEntries().add(entry2);

		// initialising user and plan
		user = new User();
		discipline = new Discipline();
		discipline.setDisciplineId(0);
		user.setDiscipline(discipline);
		user.setStudyStart(new Semester(SemesterType.WINTER_TERM, 2015));
		user.getPassedModules().add(entry2);

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


	}


	@Test
	public void testGenerateWithValidConstraints() {
		Field field = new Field();
		
		RuleGroup rule = new RuleGroup();
		
		rule.getModules().add(la1);
  		rule.setMinNum(1);
  		rule.setMaxNum(2);
  		
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
		field.setMinEcts(8.0);;
		discipline.getRuleGroups().add(rule);
		discipline.getFields().add(field);
		Map<Field, Category> map = new HashMap<Field, Category>();
		map.put(field, category);
		PartialObjectiveFunction obFunction = new MinimalECTSAtomObjectiveFunction();
		Plan newPlan = generator.generate(obFunction, plan,
				dao, map, 4.0, 2.0, 0, 10);
		assertTrue(newPlan.getUser().equals(plan.getUser()));
		assertFalse(newPlan.getVerificationState().equals(VerificationState.INVALID));
		List<Module> modules = new ArrayList<Module>();
		modules.add(gbi);
		modules.add(swt);
		modules.add(pse);
		modules.add(tse);
		modules.add(la1);
		modules.add(la2);
		modules.add(prog);
		List<Module> newModules = new ArrayList<Module>();
		for(ModuleEntry entry : newPlan.getAllModuleEntries()) {
			newModules.add(entry.getModule());
		}
		assertTrue(newModules.containsAll(modules));
		assertTrue(newModules.contains(ph1) || newModules.contains(ph2));
		assertTrue(newPlan.getVerificationState() == VerificationState.VALID);
	}

	@Test
	public void testGenerateWithInvalidConstraints() {
		Field field = new Field();
		field.setFieldId(0);
		
		Map<Field, Category> map = new HashMap<Field, Category>();
		PartialObjectiveFunction obFunction = new MinimalECTSAtomObjectiveFunction();
		
		// add entries with invalid constraint
		ModuleEntry swtEntry = new ModuleEntry(swt, 1);
		ModuleEntry gbiEntry = new ModuleEntry(gbi, 2);	
		plan.getModuleEntries().clear();
		plan.getModuleEntries().add(swtEntry);
		plan.getModuleEntries().add(gbiEntry);		
		Plan newPlan = generator.generate(obFunction, plan,
				dao, map, 4.0 , 2.0, 0, 10);
		assertTrue(newPlan.getUser().equals(plan.getUser()));
		assertFalse(newPlan.getVerificationState().equals(VerificationState.INVALID));
		List<Module> modules = new ArrayList<Module>();
		modules.add(pse);
		modules.add(tse);
		modules.add(gbi);
		modules.add(prog);
		modules.add(swt);
		List<ModuleEntry> entries = newPlan.getAllModuleEntries();
		assertTrue(entries.stream().map(e -> e.getModule()).collect(Collectors.toList())
				.containsAll(modules));
	}
	
}
