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
import edu.kit.informatik.studyplan.server.model.userdata.Semester;
import edu.kit.informatik.studyplan.server.model.userdata.User;
import edu.kit.informatik.studyplan.server.model.userdata.VerificationState;
import edu.kit.informatik.studyplan.server.verification.Verifier;
import edu.kit.informatik.studyplan.server.verification.standard.StandardVerifier;

/**
 * The SimpleGenerator Class is a concrete Generator that implements the
 * Generator Interface. It uses a graph structure to sort modules and allocate
 * them to semesters, and an objective Function to evaluate the plans.
 * 
 * @author Nada_Chatti
 * @version 1.0
 */
public class SimpleGenerator implements Generator {
	
	final private int CHANGE_ALL_NODES = -1;
	
	final private int NUM_NODES_TO_CHANGE = 10;
	
	final private int NUM_PLANS_TO_CREATE = 5;


	/**
	 * The maximum number of credit points per semester specified by the user.
	 */
	private double maxECTSperSemester;
	/**
	 * The minimum number of credit points per semester specified by the user.
	 */
	private double minECTSperSemester;
	/**
	 * The minimum number of semesters specified by the user.
	 */
	private int minSemesterNum;
	/**
	 * The maximum number of semesters specified by the user.
	 */
	private int maxSemesterNum;
	/**
	 * This is an auxiliary attribute: it stores the semester to which to each node is allocated 
	 * during the generation of a plan.
	 */
	private int[] semesterAllocation;
	/**
	 * This is an auxiliary attribute: it stores the number of credit points per semester
	 *  during the generation of a plan.
	 */
	private double[] semesterSum;
	/**
	 * This is an auxiliary attribute: it stores the minimal semester to which each node can
	 * be allocated during the generation of a plan.
	 */
	private int[] minPos;
	/**
	 * The original plan that was passed to the generator as a parameter.
	 */
	private Plan currentPlan;
	/**
	 * This is an auxiliary attribute: it stores the visited nodes during the generation of 
	 * a plan.
	 */
	private List<Node> visited;

	/**
	 * @return the currentPlan
	 */
	protected Plan getCurrentPlan() {
		return currentPlan;
	}

	/**
	 * @param currentPlan
	 *            the currentPlan to set
	 */
	public void setCurrentPlan(Plan currentPlan) {
		this.currentPlan = currentPlan;
	}

	/**
	 * List of Nodes of the Graph to create.
	 */
	private NodesList nodes;

	/**
	 * @return the nodes of the graph to create
	 */
	protected NodesList getNodes() {
		return nodes;
	}

