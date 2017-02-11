package edu.kit.informatik.studyplan.server.generation.standard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import edu.kit.informatik.studyplan.server.filter.CategoryFilter;
import edu.kit.informatik.studyplan.server.filter.Filter;
import edu.kit.informatik.studyplan.server.generation.Generator;
import edu.kit.informatik.studyplan.server.generation.objectivefunction.PartialObjectiveFunction;
import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.RuleGroup;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraint;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDao;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDaoFactory;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.model.userdata.PreferenceType;

/************************************************************/
/**
 * The SimpleGenerator Class is a concrete Generator that implements the
 * Generator Interface. It uses a graph structure to sort and parallelize
 * modules, and an objective Function to evaluate the plans.
 */
public class SimpleGenerator implements Generator {
	/**
	 * ModulDao to use the Filters.
	 */
	private ModuleDao moduleDAO = ModuleDaoFactory.getModuleDao();
	/**
	 * List of Nodes of the Graph.
	 */
	private NodesList nodes;

	public Plan generate(PartialObjectiveFunction objectiveFunction, Plan currentPlan,
			Map<Field, Category> preferredSubjects) {
		Map<Plan, NodesList> planFamily;
		Iterator<Plan> it;
		Plan plan = currentPlan;
		// first generation of family of plans with change of all randomly added
		// nodes
		planFamily = randomlyGeneratedFamilyOfPlans(nodes, plan, preferredSubjects, -1);
		it = planFamily.keySet().iterator();
		plan = new Plan();
		plan.setUser(currentPlan.getUser());
		if (it.hasNext()) {
			plan = it.next();
		}
		while (it.hasNext()) {
			Plan nextPlan = it.next();
			if (objectiveFunction.evaluate(nextPlan) > objectiveFunction.evaluate(plan)) {
				plan = nextPlan;
			}
		}
		// Save the nodes list from which this plan was created
		NodesList planNodesList;
		for (int i = 0; i < 5; i++) {
			planNodesList = planFamily.get(plan);
			planFamily = randomlyGeneratedFamilyOfPlans(planNodesList, plan, preferredSubjects, 10);
			it = planFamily.keySet().iterator();
			plan = new Plan();
			plan.setUser(currentPlan.getUser());
			if (it.hasNext()) {
				plan = it.next();
			}
			while (it.hasNext()) {
				Plan nextPlan = it.next();
				if (objectiveFunction.evaluate(nextPlan) > objectiveFunction.evaluate(plan)) {
					plan = nextPlan;
				}
			}
		}
		return currentPlan;
	}

	/**
	 * Adds the modules needed to reach the required credit points for the given
	 * field.
	 * 
	 * @param field
	 *            which modules are being added
	 * @param category
	 *            preferred to get the preferred modules
	 * @param currentPlan
	 *            the plan being generated
	 */
	private void addFieldModules(Field field, Category category, Plan currentPlan) {
		int creditPoints = nodes.getCreditPoints(field);
		if (creditPoints >= field.getMinEcts()) {
			return;
		}
		List<Module> preferredModules;
		// set of random numbers to choose modules randomly from the list
		Set<Integer> randomNumbers;
		// to iterate through the set above
		preferredModules = getModulesWithPreference(currentPlan, field.getModules(), category, PreferenceType.POSITIVE);
		randomNumbers = getRandomNumbers(preferredModules.size(), preferredModules.size());
		// Iterator to iterate through the set above
		Iterator<Integer> it = randomNumbers.iterator();
		// add modules from preferred modules in the category chosen
		while (creditPoints < field.getMinEcts() && it.hasNext()) {
			Node node = new NodeWithOutput(preferredModules.get(it.next()));
			if(nodes.addToAllNodes(node)) {
				nodes.getRandomlyAddedNodes().add(node);
				node.fulfillConstraints(nodes, true);
			}
			creditPoints += node.getModule().getCreditPoints();
		}
		if (creditPoints >= field.getMinEcts()) {
			return;
		}
		List<Module> notEvaluatedModules = getModulesWithPreference(currentPlan, field.getModules(), category, null);
		randomNumbers = getRandomNumbers(notEvaluatedModules.size(), notEvaluatedModules.size());
		it = randomNumbers.iterator();
		/*
		 * if preferred modules do not reach the credit points needed add
		 * modules from not evaluated modules in the category chosen
		 */
		while (creditPoints < field.getMinEcts() && it.hasNext()) {
				Node node = new NodeWithOutput(notEvaluatedModules.get(it.next()));
				if(nodes.addToAllNodes(node)) {
					nodes.getRandomlyAddedNodes().add(node);
					node.fulfillConstraints(nodes, true);
				}
				creditPoints += node.getModule().getCreditPoints();
		}
		if (creditPoints >= field.getMinEcts()) {
			return;
		}

		if (creditPoints < field.getMinEcts()) {
			throw new IllegalArgumentException("CreditPoints of the Category " + category.getName() + " with id "
					+ category.getCategoryId() + " < minECTS of field " + field.getName());
		}
	}

