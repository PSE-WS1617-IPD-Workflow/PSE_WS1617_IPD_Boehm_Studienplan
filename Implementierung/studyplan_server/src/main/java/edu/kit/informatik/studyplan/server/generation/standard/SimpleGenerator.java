package edu.kit.informatik.studyplan.server.generation.standard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import edu.kit.informatik.studyplan.server.filter.CategoryFilter;
import edu.kit.informatik.studyplan.server.filter.Filter;
import edu.kit.informatik.studyplan.server.generation.Generator;
import edu.kit.informatik.studyplan.server.generation.objectivefunction.PartialObjectiveFunction;
import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.RuleGroup;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.PrerequisiteModuleConstraintType;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDao;
import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.model.userdata.PreferenceType;
import edu.kit.informatik.studyplan.server.model.userdata.User;
import edu.kit.informatik.studyplan.server.model.userdata.VerificationState;
import edu.kit.informatik.studyplan.server.verification.Verifier;
import edu.kit.informatik.studyplan.server.verification.standard.StandardVerifier;

/**
 * The SimpleGenerator Class is a concrete Generator that implements the
 * Generator Interface. It uses a graph structure to sort and parallelize
 * modules, and an objective Function to evaluate the plans.
 * 
 * @author Nada_Chatti
 * @version 1.0
 */
public class SimpleGenerator implements Generator {

	/**
	 * List of Nodes of the Graph to create.
	 */
	private NodesList nodes;

	/**
	 * @return the nodes of the graph to create
	 */
	public NodesList getNodes() {
		return nodes;
	}

	public Plan generate(PartialObjectiveFunction objectiveFunction, final Plan currentPlan, ModuleDao moduleDAO,
			Map<Field, Category> preferredSubjects, int maxSemesterEcts) {
		Map<Plan, NodesList> planFamily;
		Iterator<Plan> it;
		Verifier verifier = new StandardVerifier();
		Plan plan = currentPlan;
		// first generation of family of plans with change of all randomly added
		// nodes
		planToGraph(currentPlan);
		planFamily = randomlyGeneratedFamilyOfPlans(nodes, plan, preferredSubjects, -1, maxSemesterEcts, moduleDAO);
		it = planFamily.keySet().iterator(); // iterates through all plans
												// created
		plan = new Plan();
		plan.setUser(currentPlan.getUser());
		if (it.hasNext()) {
			plan = it.next();
		}
		if (verifier.verify(plan).equals(VerificationState.INVALID)) {
			// no valid plan could be created thus return an invalid empty plan
			plan.setVerificationState(VerificationState.INVALID);
			plan = new Plan();
			plan.setUser(currentPlan.getUser());
			return plan;
		}
		// // search for a valid plan
		// while(it.hasNext()) {
		// Plan nextPlan = it.next();
		// if(verifier.verify(plan).equals(VerificationState.INVALID)) {
		// plan = nextPlan;
		// }
		// }
		// if(verifier.verify(plan).equals(VerificationState.INVALID)) {
		// // no valid plan could be created thus return an invalid plan
		// return plan;
		// }
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
			planFamily = randomlyGeneratedFamilyOfPlans(planNodesList, plan, preferredSubjects, 10, maxSemesterEcts,
					moduleDAO);
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
		plan.getPreferences().addAll(currentPlan.getPreferences());
		plan.setVerificationState(VerificationState.VALID);
		return plan;
	}

