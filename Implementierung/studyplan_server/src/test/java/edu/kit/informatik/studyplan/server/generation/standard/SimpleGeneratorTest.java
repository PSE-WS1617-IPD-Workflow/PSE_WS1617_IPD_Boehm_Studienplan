/**
 * 
 */
package edu.kit.informatik.studyplan.server.generation.standard;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.kit.informatik.studyplan.server.filter.CategoryFilter;
import edu.kit.informatik.studyplan.server.filter.Filter;
import edu.kit.informatik.studyplan.server.generation.Generator;
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
import edu.kit.informatik.studyplan.server.model.userdata.User;

/**
 * @author Nada_Chatti
 *
 */
@PrepareForTest( ModuleDao.class ) 
public class SimpleGeneratorTest {
	SimpleGenerator generator = new SimpleGenerator();
	Plan plan = mock(Plan.class);
	List<ModuleEntry> moduleEntries;
	Module gbi;
	Module swt;
	Module prog;
	Module tse;
	Module pse;
	Module la1;
	Module la2;
	Category category;
	List<Node> modules;
	User user;
	Discipline discipline;
	List<ModulePreference> modPreferences;
	@Before
	public void setUp() throws Exception {
		moduleEntries = new ArrayList<ModuleEntry>();
		gbi= new Module();
		gbi.setIdentifier("GBI");
		gbi.setCycleType(CycleType.BOTH);
		swt= new Module();
		swt.setIdentifier("SWT");
		swt.setCycleType(CycleType.SUMMER_TERM);
		prog= new Module();
		prog.setIdentifier("PROG");
		prog.setCycleType(CycleType.BOTH);
		tse= new Module();
		tse.setIdentifier("TSE");
		tse.setCycleType(CycleType.BOTH);
		pse= new Module();
		pse.setIdentifier("PSE");
		pse.setCycleType(CycleType.BOTH);
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
		prog.getToConstraints().add(both);
		gbi.getFromConstraints().add(both);
		swt.getToConstraints().add(pre1);
		tse.getToConstraints().add(pre2);
		tse.getToConstraints().add(sym);
		pse.getToConstraints().add(pre3);
		tse.getFromConstraints().add(sym);
		System.out.println(swt.getConstraints().size());
		modules = new ArrayList<Node>();
		modules.add(new NodeWithOutput(pse));
		modules.add(new NodeWithOutput(tse));
		modules.add(new NodeWithOutput(gbi));
		modules.add(new NodeWithOutput(prog));
		modules.add(new NodeWithOutput(swt));
		user = new User();
		discipline = new Discipline();
		discipline.setDisciplineId(0);
		user.setDiscipline(discipline);
		when(plan.getUser()).thenReturn(user);
		ModuleEntry entry = new ModuleEntry();
		entry.setModule(tse);
		List<ModuleEntry> moduleEntries = new ArrayList<ModuleEntry>();
		when(plan.getModuleEntries()).thenReturn(moduleEntries);
		plan.getModuleEntries().add(entry);
		generator.planToGraph(plan);
		modPreferences = new ArrayList<ModulePreference>();
		la1 = new Module();
		la1.setIdentifier("LA1");
		la1.setCycleType(CycleType.BOTH);
		la2 = new Module();
		la2.setIdentifier("LA2");
		la2.setCycleType(CycleType.BOTH);
		ModuleConstraint sym2 = new ModuleConstraint();
		sym2.setFirstModule(la1);
		sym2.setSecondModule(la2);
		sym2.setConstraintType(new SemesterLinkModuleConstraintType());
		ModulePreference modPref = new ModulePreference();
		modPref.setModule(la1);
		modPref.setPreference(PreferenceType.POSITIVE);
		modPreferences.add(modPref);
		plan.setModulePreferences(modPreferences);
		Category category = new Category();
		category.setName("Math");
//		Filter filter = new CategoryFilter(category.getCategoryId(), currentPlan.getUser().getDiscipline());
//		ModuleDao 
	}

	@Test
	public void testPlanToGraph() {
		
		assertTrue(SimpleGenerator.getNodes().getAllNodes().containsAll(modules));
	}

	@Test
	public void testGetModulesWithPreference() {
		List<Module> modlist = new ArrayList<Module>();
		for(Node n : modules) {
			modlist.add(n.getModule());
		}
		modlist.add(la1);
		Category spy = spy(category);
		when(spy.getCategoryId()).thenReturn(0);
		Filter filter = null;
		 ModuleDao dao = mock(ModuleDao.class);
		 List<Module> byFilter = new ArrayList<Module>();
		 byFilter.add(la1);
		 when(dao.getModulesByFilter(filter, discipline)).thenReturn(byFilter);
		 for(Module m : modlist) {
			 if(!m.equals(la1)) {
			 when(plan.getPreferenceForModule(m)).thenReturn(PreferenceType.NEGATIVE);
			 }
			 else {
				 when(plan.getPreferenceForModule(m)).thenReturn(PreferenceType.POSITIVE);

			 }
		 }
		assertTrue(generator.getModulesWithPreference(plan
				, modlist, category, PreferenceType.POSITIVE, dao).containsAll(byFilter));
	}
	
//	@Test
//	public void testAddRuleGroupModules(){
//		RuleGroup rule = new RuleGroup();
//		rule.getModules().add(la1);
//		 rule.setMinNum(1);
//		 rule.setMaxNum(2);
//		 
//		 
//		}
//	}
}
