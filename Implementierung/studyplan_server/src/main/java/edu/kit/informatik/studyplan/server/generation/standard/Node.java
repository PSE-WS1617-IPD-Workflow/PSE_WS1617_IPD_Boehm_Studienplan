package edu.kit.informatik.studyplan.server.generation.standard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.kit.informatik.studyplan.server.model.moduledata.CycleType;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraint;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.PrerequisiteModuleConstraintType;
import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.model.userdata.Semester;
import edu.kit.informatik.studyplan.server.model.userdata.SemesterType;

/**
 * The abstract class Node represents a module in a Graph.
 * 
 * @author Nada_Chatti
 * @version 1.0
 */
public abstract class Node {
	/**
	 * The generator that generated this node.
	 */
	private SimpleGenerator generator;
	/**
	 * The Module that this node represents.
	 */
	private Module module;
	/**
	 * the plan that this Node was created from.
	 */
	private Plan plan;
	/**
	 * True if the module of this node is a passed module. False if not.
	 */
	private boolean isPassed = false;
	/**
	 * If this Node was created from a module entry in a plan this attribute
	 * returns the number of the Semester it was in.
	 */
	private int semester;
	/**
	 * List of nodes that have an edge to this node.
	 */
	private List<Node> parents = new ArrayList<Node>();
	/**
	 * An inner node to represent nodes of parallel modules.
	 */
	private Node innerNode = null;
	/**
	 * An outer Node is used to be able to iterate back to nodes that have this
	 * node as an inner node.
	 */
	private Node outerNode = null;
	/**
	 * Boolean that says if the module constraints of this node are already
	 * fulfilled.
	 */
	private boolean constraintsFulfilled = false;

	/**
	 * This method fulfills all constraints related to this Node according to
	 * the constraints' list of its module: - it adds all nodes, to which this
	 * node is connected with an output edge (if constraint = plan_link or
	 * prerequisite), to the children list of this node and recursively
	 * completes all children lists of the nodes added. -it adds all nodes of
	 * parallel modules (constraint = semester_link) as inner nodes. Before
	 * adding the new nodes, this method checks if a node with the same module
	 * already exists if not it creates a new node with that module then adds
	 * it.
	 * 
	 * If this node's module is passed, this method does nothing and returns
	 * null.
	 * 
	 * If the parameter random is true this method adds the new nodes to the
	 * list of randomly added nodes.
	 * 
	 * @param random
	 *            -true if this node is a randomly added node from the generator
	 *            -false if not
	 */
	protected abstract void fulfillConstraints(boolean random);

	/**
	 * This method returns a list of all nodes, to which this node is connected
	 * with an output edge.
	 * 
	 * @return children-nodes
	 */
	protected abstract ArrayList<Node> getChildren();

	/**
	 * This method adds a node to the list of nodes to which this node is
	 * connected with an output edge.
	 * 
	 * @param node
	 *            the node to add.
	 */
	protected abstract void addChild(Node node);

	/**
	 * This method removes the given node to the list of nodes to which this
	 * node is connected with an output edge. This method returns a boolean
	 * indicating if the removal was successful.
	 * 
	 * @param node
	 *            the node to remove.
	 * @return -true if the removal was successful -false if the node given
	 *         couldn't be found
	 */
	protected abstract boolean removeChild(Node node);

	/**
	 * Creates a new Node from the plan given with the module given.
	 * 
	 * @param module
	 *            of this Node.
	 * @param plan
	 *            from which this Node was created.
	 * @param generator
	 *            the generator that created this node.
	 */
	protected Node(Module module, Plan plan, SimpleGenerator generator) {
		this.generator = generator;
		this.module = module;
		this.plan = plan;
		for (ModuleEntry m : plan.getUser().getPassedModules()) {
			if (module.getIdentifier() == m.getModule().getIdentifier()) {
				isPassed = true;
			}
		}
	}

	/**
	 * @param semester
	 *            the semester to set
	 */
	protected void setSemester(int semester) {
		this.semester = semester;
	}

	/**
	 * Returns the list of parents.
	 * 
	 * @return parent-modules
	 */
	protected List<Node> getParents() {
		return parents;
	}

	/**
	 * Returns the module that this node represents.
	 * 
	 * @return the module this nodes's module
	 */
	protected Module getModule() {
		return module;
	}