	/**
	 * Adds the modules needed to reach the required credit points for the given
	 * field.
	 * 
	 * @param field
	 *            which modules are being added
	 * @param category
	 *            preferred to get the modules from (if field is not choosable
	 *            this parameter would be null)
	 * @param currentPlan
	 *            the plan being generated
	 */
	void addFieldModules(Field field, Category category, Plan currentPlan, ModuleDao moduleDAO) {
		double creditPoints = nodes.getCreditPoints(field);
		if (creditPoints >= field.getMinEcts()) {
			return;
		}

		List<Module> modules;
		// set of random numbers to choose modules randomly from the list
		Set<Integer> randomNumbers;
		// to iterate through the set above
		modules = getModulesWithPreference(currentPlan, field.getModules(), category, PreferenceType.POSITIVE,
				moduleDAO);
		randomNumbers = randomNumbers(modules.size(), modules.size());
		// Iterator to iterate through the set above
		Iterator<Integer> it = randomNumbers.iterator();
		// add modules from preferred modules in the category chosen
		while (creditPoints < field.getMinEcts() && it.hasNext()) {
			Node node = new NodeWithOutput(modules.get(it.next()), currentPlan, this);
			if (nodes.add(node, true)) {
				node.fulfillConstraints(true);
			}
			creditPoints = nodes.getCreditPoints(field);
		}
		if (creditPoints >= field.getMinEcts()) {
			return;
		}

		/*
		 * if preferred modules do not reach the credit points needed add
		 * modules from the not evaluated modules in the category chosen
		 */
		modules = getModulesWithPreference(currentPlan, field.getModules(), category, null, moduleDAO);
		randomNumbers = randomNumbers(modules.size(), modules.size());
		it = randomNumbers.iterator();
		while (creditPoints < field.getMinEcts() && it.hasNext()) {
			Node node = new NodeWithOutput(modules.get(it.next()), currentPlan, this);
			if (nodes.add(node, true)) {
				node.fulfillConstraints(true);
			}
			creditPoints = nodes.getCreditPoints(field);
		}
		if (creditPoints >= field.getMinEcts()) {
			return;
		}

		/*
		 * if preferred and not evaluated modules do not reach the credit points
		 * needed add modules from the negatively evaluated modules in the
		 * category chosen
		 */
		modules = getModulesWithPreference(currentPlan, field.getModules(), category, PreferenceType.NEGATIVE,
				moduleDAO);
		randomNumbers = randomNumbers(modules.size(), modules.size());
		it = randomNumbers.iterator();
		while (creditPoints < field.getMinEcts() && it.hasNext()) {
			Node node = new NodeWithOutput(modules.get(it.next()), currentPlan, this);
			if (nodes.add(node, true)) {
				node.fulfillConstraints(true);
			}
			creditPoints = nodes.getCreditPoints(field);
		}
		if (creditPoints >= field.getMinEcts()) {
			return;
		}
		List<Module> list = field.getModules().stream()
				.filter(m -> !nodes.stream().map(node -> node.getModule()).collect(Collectors.toList()).contains(m))
				.collect(Collectors.toList());

		/*
		 * if modules in the category given do not reach the credit points
		 * needed add modules from the rest of the modules in the field
		 */
		modules = field.getModules();
		randomNumbers = randomNumbers(modules.size(), modules.size());
		it = randomNumbers.iterator();
		while (creditPoints < field.getMinEcts() && it.hasNext()) {
			Node node = new NodeWithOutput(modules.get(it.next()), currentPlan, this);
			if (nodes.add(node, true)) {
				node.fulfillConstraints(true);
			}
			creditPoints = nodes.getCreditPoints(field);
		}

		if (creditPoints >= field.getMinEcts()) {
			return;
		}
		if (creditPoints < field.getMinEcts()) {
			throw new IllegalArgumentException("CreditPoints of the Category < minECTS of " + "field");
		}
	}

