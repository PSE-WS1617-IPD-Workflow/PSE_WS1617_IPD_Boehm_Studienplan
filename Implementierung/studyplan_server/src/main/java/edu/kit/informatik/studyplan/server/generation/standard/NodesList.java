package edu.kit.informatik.studyplan.server.generation.standard;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.RuleGroup;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraint;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.OverlappingModuleConstraintType;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;
/**
 * This class represents a list of nodes of a plan.
 * It's a class used by the generator to :
 * 		-store the nodes created from the plan,
 * 		-edit the nodes to generate
 * @author Nada_Chatti
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
	 * @param plan to set.
	 */
	public NodesList(Plan plan, SimpleGenerator generator) {
		this.plan = plan;
		this.generator = generator;
	}
	/**
	 * A list of all nodes in the list along each node's parents, children and inner nodes.
	 */
	private List<Node> allNodes = new ArrayList<Node>(); 
	/**
	 * This list represents all nodes added to this list by the generator during a random 
	 * generation process.
	 */
	private List<Node> randomlyAddedNodes = new ArrayList<Node>(); 
	/**
	 * Auxiliary attribute: stores result while sorting the list.
	 */
	private LinkedList<Node> stack = new LinkedList<Node>();
	/**
	 * Auxiliary attribute: stores visited nodes while sorting the list.
	 */
	Set<Node> visited = new LinkedHashSet<Node>();
	/**
	 * Adds a node to the actual list of nodes and not only to the allNodes list.
	 * This method is used to add the original nodes that were created from the module entries of 
	 * the plan given to the generator as parameter.
	 * @param node to add.
	 */
	public void addNode(Node node) {
		if (addToAllNodes(node)) {
			add(node);
		}
		
	}
	/**
	 * Returns a boolean that says if the node given exists in the list.
	 * @param node needed.
	 * @return true if this list contains the node given, false if it doesn't.
	 */
	public boolean hasNode(Node node) {
		for (Node n : allNodes) {
			if (n.equals(node)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Adds the Node given to the list of all nodes if it doesn't already exist.
	 * @param node the node that should be added.
	 * @return the Node added if add was successful, null if node already exist.
	 */
	public boolean addToAllNodes(Node node) {
		for (Node n: allNodes) {
			if (node.equals(n)) {
				return false;
			}
		}
		allNodes.add(node);
		return true;
	}
	/**
	 * Adds a new Node with the module given to the list of all nodes if it doesn't already exist.
	 * @param m module which node should be added
	 * @return the Node added if add was successful, null if node already exist.
	 */
	public Node addToAllNodes(Module m) {
		for (Node n: allNodes) {
			if (m.getIdentifier() == n.getModule().getIdentifier()) {
				return null;
			}
		}
		Node node = new NodeWithOutput(m, plan, generator);
		allNodes.add(node);
		return node;
	}
	/**
	 * Returns this module's node if it already exists in this list.
	 * @param module the module which node is needed.
	 * @return -the node of the module given if it exists,
	 * 			-null if it doesn't.
	 */
	public Node getFromAllNodes(Module m) {
		for (Node n: allNodes) {
			if (m.getIdentifier() == n.getModule().getIdentifier()) {
				return n;
			}
		}
		return null;
	}
	/**
	 * @return the allNodes
	 */
	public List<Node> getAllNodes() {
		return allNodes;
	}
	/**
	 * Returns the sum of the Credit Points of all modules which have nodes in the list.
	 * @return Sum of Credit Points
	 */
	public double getSumCreditPoints() {
		int r = 0;
		for (Node n : allNodes) {
			r += n.getModule().getCreditPoints();
		}
		return r;
	}

	/**
	 * @return the randomlyAddedNodes
	 */
	public List<Node> getRandomlyAddedNodes() {
		return randomlyAddedNodes;
	}
	/**
	 * Remove given node from list.
	 * @param n node to remove
	 */
	protected void removeNode(Node n) {
		for (Node nodeBefRem : allNodes) { // check if node n exists in nodeslist
			if (nodeBefRem.equals(n)) {
				for (Node childNode : nodeBefRem.getChildren()) { //remove children of the node recursively
					removeNode(childNode);
				}
				//remove all semester-linked nodes with the node recursively
				while (nodeBefRem.getInnermostNode() != nodeBefRem) {
					removeNode(nodeBefRem.getInnermostNode());
				}
				while (nodeBefRem.getOutermostNode() != nodeBefRem) {
					removeNode(nodeBefRem.getOutermostNode());
				}
				//remove the node itself
				getRandomlyAddedNodes().remove(nodeBefRem);
				allNodes.remove(nodeBefRem);
				remove(nodeBefRem);
				// remove this node from the child list of the remaining nodes
				for (Node nodeAfRem : allNodes) {
					nodeAfRem.removeChild(n);
				}
			}
		}
	}
	/**
	 * Returns the sum of credit points of all modules that belong to the given field.
	 * @param field to
	 * @return
	 */
	protected int getCreditPoints(Field field) {
		int creditPoints = 0;
		for (Node n : getAllNodes()) {
			if (field.equals(n.getModule().getField())) {
				creditPoints += n.getModule().getCreditPoints();
			}
		}
		return creditPoints;
	}
	/**
	 * Returns a list of the nodes whose modules that belong to the given ruleGroup.
	 * @param ruleGroup concerned
	 * @return a list of the nodes whose modules that belong to the given ruleGroup.
	 */
	protected List<Node> nodesInRuleGroup(RuleGroup ruleGroup) {
		List<Node> result = new ArrayList<Node>();
		for (Node n : getAllNodes()) {
			if (ruleGroup.getModules().contains(n.getModule())) {
				result.add(n);
			}
		}
		return result;
	}
	/**
	 * Returns a topologically sorted list of the nodes of the list given.
	 * This method considers a node and its inner nodes as one so that the result stack 
	 * only has one node of the whole list.
	 * @param nodes
	 *            list of nodes
	 * @return a list of sorted nodes
	 */
	protected LinkedList<Node> sort() {
		visited.clear();
		stack.clear();
		for (Node n : getAllNodes()) {
			if (!visited.contains(n)) {
				System.out.println("SORT" + n.getModule().getIdentifier());
				sortUtil(n, visited);
			}
		}
		return stack;
	}

	/**
	 * Adds a node to the stack recursively so that the stack always stays topologically 
	 * sorted.
	 * This method considers a node and its inner nodes as one so that the result stack only has 
	 * one node of the whole list.
	 * 
	 * @param node
	 *            the node to begin with
	 * @param visited
	 *            set of visited nodes
	 */
	private void sortUtil(Node node, Set<Node> visited) {
		visited.add(node);
		Node n = node;
		while (n.hasInnerNode()) {
			n = node.getInnerNode();
			visited.add(n);
		}
		n = node;
		while (n.hasOuterNode()) {
			n = node.getOuterNode();
			visited.add(n);
		}
		ArrayList<Node> children = node.getChildren();
		int j = 0;
		while (j < children.size()) {
			if (!visited.contains(children.get(j))) {
				sortUtil(children.get(j), visited);
			}
			j++;
		}
		stack.push(node);
	}
	/**
	 * Returns a list of all nodes in the list that can't be added to the same semester 
	 * as the node given.
	 * If the node's constraints are not fulfilled this method returns null.
	 * 
	 * @param node the node concerned.
	 * @return list of all nodes in the list that can't be added to the same semester 
	 * as the node given.
	 */
	protected List<Node> getOverlappingNodes(Node node) {
		List<Node> result = new ArrayList<Node>();
		if (!node.constraintsAreFulfilled()) {
			throw new IllegalArgumentException("This node's constraints are not fulfilled");
		}
		for (ModuleConstraint constraint : node.getModule().getConstraints()) {
			if ((constraint.getConstraintType() instanceof OverlappingModuleConstraintType)) {
				Node n = getFromAllNodes(node.getRemainingModuleFromConstraint(constraint));
				result.add(n);
			}
		}
		return result;
	}
	
}