	public Plan generate(PartialObjectiveFunction objectiveFunction, final Plan currentPlan, ModuleDao moduleDAO,
			Map<Field, Category> preferredSubjects, double maxECTSperSemester, double minECTSperSemester,
			int minSemesterNum, int maxSemesterNum) {
		this.currentPlan = currentPlan;
		this.maxECTSperSemester = maxECTSperSemester;
		this.minECTSperSemester = minECTSperSemester;
		this.minSemesterNum = minSemesterNum;
		this.maxSemesterNum = maxSemesterNum;
		Map<Plan, NodesList> planFamily;
		Iterator<Plan> it;
		Verifier verifier = new StandardVerifier();
		Plan plan = currentPlan;
		// first generation of family of plans with change of all randomly added
		// nodes
		planToGraph(currentPlan);
		planFamily = randomlyGeneratedFamilyOfPlans(nodes, preferredSubjects, CHANGE_ALL_NODES, 
				moduleDAO);
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
		while (it.hasNext()) {
			Plan nextPlan = it.next();
			if (objectiveFunction.evaluate(nextPlan) > objectiveFunction.evaluate(plan)) {
				plan = nextPlan;
			}
		}
		// Save the nodes list from which this plan was created
		NodesList planNodesList;
		for (int i = 0; i < NUM_PLANS_TO_CREATE; i++) {
			planNodesList = planFamily.get(plan);
			planFamily = randomlyGeneratedFamilyOfPlans(planNodesList, preferredSubjects, 
					NUM_NODES_TO_CHANGE, moduleDAO);
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
	 */
	private void addFieldModules(Field field, Category category, ModuleDao moduleDAO) {
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
	 * @param minECTSperSemester
	 *            minimum amount of credit points per semester
	 * @param moduleDAO
	 *            the moduleDao used to fetch modules
	 * @return a Map containing only one key (the plan generated) and one value
	 *         (the nodesList from which the plan was generated) for later
	 *         modification.
	 */
	private GenerationResult complete(NodesList nodes, Map<Field, Category> preferredSubjects, ModuleDao moduleDAO) {
		// adding modules of the rule groups of the discipline
		List<RuleGroup> ruleGroups = currentPlan.getUser().getDiscipline().getRuleGroups();
		for (RuleGroup ruleGroup : ruleGroups) {
			addRuleGroupModules(ruleGroup, preferredSubjects.get(ruleGroup), moduleDAO);
		}
		// adding modules of the fields of the discipline
		List<Field> fields = currentPlan.getUser().getDiscipline().getFields();
		for (Field field : fields) {
			addFieldModules(field, preferredSubjects.get(field), moduleDAO);
		}
		List<Node> sorted = nodes.sort();
		allocateToSemesters(sorted);
		GenerationResult result = new GenerationResult(createPlan(sorted, semesterAllocation, currentPlan.getUser()), nodes);
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
	private Plan createPlan(List<Node> sorted, int[] semesterAllocation, User user) {
		Plan plan = new Plan();
		plan.getAllModuleEntries().addAll(user.getPassedModules());
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
	 * @param moduleDAO
	 *            the moduleDao used to fetch modules
	 */
	private void addRuleGroupModules(RuleGroup ruleGroup, Category category, ModuleDao moduleDAO) {
		int num = nodes.nodesInRuleGroup(ruleGroup).size();
		int i = ruleGroup.getMinNum();
		int a = ruleGroup.getMaxNum();
		if (num >= ruleGroup.getMinNum() && (num <= ruleGroup.getMaxNum() 
												|| ruleGroup.getMaxNum() == -1)) {
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
	 * Allocates the sorted list of nodes given to semesters. That means that it
	 * spreads the nodes so that they can fit in respective semesters to create
	 * a plan.
	 * 
	 * @param sorted the sorted list of nodes.
	 */
	private void parallelize(List<Node> sorted) {
		WeightFunction weight = new WeightFunction();
		Node node;
		boolean set;
		int currentSem = Semester.getCurrentSemester().getDistanceTo(currentPlan.getUser().getStudyStart());
		// semesterSum[0] is the sum of semester number 1 !
		for (int i = 0; i < minPos.length; i++) {
			minPos[i] = currentSem;
		}
		for (int i = 0; i < sorted.size(); i++) {
			node = sorted.get(i);
			set = false;
			/*
			 * Check if a node is already fixed in a semester: if it is check if
			 * a parent is in a previous semester
			 */
			if (node.getSemester() != 0 
					&& node.fitsInSemester(node.getSemester(), semesterAllocation, sorted)
					&& weight.getWeight(node) + semesterSum[node.getSemester() - 1] <= maxECTSperSemester
					&& checkIfOverlapping(node, semesterAllocation, sorted, node.getSemester())) {
				minPos[i] = node.getSemester();
				semesterAllocation[i] = node.getSemester();
				semesterSum[node.getSemester() - 1] += weight.getWeight(node);
				for (Node child : node.getPrerequisiteChildren()) {
					if (sorted.contains(child)) {
						minPos[sorted.indexOf(child)] = Math.max(node.getSemester() + 1, minPos[sorted.indexOf(child)]);
					}
				}
				set = true;
			} else {
				node.setSemester(0);
				/*
				 * Iterate through semesters from minimum position of the node
				 * to last possible semester (size of sorted plus the current
				 * Semester
				 */
				for (int j = minPos[i]; j < sorted.size() + currentSem; j++) {
					if (weight.getWeight(node) + semesterSum[j - 1] <= maxECTSperSemester
							&& checkIfOverlapping(node, semesterAllocation, sorted, j)
							&& node.fitsInSemester(j, semesterAllocation, sorted)) {
						minPos[i] = j;
						semesterAllocation[i] = j;
						semesterSum[j - 1] += weight.getWeight(node);
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
	}
	/**
	 * Optimizes the semester allocation according to the minimum number of semesters
	 * specified by the user.
	 * @param currentSem number of the current semester.
	 * @param sorted the sorted list of nodes.
	 */
	private void considerMinSemesterNum(int currentSem, List<Node> sorted) {
		// Get the last semester(greatest number in semesterAllocation)
		int lastSem = max(semesterAllocation);
		boolean ok = true;
		while (lastSem < minSemesterNum && ok == true) {
			ok = false;
			// iterate through other semesters
			for (int otherSem = currentSem; otherSem < lastSem; otherSem++) {
				final int a = otherSem; // to be able to use prevSem in
										// stream
				List<Node> nodesAllocated = sorted.stream().filter(n -> (semesterAllocation[sorted.indexOf(n)] == a))
						.collect(Collectors.toList());
				/*
				 * iterate through the nodes allocated to this semester (that
				 * have children with constraint from type Prerequisite)
				 */
				for (Node n : nodesAllocated.stream()
						.filter(no -> no.getPrerequisiteChildren().isEmpty() && no.getPrerequisiteParents().isEmpty())
						.collect(Collectors.toList())) {
					if (pushToSem(lastSem + 1, n, sorted)) {
						ok = true;
						break;
					}
				}
				if (ok) {
					break;
				}
			}
			if (semesterSum[lastSem] == 0.0) {
				ok = false;
				// iterate through other semesters
				for (int otherSem = currentSem; otherSem < lastSem; otherSem++) {
					final int a = otherSem; // to be able to use prevSem in
											// stream
					List<Node> nodesAllocated = sorted.stream()
							.filter(n -> (semesterAllocation[sorted.indexOf(n)] == a)).collect(Collectors.toList());
					/*
					 * iterate through the nodes allocated to this semester
					 * (that have children with constraint from type
					 * Prerequisite)
					 */
					for (Node n : nodesAllocated) {
						if (pushToSem(lastSem + 1, n, sorted)) {
							ok = true;
							break;

						}
					}
					if (ok) {
						break;
					}
				}
			}
			lastSem = max(semesterAllocation);
		}
	}
	/**
	 * Changes the semester to which the node was allocated to the semester given as a 
	 * parameter, if it fits in the given semester. This method uses the recursive 
	 * pushToSemUtil to check if the change of semester is allowed(module constraints).
	 * 
	 * @param semester to which the node should be allocated.
	 * @param node the node concerned.
	 * @param sorted the sorted list of nodes.
	 * @return true if the change was successful, false if not.
	 */
	private boolean pushToSem(int semester, Node node, List<Node> sorted) {
		visited = new ArrayList<Node>();
		return pushToSemUtil(semester, node, sorted);
	}
	/**
	 * Changes the semester to which the node was allocated to the semester given as a 
	 * parameter and according to that changes recursively  the semesters to which the 
	 * children of the node were allocated to.
	 * 
	 * @param semester to which the node should be allocated.
	 * @param node the node concerned.
	 * @param sorted the sorted list of nodes.
	 * @return true if the change was successful, false if not.
	 */
	private boolean pushToSemUtil(int semester, Node node, List<Node> sorted) {
		if (semester > maxSemesterNum) {
			return false;
		}
		visited.add(node);
		boolean ok = false;
		WeightFunction weight = new WeightFunction();
		int otherSem = semesterAllocation[sorted.indexOf(node)];
		for (Node child : node.getPrerequisiteChildren()) {
			if (!visited.contains(child)) {
				ok = pushToSemUtil(semester + 1, node, sorted);
			}
		}
		if ((semesterSum[otherSem - 1] - weight.getWeight(node)) >= minECTSperSemester
				&& node.fitsInSemester(semester, semesterAllocation, sorted)) {
			semesterAllocation[sorted.indexOf(node)] = semester;
			semesterSum[otherSem - 1] -= weight.getWeight(node);
			semesterSum[semester - 1] += weight.getWeight(node);
			ok = true;
		}
		return ok;
	}
	/**
	 * Optimizes the semester allocation according to the minimum number of credit points 
	 * specified by the user.
	 * @param currentSem number of the current semester.
	 * @param sorted the sorted list of nodes.
	 */
	private void considerMinECTSperSemester(int currentSem, List<Node> sorted) {
		// Get the last semester(greatest number in semesterAllocation)
		int lastSem = max(semesterAllocation);
		int sem = lastSem;
		boolean ok;
		while (sem > currentSem) {
			if (semesterSum[sem - 1] < minECTSperSemester) {
				ok = false;
				// iterate through semesters
				for (int otherSem = currentSem; otherSem < lastSem; otherSem++) {
					final int a = otherSem; // to be able to use prevSem in
											// stream
					List<Node> nodesAllocated = sorted.stream()
							.filter(n -> (semesterAllocation[sorted.indexOf(n)] == a)).collect(Collectors.toList());
					/*
					 * iterate through the nodes allocated to this semester that
					 * do not have children nor parents(with constraint from
					 * type Prerequisite)
					 */
					for (Node n : nodesAllocated.stream().filter(
							no -> no.getPrerequisiteChildren().isEmpty() && no.getPrerequisiteParents().isEmpty())
							.collect(Collectors.toList())) {
						if (pushToSem(sem, n, sorted)) {
							if (semesterSum[sem - 1] >= minECTSperSemester) {
								ok = true;
								break;
							}

						}
					}
					if (ok) {
						break;
					}
				}
			}
			if (semesterSum[sem - 1] < minECTSperSemester) {
				ok = false;
				// iterate through other semesters
				for (int otherSem = lastSem; otherSem > currentSem; otherSem--) {
					final int a = otherSem; // to be able to use prevSem in
											// stream
					List<Node> nodesAllocated = sorted.stream()
							.filter(n -> (semesterAllocation[sorted.indexOf(n)] == a)).collect(Collectors.toList());
					/*
					 * iterate through the nodes allocated to this semester
					 */
					for (Node n : nodesAllocated) {
						if (pushToSem(sem, n, sorted)) {
							if (semesterSum[sem - 1] >= minECTSperSemester) {
								ok = true;
								break;
							}

						}
					}
					if (ok) {
						break;
					}
				}
			}
			sem--;
		}
	}

	/**
	 * Allocates the sorted list of nodes given to semesters and optimizes the allocation 
	 * according to the specified parameters passed by the user.
	 * 
	 * @param sorted
	 *            topologically sorted list of nodes
	 * @param maxECTSperSemester
	 *            maximum amount of credit points per semester
	 * @param minECTSperSemester
	 *            minimum amount of credit points per semester
	 * @return an array containing the number of the semester to which each node
	 *         is allocated.
	 */
	private int[] allocateToSemesters(List<Node> sorted) {
		int currentSem = Semester.getCurrentSemester().getDistanceTo(currentPlan.getUser().getStudyStart());
		semesterAllocation = new int[sorted.size()];
		semesterSum = new double[Math.max(sorted.size() + currentSem, maxSemesterNum)];
		// semesterSum[0] is the sum of the current Semester !
		minPos = new int[sorted.size()];
		// add the credit points of the modules passed in the current semester
		// to the current semester.
		for (ModuleEntry e : currentPlan.getUser().getPassedModules().stream()
				.filter(e -> (e.getSemester() == currentSem)).collect(Collectors.toList())) {
			semesterSum[0] += e.getModule().getCreditPoints();
		}
		parallelize(sorted);

		// make sure that the number of semesters > minECTSperSemester
		considerMinSemesterNum(currentSem, sorted);

		// make sure that the credit points in a semester > minECTSperSemester
		considerMinECTSperSemester(currentSem, sorted);

		return semesterAllocation;
	}

	/**
	 * 
	 * @param array
	 * @return
	 */
	private int max(int[] array) {
		int max = -1;
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}
		return max;
	}

	/**
	 * Checks if there is a node, that has a constraint from type overlapping
	 * with the node given, in the semester with the number given.
	 * 
	 * @param node
	 *            the node concerned
	 * @param semesterAllocation
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
	private boolean checkIfOverlapping(Node node, int[] semesterAllocation, List<Node> sorted, int semesterNum) {
		for (Node n : nodes.getOverlappingNodes(node)) {
			if (semesterAllocation[sorted.indexOf(n)] == semesterNum) {
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
	 * @param minECTSperSemester
	 *            minimum amount of credit points per semester
	 * @param moduleDAO
	 *            the moduleDao used to fetch modules
	 */
	private Map<Plan, NodesList> randomlyGeneratedFamilyOfPlans(NodesList nodes, 
			Map<Field, Category> preferredSubjects, int numberOfNodesToChange, ModuleDao moduleDAO) {
		Map<Plan, NodesList> planFamily = new HashMap<Plan, NodesList>();
		GenerationResult generated = complete(nodes, preferredSubjects, moduleDAO);
		planFamily.put(generated.getPlan(), generated.getNodesList());
		for (int i = 0; i < 9; i++) {
			if (numberOfNodesToChange == -1) {
				numberOfNodesToChange = nodes.getRandomlyAddedNodes().size();
			}
			GenerationResult modified = modify(numberOfNodesToChange, generated, preferredSubjects, moduleDAO);
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

	private void planToGraph(Plan plan) {
		nodes = new NodesList(plan, this);
		Node node;
		// Create a Node for every ModuleEntry and add it to the list of nodes
		for (int i = 0; i < plan.getAllModuleEntries().size(); i++) {
			Module m = plan.getAllModuleEntries().get(i).getModule();
			node = nodes.get(m);
			if (node == null) {
				node = new NodeWithOutput(m, plan, this);
				node.setSemester(plan.getAllModuleEntries().get(i).getSemester());
				nodes.add(node, false);
			} else {
				nodes.get(m).setSemester(plan.getAllModuleEntries().get(i).getSemester());
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
	private Set<Integer> randomNumbers(int max, int i) {
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
	 * @param maxECTSperSemester
	 *            maximum amount of credit points per semester
	 * @param minECTSperSemester
	 *            minimum amount of credit points per semester
	 * @param preferredSubjects
	 * @return the new plan
	 */
	private GenerationResult modify(int numberOfNodes, GenerationResult generated,
			Map<Field, Category> preferredSubjects, ModuleDao moduleDAO) {
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
		return complete(nodes, preferredSubjects, moduleDAO);
	}
	/**
	 * @param maxECTSperSemester the maxECTSperSemester to set
	 */
	protected void setMaxECTSperSemester(double maxECTSperSemester) {
		this.maxECTSperSemester = maxECTSperSemester;
	}
	
	/**
	 * @param minECTSperSemester the minECTSperSemester to set
	 */
	protected void setMinECTSperSemester(double minECTSperSemester) {
		this.minECTSperSemester = minECTSperSemester;
	}
	
	/**
	 * @param minSemesterNum the minSemesterNum to set
	 */
	protected void setMinSemesterNum(int minSemesterNum) {
		this.minSemesterNum = minSemesterNum;
	}
	
	/**
	 * @param maxSemesterNum the maxSemesterNum to set
	 */
	protected void setMaxSemesterNum(int maxSemesterNum) {
		this.maxSemesterNum = maxSemesterNum;
	}
	

}