	/**
	 * Adds the node given to parents list.
	 * 
	 * @param node
	 */
	protected void addParent(Node node) {
		if (!getChildren().contains(node) && !getParents().contains(node)) {
			node.setPlan(plan);
			parents.add(node);
			node.addChild(this);
		}
	}

	/**
	 * Returns inner node if this node has one, nothing if it doesn't.
	 * 
	 * @return inner node
	 */
	protected Node getInnerNode() {
		return innerNode;
	}

	/**
	 * Returns outer node if this node has one, nothing if it doesn't.
	 * 
	 * @return outer node
	 */
	protected Node getOuterNode() {
		return outerNode;
	}

	/**
	 * Adds an inner node to the innermost node.
	 * 
	 * @param node
	 *            inner node to add.
	 */
	protected void addInnerNode(Node node) {
		if (this.equals(node)) {
			throw new IllegalArgumentException("The node to add should be different from the current Node");
		}
		Node n = this;
		while (n.hasOuterNode()) {
			n = n.getOuterNode();
			if (n.equals(node)) {
				return;
			}
		}
		n = this;
		while (n.hasInnerNode()) {
			n = n.getInnerNode();
			if (n.equals(node)) {
				return;
			}
		}
		node.setPlan(plan);
		n.innerNode = node;
		node.setOuterNode(n);
	}

	/**
	 * Sets the outer node
	 * 
	 * @param node
	 *            to set
	 */
	protected void setOuterNode(Node node) {
		this.outerNode = node;
	}

	/**
	 * @param innerNode
	 *            the innerNode to set
	 */
	protected void setInnerNode(Node innerNode) {
		if (innerNode != null) {
			this.innerNode = innerNode;
			innerNode.setOuterNode(this);
		}
	}

	/**
	 * True if the module of this nodes is passed, false if not.
	 * 
	 * @return isPassed
	 */
	protected boolean isPassed() {
		return isPassed;
	}

	/**
	 * @return the plan from which this node was created.
	 */
	protected Plan getPlan() {
		return plan;
	}

	/**
	 * Sets the plan from which this node was created.
	 * 
	 * @param plan
	 *            to set.
	 */
	protected void setPlan(Plan plan) {
		this.plan = plan;
	}