	/**
	 * Generates a complete Plan with random Modules
	 * 
	 * @param nodes
	 *            the nodes list to generate plan from.
	 * @param plan
	 *            plan from which a new plan is being generated.
	 * @param preferredSubjects
	 *            a mapping of the categories chosen for each field
	 * @param maxECTSperSemester
	 *            maximum amount of credit points per semester
	 * @param moduleDAO
	 *            the moduleDao used to fetch modules
	 * @return a Map containing only one key (the plan generated) and one value
	 *         (the nodesList from which the plan was generated) for later
	 *         modification.
	 */
	GenerationResult complete(NodesList nodes, Plan plan, Map<Field, Category> preferredSubjects,
			int maxECTSperSemester, ModuleDao moduleDAO) {
		// adding modules of the rule groups of the discipline
		List<RuleGroup> ruleGroups = plan.getUser().getDiscipline().getRuleGroups();
		for (RuleGroup ruleGroup : ruleGroups) {
			addRuleGroupModules(ruleGroup, plan, preferredSubjects.get(ruleGroup), moduleDAO);
		}
		// adding modules of the fields of the discipline
		List<Field> fields = plan.getUser().getDiscipline().getFields();
		for (Field field : fields) {
			addFieldModules(field, preferredSubjects.get(field), plan, moduleDAO);
		}
		List<Node> sorted = nodes.sort();
		GenerationResult result = new GenerationResult(
				createPlan(sorted, parallelize(sorted, maxECTSperSemester), plan.getUser()), nodes);
		return result;
	}

