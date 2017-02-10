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
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDao;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDaoFactory;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.model.userdata.PreferenceType;

/************************************************************/
/**
 * Die Klasse SimpleGenerator, welche Generator implementiert, stellt einen
 * konkreten Generator da. Dieser nutzt die Graphenstruktur, die Zielfunktion
 * und die WeightFunction.
 */
public class SimpleGenerator implements Generator {
	private ModuleDao moduleDAO = ModuleDaoFactory.getModuleDao();
	private NodesList nodes;
	public Plan generate(PartialObjectiveFunction objectiveFunction, Plan currentPlan,
			Map<Field, Category> preferredSubjects) {
		Map<Plan, NodesList> planFamily;
		Iterator<Plan> it;
		Plan plan = currentPlan;
		//first generation of family of plans with change of all randomly added nodes
		planFamily = randomlyGeneratedFamilyOfPlans(plan, preferredSubjects, -1);
		it = planFamily.keySet().iterator();
		plan = new Plan();
		if (it.hasNext()) {
			plan = it.next();
		}
		while(it.hasNext()) {
			Plan nextPlan = it.next();
			if(objectiveFunction.evaluate(nextPlan) > objectiveFunction.evaluate(plan)) {
				plan = nextPlan;
			}
		}
		for(int i = 0; i < 5; i++){
			planFamily = randomlyGeneratedFamilyOfPlans(plan, preferredSubjects, 10);
			it = planFamily.keySet().iterator();
			plan = new Plan();
			if (it.hasNext()) {
				plan = it.next();
			}
			while(it.hasNext()) {
				Plan nextPlan = it.next();
				if(objectiveFunction.evaluate(nextPlan) > objectiveFunction.evaluate(plan)) {
					plan = nextPlan;
				}
			}
		}
		return currentPlan;
	}

	/**
	 * returns a sorted list of the nodes of the list given
	 * 
	 * @param nodes
	 *            list of nodes
	 * @return a list of sorted nodes
	 */
	private Stack<Node> sort(NodesList nodes) {
		Stack<Node> stack = new Stack<Node>();
		Set<Node> visited = new LinkedHashSet<Node>();
		for (Node n : nodes.getAllNodes()) {
			if (visited.contains(n)) {
				sortUtil(stack, n, visited);
			}
		}
		return stack;
	}

	/**
	 * Adds a child to the stack recursively
	 * 
	 * @param stack
	 *            the stack
	 * @param node
	 *            the node to begin with
	 * @param visited
	 *            set of visited nodes
	 */
	private void sortUtil(Stack<Node> stack, Node node, Set<Node> visited) {
		visited.add(node);
		ArrayList<Node> children = node.getChildren();
		int j = 0;
		while (j < children.size()) {
			if (!visited.contains(children.get(j))) {
				sortUtil(stack, children.get(j), visited);
			}
			j++;
		}
		stack.push(node);

	}

