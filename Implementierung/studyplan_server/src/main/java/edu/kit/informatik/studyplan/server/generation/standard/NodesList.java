package edu.kit.informatik.studyplan.server.generation.standard;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.RuleGroup;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraint;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.OverlappingModuleConstraintType;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/**
 * This class represents a list of nodes of a plan. It's a class used by the
 * generator to : -store the nodes created from the plan, -edit the nodes to
 * generate
 * 
 * @author Nada_Chatti
 * @version 1.0
 *
 */
public class NodesList extends ArrayList<Node> {


	/**
	 * To avoid unnecessary errors.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The generator that created this list.
	 */
	private SimpleGenerator generator;
	/**
	 * The plan that this nodeslist was creates from
	 */
	private Plan plan;

	/**
	 * Creates a new NodesList and sets its plan.
	 * 
	 * @param plan
	 *            to set.
	 */
	protected NodesList(Plan plan, SimpleGenerator generator) {
		this.plan = plan;
		this.generator = generator;
	}

	/**
	 * This list represents all nodes added to this list by the generator during
	 * a random generation process.
	 */
	private List<Node> randomlyAddedNodes = new ArrayList<Node>();
	/**
	 * Auxiliary attribute: stores visited nodes while sorting the list.
	 */
	Set<Node> visited = new LinkedHashSet<Node>();

	/**
	 * Adds the Node given to the list of all nodes if it doesn't already exist.
	 * 
	 * @param node
	 *            the node that should be added.
	 * @param random
	 *            if this node is added randomly this parameter should be true.
	 * @return the Node added if add was successful, null if node already exist.
	 */
	protected boolean add(Node node, boolean random) {
		if (this.contains(node)) {
			return false;
		}
		super.add(node);
		if (random) {
			getRandomlyAddedNodes().add(node);
		}
		return true;
	}

	/**
	 * Adds a new Node with the module given to the list of all nodes if it
	 * doesn't already exist.
	 * 
	 * @param m
	 *            module which node should be added
	 * @return the Node added if add was successful, null if node already exist.
	 */
	protected Node add(Module m) {
		if (this.stream().anyMatch(n -> n.getModule().getIdentifier() == m.getIdentifier())) {
			return null;
		}
		Node node = new NodeWithOutput(m, plan, generator);
		add(node);
		return node;
	}

	/**
	 * Returns this module's node if it already exists in this list.
	 * 
	 * @param module
	 *            the module which node is needed.
	 * @return -the node of the module given if it exists, -null if it doesn't.
	 */
	protected Node get(Module m) {
		for (Node n : this) {
			if (m.getIdentifier() == n.getModule().getIdentifier()) {
				return n;
			}
		}
		return null;
	}

	/**
	 * Returns the node given if it already exists in this list.
	 * 
	 * @param module
	 *            the module which node is needed.
	 * @return -the node of the module given if it exists, -null if it doesn't.
	 */
	protected Node get(Node node) {
		for (Node n : this) {
			if (node.getModule().getIdentifier() == n.getModule().getIdentifier()) {
				return n;
			}
		}
		return null;
	}

	/**
	 * Returns the sum of the Credit Points of all modules which have nodes in
	 * the list.
	 * 
	 * @return Sum of Credit Points
	 */
	protected double getSumCreditPoints() {
		int r = 0;
		for (Node n : this) {
			r += n.getModule().getCreditPoints();
		}
		return r;
	}

	/**
	 * @return the randomlyAddedNodes
	 */
	protected List<Node> getRandomlyAddedNodes() {
		return randomlyAddedNodes;
	}

	/**
	 * Remove given node from list recursively.
	 * 
	 * @param n
	 *            node to remove
	 */
	void remove(Node n) {
		visited.clear();
		if (get(n) != null) {
			n = get(n);
		}
		removeUtil(n);
		for (Node visited : visited) {
			Node aux = visited;
			while (aux.hasInnerNode()) {
				aux = aux.getInnerNode();
				removeUtil(aux);
			}
			aux = visited;
			while (aux.hasOuterNode()) {
				aux = aux.getOuterNode();
				removeUtil(aux);
			}
		}
	}

	/**
	 * Remove given node from list recursively with removing all its inner nodes
	 * and children, and removing it from the
	 * 
	 * @param n
	 *            node to remove
	 */
	private void removeUtil(Node n) {
		if (!visited.contains(n)) {
			visited.add(n);

			/* store children of the node in an array to remove them because
			 removing while iterating on a list doesn't give the right result. */
			Node[] children = new Node[n.getChildren().size()];
			n.getChildren().toArray(children);
			for (int i = 0; i < children.length; i++) {
				removeUtil(children[i]);
			}

			// remove the node itself
			getRandomlyAddedNodes().remove(n);
			super.remove(n);
			// remove this node from the child list of the remaining nodes
			for (Node node : this) {
				// TODO if plan link remove the parent?
				node.removeChild(n);
			}
		}
	}

	/**
	 * Returns the sum of credit points of all modules that belong to the given
	 * field.
	 * 
	 * @param field
	 *            to
	 * @return
	 */
	protected double getCreditPoints(Field field) {
		double creditPoints = 0;
		for (Node n : this) {
			if (field.getFieldId() == n.getModule().getField().getFieldId()) {
				creditPoints += n.getModule().getCreditPoints();
			}
		}
		return creditPoints;
	}

	/**
	 * Returns a list of the nodes whose modules that belong to the given
	 * ruleGroup.
	 * 
	 * @param ruleGroup
	 *            concerned
	 * @return a list of the nodes whose modules that belong to the given
	 *         ruleGroup.
	 */
	protected List<Node> nodesInRuleGroup(RuleGroup ruleGroup) {
		List<Node> result = new ArrayList<Node>();
		for (Node n : this) {
			if (ruleGroup.getModules().contains(n.getModule())) {
				result.add(n);
			}
		}
		return result;
	}
	/**
	 * Sorts this list of nodes using the NodesListSorter.
	 * The List returned does not contain the nodes with passed modules.
	 * @return a sorted list of the nodes of this nodesList(without the nodes 
	 * with passed modules)
	 */
	protected List<Node> sort() {
		return (new NodesListSorter(this)).sort().stream()
				.filter(n -> (n.isPassed() == false)).collect(Collectors.toList());
	}

	/**
	 * Returns a list of all nodes in the list that can't be added to the same
	 * semester as the node given. If the node's constraints are not fulfilled
	 * this method returns null.
	 * 
	 * @param node
	 *            the node concerned.
	 * @return list of all nodes in the list that can't be added to the same
	 *         semester as the node given.
	 */
	protected List<Node> getOverlappingNodes(Node node) {
		List<Node> result = new ArrayList<Node>();
		if (!node.constraintsAreFulfilled()) {
			throw new IllegalArgumentException(
					"This node(" + node.getModule().getIdentifier() + ")'s constraints are not fulfilled");
		}
		for (ModuleConstraint constraint : node.getModule().getConstraints()) {
			if ((constraint.getConstraintType() instanceof OverlappingModuleConstraintType)) {
				Node n = get(node.getRemainingModuleFromConstraint(constraint));
				result.add(n);
			}
		}
		return result;
	}

}