	/**
	 * Creates a plan based on the sorted list of nodes and the array of
	 * semester allocation given.
	 * 
	 * @param sorted
	 *            the sorted list of nodes based on which the plan would be
	 *            created.
	 * @param semesterAllocation
	 *            an array of the number of the semester allocated to each node
	 *            of the list
	 * @param user
	 *            to set the new plan's user
	 * @return the created plan
	 */
	Plan createPlan(List<Node> sorted, int[] semesterAllocation, User user) {
		Plan plan = new Plan();
		for (int i = 0; i < sorted.size(); i++) {
			ModuleEntry entry = new ModuleEntry(sorted.get(i).getModule(), semesterAllocation[i]);
			plan.getModuleEntries().add(entry);
			Node n = sorted.get(i);
			while (n.hasInnerNode()) {
				n = n.getInnerNode();
				plan.getModuleEntries().add(new ModuleEntry(n.getModule(), semesterAllocation[i]));
			}
			n = sorted.get(i);
			while (n.hasOuterNode()) {
				n = n.getOuterNode();
				plan.getModuleEntries().add(new ModuleEntry(n.getModule(), semesterAllocation[i]));
			}

		}
		plan.setUser(user);
		return plan;
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
	 *            the plan given originally to the generator
	 * @param moduleDAO
	 *            the moduleDao used to fetch modules
	 */
	void addRuleGroupModules(RuleGroup ruleGroup, Plan currentPlan, Category category, ModuleDao moduleDAO) {
		int num = nodes.nodesInRuleGroup(ruleGroup).size();
		if (num >= ruleGroup.getMinNum() && num <= ruleGroup.getMaxNum()) {
			return;
		}
		List<Module> modules;
		// set of random numbers to choose modules randomly from the list
		Set<Integer> randomNumbers;
		// to iterate through the set above
		modules = getModulesWithPreference(currentPlan, ruleGroup.getModules(), category, PreferenceType.POSITIVE,
				moduleDAO);
		// to iterate through the set above
		Iterator<Integer> it;
		if (num > ruleGroup.getMaxNum()) {
			randomNumbers = randomNumbers(num, num);
			it = randomNumbers.iterator();
		}
		if (ruleGroup.getMaxNum() != -1) {
			while (num > ruleGroup.getMaxNum()) {
				if (!nodes.nodesInRuleGroup(ruleGroup).isEmpty()) {
					nodes.remove(nodes.nodesInRuleGroup(ruleGroup).get(0));
				}
			}
		}
		randomNumbers = randomNumbers(modules.size(), modules.size());
		it = randomNumbers.iterator();
		while (num < ruleGroup.getMinNum() && it.hasNext()) {
			Node node = new NodeWithOutput(modules.get(it.next()), currentPlan, this);
			if (nodes.add(node, true)) {
				node.fulfillConstraints(true);
			}
			num += 1;
		}
		/*
		 * if preferred modules do not reach the credit points needed add
		 * modules from not evaluated modules in the category chosen
		 */
		modules = getModulesWithPreference(currentPlan, ruleGroup.getModules(), category, null, moduleDAO);
		randomNumbers = randomNumbers(modules.size(), modules.size());
		it = randomNumbers.iterator();
		while (num < ruleGroup.getMinNum() && it.hasNext()) {
			Node node = new NodeWithOutput(modules.get(it.next()), currentPlan, this);
			if (nodes.add(node, true)) {
				node.fulfillConstraints(true);
			}
			num += 1;
		}
	}

	/**
	 * Parallelize the sorted list of nodes given and transform it to a plan.
	 * That means that it spreads the nodes so that they can fit in respective
	 * semesters to create a graph.
	 * 
	 * @param sorted
	 *            topologically sorted list of nodes
	 * @param maxECTSperSemester
	 *            maximum amount of credit points per semester
	 * @return an array containing the number of the semester allocated to each
	 *         node
	 */
	int[] parallelize(List<Node> sorted, int maxECTSperSemester) {
		WeightFunction weight = new WeightFunction();
		Node node;
		boolean set;
		int[] bucketAllocation = new int[sorted.size()];
		int[] bucketSum = new int[sorted.size()];
		int[] minPos = new int[sorted.size()];
		for (int i = 0; i < minPos.length; i++) {
			minPos[i] = 1;
		}
		for (int i = 0; i < sorted.size(); i++) {
			node = sorted.get(i);
			set = false;
			// Check if a node is already fixed in a semester
			if (node.getSemester() != 0) {
				bucketAllocation[i] = node.getSemester();
				bucketSum[node.getSemester() - 1] += weight.getWeight(node);
				for (Node child : node.getChildren()) {
					if ((child.getConstraint(node) != null)
							&& (child.getConstraint(node)
									.getConstraintType() instanceof PrerequisiteModuleConstraintType)
							&& sorted.contains(child)) {
						minPos[sorted.indexOf(child)] = Math.max(node.getSemester() + 1, minPos[sorted.indexOf(child)]);
					}
				}
				set = true;
			} else {
				for (int j = minPos[i]; j < sorted.size(); j++) {
					if (weight.getWeight(node) + bucketSum[j - 1] <= maxECTSperSemester
							&& checkIfOverlapping(node, bucketAllocation, sorted, j - 1) && node.fitsInSemester(j)) {
						bucketAllocation[i] = j;
						bucketSum[j - 1] += weight.getWeight(node);
						for (Node child : node.getChildren()) {
							if ((child.getConstraint(node) != null)
									&& (child.getConstraint(node)
											.getConstraintType() instanceof PrerequisiteModuleConstraintType)
									&& sorted.contains(child)) {
								minPos[sorted.indexOf(child)] = Math.max(j + 1, minPos[sorted.indexOf(child)]);
							}
						}
						set = true;
						break;
					}

				}
			}
			if (!set) {
				throw new IllegalArgumentException("Node" + node + "and its inner nodes "
						+ "have too many Credit Points for a single Semester" + weight.getWeight(node));
			}
		}
		return bucketAllocation;
	}

	/**
	 * Checks if there is a node, that has a constraint from type overlapping
	 * with the node given, in the semester with the number given.
	 * 
	 * @param node
	 *            the node concerned
	 * @param bucketAllocation
	 *            an array containing the number of the semester allocated to
	 *            each node with index i in the sorted list
	 * @param sorted
	 *            the sorted list of nodes
	 * @param semesterNum
	 *            number of the semester concerned
	 * @return -true if there is a node that has a constraint from type
	 *         overlapping with the node given in the semester with the number
	 *         given, -false if not.
	 */
	private boolean checkIfOverlapping(Node node, int[] bucketAllocation, List<Node> sorted, int semesterNum) {
		for (Node n : nodes.getOverlappingNodes(node)) {
			if (bucketAllocation[sorted.indexOf(n)] == semesterNum) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Generates a family of plans with random Modules
	 * 
	 * @param nodes
	 *            the nodes list to generate plan from.
	 * @param currentPlan
	 *            plan from which a new plan is being generated.
	 * @param preferredSubjects
	 *            a mapping of the categories chosen for each field
	 * @param numberOfNodesToChange
	 *            the number of nodes to change in the random modification phase
	 * @param maxECTSperSemester
	 *            maximum amount of credit points per semester
	 * @param moduleDAO
	 *            the moduleDao used to fetch modules
	 */
	Map<Plan, NodesList> randomlyGeneratedFamilyOfPlans(NodesList nodes, Plan currentPlan,
			Map<Field, Category> preferredSubjects, int numberOfNodesToChange, int maxECTSperSemester,
			ModuleDao moduleDAO) {
		Map<Plan, NodesList> planFamily = new HashMap<Plan, NodesList>();
		GenerationResult generated = complete(nodes, currentPlan, preferredSubjects, maxECTSperSemester, moduleDAO);
		planFamily.put(generated.getPlan(), generated.getNodesList());
		for (int i = 0; i < 9; i++) {
			if (numberOfNodesToChange == -1) {
				numberOfNodesToChange = nodes.getRandomlyAddedNodes().size();
			}
			GenerationResult modified = modify(numberOfNodesToChange, generated, preferredSubjects, maxECTSperSemester,
					moduleDAO);
			planFamily.put(modified.getPlan(), modified.getNodesList());
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
		nodes = new NodesList(plan, this);
		Node node;
		// Create a Node for every ModuleEntry and add it to the list of nodes
		for (int i = 0; i < plan.getModuleEntries().size(); i++) {
			Module m = plan.getAllModuleEntries().get(i).getModule();
			node = nodes.get(m);
			if (node == null) {
				node = new NodeWithOutput(m, plan, this);
				node.setSemester(plan.getModuleEntries().get(i).getSemester());
				nodes.add(node, false);
			} else {
				nodes.add(node, false);
			}
			node.fulfillConstraints(false);
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
	Set<Integer> randomNumbers(int max, int i) {
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
	List<Module> getModulesWithPreference(Plan currentPlan, List<Module> listOfModules, Category category,
			PreferenceType preference, ModuleDao moduleDAO) {
		List<Module> modules = new ArrayList<Module>();
		if (category != null) {
			Filter filter = new CategoryFilter(category);
			for (Module m : moduleDAO.getModulesByFilter(filter, currentPlan.getUser().getDiscipline())) {
				if (((currentPlan.getPreferenceForModule(m) == preference) && listOfModules.contains(m))
						|| (preference == null && listOfModules.contains(m))) {
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
	 * @param generated
	 *            a pair of the plan and the nodeslist the plan was created from
	 * @param preferredSubjects
	 * @return the new plan
	 */
	private GenerationResult modify(int numberOfNodes, GenerationResult generated,
			Map<Field, Category> preferredSubjects, int maxECTSperSemester, ModuleDao moduleDAO) {

		NodesList nodes = generated.getNodesList();
		Set<Integer> randomNumbers = randomNumbers(nodes.getRandomlyAddedNodes().size(),
				Math.min(numberOfNodes, nodes.getRandomlyAddedNodes().size()));
		Iterator<Integer> it = randomNumbers.iterator();
		Node[] ranAdded = new Node[nodes.getRandomlyAddedNodes().size()];
		nodes.getRandomlyAddedNodes().toArray(ranAdded);
		while (it.hasNext()) {
			int i = it.next();
			if (nodes.contains(ranAdded[i])) {
				nodes.remove(ranAdded[i]);
			}
		}
		return complete(nodes, generated.getPlan(), preferredSubjects, maxECTSperSemester, moduleDAO);
	}

}
