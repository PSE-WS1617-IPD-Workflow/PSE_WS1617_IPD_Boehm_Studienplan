/**
 * 
 */
package edu.kit.informatik.studyplan.server.generation.standard;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import edu.kit.informatik.studyplan.server.filter.Filter;
import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.CycleType;
import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
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
	Plan plan = new Plan();
	List<ModuleEntry> moduleEntries;
	Module gbi;
	Module swt;
	Module prog;
	Module tse;
	Module pse;
	Module la1;
	Module la2;
	Category category = new Category();
	List<Node> nodes;
	User user;
	Discipline discipline;
	List<ModulePreference> modPreferences;
	Filter filter;
	ModuleDao dao;
	@Before
	public void setUp() throws Exception {
		// creating modules
		
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
		la1 = new Module();
		la1.setIdentifier("LA1");
		la1.setCycleType(CycleType.BOTH);
		la2 = new Module();
		la2.setIdentifier("LA2");
		la2.setCycleType(CycleType.BOTH);
		
		//creating constraints
		
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
		
		//adding constraints to modules
		
		prog.getToConstraints().add(both);
		gbi.getFromConstraints().add(both);
		swt.getToConstraints().add(pre1);
		tse.getToConstraints().add(pre2);
		tse.getToConstraints().add(sym);
		pse.getToConstraints().add(pre3);
		tse.getFromConstraints().add(sym);
		
		//creating and adding module entry
		
		ModuleEntry entry = new ModuleEntry();
		entry.setModule(tse);
		plan.getModuleEntries().add(entry);
		
//		moduleEntries = new ArrayList<ModuleEntry>();
//		when(plan.getModuleEntries()).thenReturn(moduleEntries);
//		plan.getModuleEntries().add(entry);
		
		//initialising user
		
		user = new User();
		discipline = new Discipline();
		discipline.setDisciplineId(0);
		user.setDiscipline(discipline);
		plan.setUser(user);
//		when(plan.getUser()).thenReturn(user);
		
		// mocking moduleDao
		
		dao = mock(ModuleDao.class);
		
		//creating nodes to compare with
		
		nodes = new ArrayList<Node>();
		nodes.add(new NodeWithOutput(pse));
		nodes.add(new NodeWithOutput(tse));
		nodes.add(new NodeWithOutput(gbi));
		nodes.add(new NodeWithOutput(prog));
		nodes.add(new NodeWithOutput(swt));
		
		ModulePreference modPref = new ModulePreference(la1, PreferenceType.POSITIVE);
		plan.getPreferences().add(modPref);
		System.out.println(modPreferences.get(0).getModule().getIdentifier());
		if (plan.getPreferences().isEmpty()) System.out.println("EMPTY");
		System.out.println("end set up");
//		System.out.println(plan.getPreferences().get(0).getModule().getIdentifier() + "Hillow");
//		category.setName("Math");
	}

	@Test
	public void testPlanToGraph() {
		System.out.println("plan to graph");
		generator.planToGraph(plan);
		for(Node n: SimpleGenerator.getNodes().getAllNodes()) {
			System.out.println("generated" + n.getModule().getIdentifier());
		}
		for(Node n: nodes) {
			System.out.println(n.getModule().getIdentifier());
		}
		assertTrue(SimpleGenerator.getNodes().getAllNodes().containsAll(nodes));
	}

	@Test
	public void testGetModulesWithPreference() {
		List<Module> modlist = new ArrayList<Module>();
		for(Node n : nodes) {
			modlist.add(n.getModule());
		}
		modlist.add(la1);
//		Category spy = spy(category);
		 
		 for(Module m : modlist) {
			 if(!m.equals(la1)) {
			 when(plan.getPreferenceForModule(m)).thenReturn(PreferenceType.NEGATIVE);
			 }
			 else {
				 when(plan.getPreferenceForModule(m)).thenReturn(PreferenceType.POSITIVE);

			 }
		 }
		 for (ModulePreference pref : plan.getPreferences()) {
			 System.out.println(pref.getModule().getIdentifier());
		 }
		 
		 //mocking getModulesbyFilter
		 List<Module> byFilter = new ArrayList<Module>();
		 byFilter.add(la1);
		 filter = null;
		 when(dao.getModulesByFilter(filter, discipline)).thenReturn(byFilter);
		assertTrue(generator.getModulesWithPreference(plan
				, modlist, category, PreferenceType.POSITIVE, dao).containsAll(byFilter));
	}
	
	@Test
	public void testAddRuleGroupModules(){
		RuleGroup rule = new RuleGroup();
		rule.getModules().add(la1);
		 rule.setMinNum(1);
		 rule.setMaxNum(2);
//		 assert(generator.addRuleGroupModules(rule, plan, category, null))
		 
	}
}