	/**
	 * Checks if this Node has an inner node.
	 * 
	 * @return true if this node has an inner node, false if not.
	 */
	protected boolean hasInnerNode() {
		if (getInnerNode() == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Checks if this Node has an inner node.
	 * 
	 * @return true if this node has an inner node, false if not.
	 */
	protected boolean hasOuterNode() {
		if (getOuterNode() == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Removes the inner node given from the inner nodes. if this node does not
	 * have such an inner node this method does nothing.
	 * 
	 * @param n
	 * @return true if the removal was successful, false if not (this node
	 *         doesn't have an inner node with the module given).
	 */
	protected boolean removeInnerNode(Node n) {
		Node node = this;
		while (node.hasInnerNode()) {
			node = node.getInnerNode();
			if (node.equals(n)) {
				node.getOuterNode().setInnerNode(node.getInnerNode());
				return true;
			}
		}
		node = this;
		while (node.hasOuterNode()) {
			node = node.getOuterNode();
			if (node.equals(n)) {
				if (node.hasOuterNode()) {
					node.getOuterNode().setInnerNode(node.getInnerNode());
				} else {
					node.getInnerNode().setOuterNode(null);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean equals(Object o) {
		boolean res = false;

		if (o instanceof Node) {
			Node node = (Node) o;
			res = node.getModule().getIdentifier() == this.getModule().getIdentifier();
		}

		return res;
	}

	/**
	 * @return the semester
	 */
	protected int getSemester() {
		return semester;
	}

	/**
	 * @return the constraintsFulfilled
	 */
	protected boolean constraintsAreFulfilled() {
		return constraintsFulfilled;
	}

	/**
	 * @param constraintsFulfilled
	 *            the constraintsFulfilled to set
	 */
	protected void setConstraintsFulfilled(boolean constraintsFulfilled) {
		this.constraintsFulfilled = constraintsFulfilled;
	}

	/**
	 * 
	 * @return the module that is on the other end of the constraint
	 */
	protected Module getRemainingModuleFromConstraint(ModuleConstraint c) {
		if (this.getModule().getIdentifier() == c.getFirstModule().getIdentifier()) {
			return c.getSecondModule();
		} else
			return c.getFirstModule();
	}

	/**
	 * Returns the Semester type of the semester with number given
	 * 
	 * @param i
	 *            number of the semester
	 * @return Semester type of the semester with the number given
	 */
	private SemesterType getSemesterType(int i) {
		if ((i % 2) == 1) {
			return plan.getUser().getStudyStart().getSemesterType();
		}
		// if not the cycle takes the opposite value
		else if (plan.getUser().getStudyStart().getSemesterType() == SemesterType.SUMMER_TERM) {
			return SemesterType.WINTER_TERM;
		} else {
			return SemesterType.SUMMER_TERM;
		}

	}

	/**
	 * Returns the constraint of this node's module with the module of the node
	 * given if it exists.
	 * 
	 * @param node
	 * @return the constraint of this node's module with the module of the node
	 *         given if it exists, and null if it doesn't.
	 */
	protected ModuleConstraint getConstraint(Node node) {
		for (ModuleConstraint c : getModule().getConstraints()) {
			if (getRemainingModuleFromConstraint(c).getIdentifier() == node.getModule().getIdentifier()) {
				return c;
			}
		}
		for (ModuleConstraint c : node.getModule().getConstraints()) {
			if (getRemainingModuleFromConstraint(c).getIdentifier() == getModule().getIdentifier()) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Checks if this node can be added to the semester with number given
	 * respecting its cycle type.
	 * 
	 * @param i
	 *            number of the semester.
	 * @param semesterAllocation
	 *            array of the current semester allocation of the nodes.
	 * @param sorted
	 *            the sorted list of nodes.
	 * @return a boolean that is only if the node can be added to the semester
	 *         with the number given.
	 */
	protected boolean fitsInSemester(int i, int[] semesterAllocation, List<Node> sorted) {
		if (this.isPassed && i == this.getSemester()) {
			return true;
		}
		if (i < Semester.getCurrentSemester().getDistanceTo(plan.getUser().getStudyStart())) {
			return false;
		}
		// Check if Semester type corresponds to cycle type of the module
		if (getModule().getCycleType() == CycleType.WINTER_TERM) {
			if (getSemesterType(i) != SemesterType.WINTER_TERM) {
				return false;
			}
		}
		if (getModule().getCycleType() == CycleType.SUMMER_TERM) {
			if (getSemesterType(i) != SemesterType.SUMMER_TERM) {
				return false;
			}
		}
		// check if a prerequisite module is placed in a further semester
		for (Node parent : getPrerequisiteParents()) {
			if (parent.isPassed()) {
				continue;
			}
			if (parent.getSemester() >= i) {
				return false;
			} else if (semesterAllocation[sorted.indexOf(parent)] >= i) {
				return false;
			}
		}
		/* check if a module that has this module as prerequisite is placed in a further 
		 * previous semester.
		 */
		for (Node child : getPrerequisiteChildren()) {
			if (child.getSemester() != 0 && child.getSemester() <= i) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @return a list of the parents whose mudules are prerequisites to this
	 *         node's module.
	 */
	List<Node> getPrerequisiteParents() {
		List<Node> list = new ArrayList<Node>();
		for (ModuleConstraint constraint : getModule().getConstraints().stream()
				.filter(c -> c.getConstraintType() instanceof PrerequisiteModuleConstraintType)
				.collect(Collectors.toList())) {
			if (constraint.getConstraintType() instanceof PrerequisiteModuleConstraintType) {
				for (Node parent : getParents()) {
					if (parent.getModule().equals(getRemainingModuleFromConstraint(constraint))) {
						list.add(parent);
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 
	 * @return a list of the children(if this node has children) whose modules have this
	 *  this node's module as prerequisite.
	 */
	List<Node> getPrerequisiteChildren() {
		return null;
	}

	/**
	 * @return the generator that created this node
	 */
	protected SimpleGenerator getGenerator() {
		return generator;
	}

	@Override
	public String toString() {
		return module.getIdentifier() + "  " + module.getName();
	}

}