	/**
	 * Generates a complete Plan with random Modules
	 * 
	 * @param nodes
	 * @param currentPlan
	 * @param preferredSubjects
	 */

	private Plan randomlyGeneratedPlan(Plan currentPlan, Map<Field, Category> preferredSubjects) {
		//adding modules of the rule groups of the discipline
		List<RuleGroup> ruleGroups = currentPlan.getUser().getDiscipline().getRuleGroups();
		for (RuleGroup ruleGroup : ruleGroups) {
			addRuleGroupModules(ruleGroup, currentPlan, preferredSubjects.get(ruleGroup));
		}
		//adding modules of the fields of the discipline
		List<Field> fields = currentPlan.getUser().getDiscipline().getFields();
		for (Field field : fields) {
			addFieldModules(field, preferredSubjects.get(field), currentPlan);
		}
		return parallelize(nodes.sort());
	}

	/**
	 * Adds the modules needed to reach the required number of modules that
	 * belong to the given rule group.
	 * 
	 * @param ruleGroup
	 *            which modules are being added
	 * @param category
	 *            preferred to get the preferred modules
	 * @param currentPlan
	 *            the plan being generated
	 */
	private void addRuleGroupModules(RuleGroup ruleGroup, Plan plan, Category category) {
		int num = nodes.numNodesInRuleGroup(ruleGroup);
		if (num >= ruleGroup.getMinNum() && num <= ruleGroup.getMinNum()) {
			return;
		}
		List<Module> preferredModules;
		// set of random numbers to choose modules randomly from the list
		Set<Integer> randomNumbers;
		// to iterate through the set above
		preferredModules = getModulesWithPreference(plan, ruleGroup.getModules(), category, PreferenceType.POSITIVE);
		randomNumbers = getRandomNumbers(preferredModules.size(), preferredModules.size());
		// to iterate through the set above
		Iterator<Integer> it = randomNumbers.iterator();
		while(num > ruleGroup.getMaxNum()) {
			
		}
		while (num < ruleGroup.getMinNum() && it.hasNext()) {
			Node node = new NodeWithOutput(preferredModules.get(it.next()), plan);
			if(nodes.addToAllNodes(node)) {
				nodes.getRandomlyAddedNodes().add(node);
				node.fulfillConstraints(nodes, true);
			}
			num += 1;
		}
		List<Module> notEvaluatedModules = getModulesWithPreference(plan, ruleGroup.getModules(), category, null);
		randomNumbers = getRandomNumbers(notEvaluatedModules.size(), notEvaluatedModules.size());
		it = randomNumbers.iterator();
		/*
		 * if preferred modules do not reach the credit points needed add
		 * modules from not evaluated modules in the category chosen
		 */
		while (num < ruleGroup.getMinNum() && it.hasNext()) {
			Node node = new NodeWithOutput(notEvaluatedModules.get(it.next()), plan);
			if(nodes.addToAllNodes(node)) {
				nodes.getRandomlyAddedNodes().add(node);
				node.fulfillConstraints(nodes, true);
			}
			num += 1;
		}
	}

	/**
	 * Parallelize the sorted stack of nodes given and transform it to a plan.
	 * 
	 * @param stack
	 *            topologically sorted stack of nodes
	 * @return the new plan
	 */
	private Plan parallelize(Stack<Node> stack) {
		return null;
	}
	/**
	 * 
	 * @param node1
	 * @param node2
	 * @return
	 */
	private boolean checkIfOverlapping(Node node1, Node node2) {
		for(ModuleConstraint constraint: node1.getModule().getConstraints()) {
			if(node1.getRemainingModuleFromConstraint(constraint).getIdentifier() == node2.getModule().getIdentifier()) {
				return true;
			}
		}
		return false;
	}
	

