package edu.kit.informatik.studyplan.server.generation.standard;

import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraint;
import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;

import java.util.ArrayList;
import java.util.List;
// TODO all collections to lists and initialize!!
/**
 * Die abstrakte Klasse Node stellt Knoten des Graphen da.
 */
public abstract class Node {
	/**
	 * Ein Knoten beinhaltet ein Module module, welches er darstellt.
	 */
	private Module module;
	/**
	 * the plan that this Node was created from.
	 */
	private Plan plan;
	/**
	 * True if the module of this node is a passed module.
	 * False if not.
	 */
	private boolean isPassed = false;
	/**
	 * If this Node was created from a module entry in a plan this attribute 
	 * returns the number of the Semester it was in.
	 */
	private int semester = 0;
	/**
	 * List of nodes that have an edge to this node.
	 */
	private ArrayList<Node> parents = new ArrayList<Node>();	
	/**
	 * An inner node to represent nodes of parallel modules.
	 */
	private Node innerNode = null;
	/**
	 * An outer Node is used to be able to iterate back to nodes that have this node 
	 * as an inner node. 
	 */
	private Node outerNode = null;
	/**
	 * Boolean that says if the module constraints of this node are already fulfilled.
	 */
	private boolean constraintsFulfilled = false;
	/**
	 * If this  is a node with output, this method fulfills all constraints related to 
	 * this Node according to the constraints' list of its module:
	 *    - it adds all nodes, to which this node is connected with an output edge
	 *    (if constraint = plan_link or prerequisite), to the children list of this node 
	 *    and recursively completes all children lists of the nodes added. 
	 *    - it adds all nodes of parallel modules (constraint = semester_link) as inner nodes.
	 * Before adding the new nodes, this method checks if a node with the same module
	 * already exists if not it creates a new node with that module 
	 * then adds it.
	 * 
	 * If this is a node without output, it does nothing.
	 * 
	 * If this node's module is passed, this method does nothing and returns null.
	 * 
	 * @param nodes of the modules in the current graph.
	 * @param random -true if this node is a randomly added node from the generator
	 * 				 -false if not
	 */
	protected abstract List<Node> fulfillConstraints(NodesList graphNodes, boolean random);
	/**
	 * If this is a Node with Output, this method returns a list of all nodes, to which 
	 * this node is connected with an output edge.
	 * If this is a node without output, it returns null.
	 * @return children-nodes
	 */
	protected abstract ArrayList<Node> getChildren();
	
	/**
	 * If this is a Node with Output, this method adds a node to the list of nodes to which 
	 * this node is connected with an output edge.
	 * If this is a node without output, it does nothing.
	 * 
	 * @param node the node to add.
	 */
	protected abstract void addChild(Node node);
	
	/**
	 * If this is a Node with Output, this method removes the given node to the list of
	 *  nodes to which this node is connected with an output edge. This method returns a boolean
	 *  indicating if the removal was successful.
	 * If this is a node without output, it does nothing and returns false.
	 * 
	 * @param node the node to remove.
	 * @return -true if the removal was successful
	 * 		   -false if the node given couldn't be found or the Node is without Output 
	 */
	protected abstract boolean removeChild(Node node);
	/**
	 * Creates a new Node with the module given.
	 * This constructor should only be used as an auxiliary by the Generator (e.g. to use the equals
	 * method) .
	 * @param module of this Node.
	 */
	protected Node(Module module){
		this.module = module;
	}
	/**
	 * Creates a new Node from the plan given with the module given.
	 * @param module of this Node.
	 * @param plan from which this Node was created.
	 */
	protected Node(Module module, Plan plan){
		this.module = module;
		this.plan = plan;
		//Check if the module in in the passedModules list of the user 
		for(ModuleEntry m : plan.getUser().getPassedModules()) {
			if(module.getIdentifier() == m.getModule().getIdentifier()){
				isPassed = true;
			}
		}
	}
	/**
	 * @param semester the semester to set
	 */
	protected void setSemester(int semester) {
		this.semester = semester;
	}
	/**
	 * Returns the list of parents.
	 * 
	 * @return parent-modules
	 */
	protected ArrayList<Node> getParents() {
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
		if (!getChildren().contains(node) && !getParents().contains(node)){
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
		if(this.equals(node)){
			throw new IllegalArgumentException("The node to add should be different from the current Node");
		}
		Node n = this;
		while(n.hasOuterNode()){
			n = n.getOuterNode();
			if(n.equals(node)){
				return;
			}
		}
		n = this;
		while(n.hasInnerNode()){
			n = n.getInnerNode();
			if(n.equals(node)){
				return;
			}
		}
		node.setPlan(plan);
		n.innerNode = node;
		node.setOuterNode(n);
	}
	/**
	 * Sets the outer node
	 * @param node to set
	 */
	protected void setOuterNode(Node node){
		this.outerNode = node;
	}
	/**
	 * returns the innermost node.
	 * @return innermost node.
	 */
	protected Node getInnermostNode() {
		Node n = this;
		while (n.hasInnerNode()){
			n = n.getInnerNode();
		}
		return n;
	}
	/**
	 * returns the outermost node.
	 * @return outermost node.
	 */
	protected Node getOutermostNode() {
		Node n = this;
		while (n.hasOuterNode()){
			n = n.getOuterNode();
		}
		return n;
	}
	/**
	 * @param innerNode the innerNode to set
	 */
	protected void setInnerNode(Node innerNode) {
		this.innerNode = innerNode;
		innerNode.setOuterNode(this);
	}
	/**
	 * True if the module of this nodes is passed, false if not.
	 * @return isPassed
	 */
	protected boolean isPassed(){
		return isPassed;
	}
	/**
	 * Creates a module entry from this node.
	 * @return module entry
	 */
	protected ModuleEntry toModuleEntry(){
		ModuleEntry entry = new ModuleEntry();
		entry.setModule(module);
		entry.setSemester(semester);
		return entry;
	}
	/**
	 * @return the plan from which this node was created.
	 */
	protected Plan getPlan() {
		return plan;
	}
	/**
	 * Sets the plan from which this node was created.
	 * @param plan to set.
	 */
	protected void setPlan(Plan plan) {
		this.plan = plan;
	}
	/**
	 * Checks if this Node has an inner node.
	 * @return true if this node has an inner node, 
	 * 			false if not.
	 */
	protected boolean hasInnerNode() {
		if (getInnerNode() == null) {
			return false;
		}
		else {
			return true;
		}
	}
	/**
	 * Checks if this Node has an inner node.
	 * @return true if this node has an inner node, 
	 * 			false if not.
	 */
	protected boolean hasOuterNode() {
		if (getOuterNode() == null) {
			return false;
		}
		else {
			return true;
		}
	}
	@Override
	public boolean equals(Object o){
        boolean res = false;

        if (o instanceof Node){
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
	 * @param constraintsFulfilled the constraintsFulfilled to set
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
	protected boolean fitsInSemester(int i){
		if(semester != 0) {
			if(semester == i) {
				return true;
			}
			return false;
		}
//		if () {
//		TODO	
//		}
		return false;
	}
}