	private void addFieldModules(Field field, Category category, Plan currentPlan) {
		int creditPoints = nodes.getCreditPoints(field);
		if (creditPoints >= field.getMinEcts()) {
			return;
		}
		List<Module> preferredModules;
		// set of random numbers to choose modules randomly from the list
		Set<Integer> randomNumbers;
		// to iterate through the set above
		Iterator<Integer> it;
		preferredModules = getModulesWithPreference(currentPlan, field, category, PreferenceType.POSITIVE);
		randomNumbers = getRandomNumbers(preferredModules.size(), Math.min(preferredModules.size(), 10)); // 10
																											// is
																											// randomly
																											// chosen
		it = randomNumbers.iterator();
		int i = 0;
		while (creditPoints < field.getMinEcts() && i < preferredModules.size()) {
			if (it.hasNext()) {
				Node node = new NodeWithOutput(preferredModules.get(it.next()));
				creditPoints += node.getModule().getCreditPoints();
				nodes.getRandomlyAddedNodes().add(node);
				nodes.addToAllNodes(node);
				node.fulfillConstraints(nodes, true);
			}
		}
		if (creditPoints >= field.getMinEcts()) {
			return;
		}
		List<Module> notEvaluatedModules = getModulesWithPreference(currentPlan, field, category, null);
		randomNumbers = getRandomNumbers(notEvaluatedModules.size(), Math.min(notEvaluatedModules.size(), 10));
		while (creditPoints < field.getMinEcts() && i < notEvaluatedModules.size()) {
			if (it.hasNext()) {
				Node node = new NodeWithOutput(notEvaluatedModules.get(it.next()));
				nodes.getRandomlyAddedNodes().add(node);
				nodes.addToAllNodes(node);
				node.fulfillConstraints(nodes, true);
			}
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
		List<Field> fields = currentPlan.getUser().getDiscipline().getFields();
		for (Field field : fields) {
			addFieldModules(field, preferredSubjects.get(field), currentPlan);
		}
		sort(nodes);
		return parallelize();
	}

	private Plan parallelize() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Generates a family of plans with random Modules
	 * 
	 * @param nodes
	 * @param currentPlan
	 * @param preferredSubjects
	 */
	private Map<Plan, NodesList> randomlyGeneratedFamilyOfPlans(Plan currentPlan, 
			Map<Field, Category> preferredSubjects, int numberOfNodesToChange) {
		Map<Plan,NodesList> planFamily = new HashMap<Plan,NodesList>();
		planToGraph(currentPlan);
		Plan plan =  randomlyGeneratedPlan(currentPlan, preferredSubjects);
		planFamily.put(plan, nodes);
		for (int i = 0; i < 9; i++) {
			if(numberOfNodesToChange == -1) {
				numberOfNodesToChange = nodes.getRandomlyAddedNodes().size();
			}
			plan = randomlyModifiedPlan(numberOfNodesToChange, plan, nodes, preferredSubjects);
			planFamily.put(plan, nodes);
		}
		return planFamily;
	}

	/**
	 * @return the graph
	 */

	public void planToGraph(Plan plan) {
		nodes = new NodesList(plan);
		Node node;
		// Create a Node for every ModuleEntry and add it to the list of nodes
		for (int i = 0; i < plan.getModuleEntries().size(); i++) {
			Module m = plan.getModuleEntries().get(i).getModule();
			node = nodes.getFromAllNodes(m);
			if (nodes.contains(node)) {
				throw new IllegalArgumentException("Two Module Entries with the same Module in the Plan");
			}
			if (node == null) {
				node = new NodeWithOutput(m, plan);
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
	 * Returns a list of the modules that belong to the field and category given
	 * and that has the preference given.
	 * 
	 * @param currentPlan
	 *            the plan being generated.
	 * @param field
	 *            the field which modules are needed.
	 * @param category
	 *            the category chosen, if the category is null, it means that
	 *            the field is not choosable.
	 * @return the list of preferred modules.
	 */
	private List<Module> getModulesWithPreference(Plan currentPlan, Field field, Category category,
			PreferenceType preference) {
		List<Module> modules = new ArrayList<Module>();
		if (category != null) {
			Filter filter = new CategoryFilter(category.getCategoryId(), currentPlan.getUser().getDiscipline());
			for (Module m : moduleDAO.getModulesByFilter(filter, currentPlan.getUser().getDiscipline())) {
				if ((currentPlan.getPreferenceForModule(m) == preference) && m.getField().equals(field)) {
					modules.add(m);
				}
			}
		} else {
			for (Module m : field.getModules()) {
				if ((currentPlan.getPreferenceForModule(m) == preference) && m.getField().equals(field)) {
					modules.add(m);
				}
			}
		}
		return modules;
	}

	private Plan randomlyModifiedPlan(int numberOfNodes, Plan plan, NodesList nodes, Map<Field, Category> preferredSubjects) {
		Set<Integer> randomNumbers = getRandomNumbers(nodes.getRandomlyAddedNodes().size(),
				Math.min(numberOfNodes, nodes.getRandomlyAddedNodes().size()));
		Iterator<Integer> it = randomNumbers.iterator();
		while (it.hasNext()) {
			nodes.removeNode(nodes.getRandomlyAddedNodes().get(it.next()));
		}
		return randomlyGeneratedPlan(plan, preferredSubjects);
	}

}