	/**
	 * Generates a family of plans with random Modules
	 * 
	 * @param nodes
	 * @param currentPlan
	 * @param preferredSubjects
	 * @param numberOfNodesToChange
	 * @return a mapping of the generated plan and the nodes from which they
	 *         were generated
	 */
	private Map<Plan, NodesList> randomlyGeneratedFamilyOfPlans(NodesList nodes, Plan currentPlan,
			Map<Field, Category> preferredSubjects, int numberOfNodesToChange) {
		Map<Plan, NodesList> planFamily = new HashMap<Plan, NodesList>();
		planToGraph(currentPlan);
		Plan plan = randomlyGeneratedPlan(currentPlan, preferredSubjects);
		planFamily.put(plan, nodes);
		for (int i = 0; i < 9; i++) {
			if (numberOfNodesToChange == -1) {
				numberOfNodesToChange = nodes.getRandomlyAddedNodes().size();
			}
			plan = randomlyModifiedPlan(numberOfNodesToChange, plan, preferredSubjects);
			planFamily.put(plan, nodes);
		}
		return planFamily;
	}

	/**
	 * Transforms a Plan given to a graph with: - creating Nodes to represent
	 * all ModulEntries - fulfilling all constraints of the nodes (except the
	 * passed modules) - add nodes to the nodes attribute
	 * 
	 * @param plan
	 *            the plan to use
	 */

	public void planToGraph(Plan plan) {
		nodes = new NodesList(plan);
		Node node;
		// Create a Node for every ModuleEntry and add it to the list of nodes
		for (int i = 0; i < plan.getModuleEntries().size(); i++) {
			Module m = plan.getModuleEntries().get(i).getModule();
			node = nodes.getFromAllNodes(m);
			if (nodes.contains(node)) {
				throw new IllegalArgumentException("Two Module Entries with the same Module in "
						+ "the Plan");
			}
			if (node == null) {
				node = new NodeWithOutput(m, plan);
				node.setSemester(plan.getModuleEntries().get(i).getSemester());
				nodes.addNode(node);
			} else {
				nodes.add(node);
			}
			node.fulfillConstraints(nodes, false);
		}
	}

	/**
	 * Returns a set of i random numbers that are < max.
	 * 
	 * @param max
	 *            maximum of numbers needed
	 * @param i
	 *            number of Integers needed
	 * @return set of i random numbers that are < max.
	 */
	private Set<Integer> getRandomNumbers(int max, int i) {
		Set<Integer> generated = new LinkedHashSet<Integer>();
		Random rand = new Random();
		while (generated.size() < i) {
			Integer next = rand.nextInt(max);
			generated.add(next);
		}
		return generated;
	}

	/**
	 * Returns a list of the modules that belong to the list and category given
	 * and that has the preference given. If the category is null (the field is
	 * not choosable) this method would search through all modules in the list
	 * given.
	 * 
	 * @param currentPlan
	 *            the plan being generated.
	 * @param field
	 *            the field which modules are needed.
	 * @param category
	 *            the category chosen.
	 * @return the list of preferred modules.
	 */
	private List<Module> getModulesWithPreference(Plan currentPlan, List<Module> listOfModules, Category category,
			PreferenceType preference) {
		List<Module> modules = new ArrayList<Module>();
		if (category != null) {
			Filter filter = new CategoryFilter(category.getCategoryId(), currentPlan.getUser().getDiscipline());
			for (Module m : moduleDAO.getModulesByFilter(filter, currentPlan.getUser().getDiscipline())) {
				if ((currentPlan.getPreferenceForModule(m) == preference) && listOfModules.contains(m)) {
					modules.add(m);
				}
			}
		} else {
			for (Module m : listOfModules) {
				if ((currentPlan.getPreferenceForModule(m) == preference)) {
					modules.add(m);
				}
			}
		}
		return modules;
	}

	/**
	 * Modifies a plan given with changing a given number of previously randomly
	 * added nodes and returns the new plan.
	 * 
	 * @param numberOfNodes
	 *            number of nodes to change
	 * @param plan
	 *            the plan to modify
	 * @param preferredSubjects
	 * @return the new plan
	 */
	private Plan randomlyModifiedPlan(int numberOfNodes, Plan plan, Map<Field, Category> preferredSubjects) {
		Set<Integer> randomNumbers = getRandomNumbers(nodes.getRandomlyAddedNodes().size(),
				Math.min(numberOfNodes, nodes.getRandomlyAddedNodes().size()));
		Iterator<Integer> it = randomNumbers.iterator();
		while (it.hasNext()) {
			nodes.removeNode(nodes.getRandomlyAddedNodes().get(it.next()));
		}
		return randomlyGeneratedPlan(plan, preferredSubjects);
	}

}
