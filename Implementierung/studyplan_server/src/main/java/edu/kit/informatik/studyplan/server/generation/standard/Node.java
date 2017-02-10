package edu.kit.informatik.studyplan.server.generation.standard;

import edu.kit.informatik.studyplan.server.model.moduledata.Module;
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
	 * 
	 */
	private Plan plan;
	/**
	 * 
	 */
	private boolean isPassed = false;
	/**
	 * 
	 */
	private boolean isFixed = false;
	/**
	 * Ein Knoten beinhaltet eine Liste parents vom Typ Node, die alle Knoten
	 * enthält, die Kanten zu diesem Knoten haben.
	 */
	private ArrayList<Node> parents = new ArrayList<Node>();	
	/**
	 * ein innerer Knoten zur Darstellung von parallelen Modulen (Knoten).
	 */
	private Node innerNode = null;
	private Node outerNode = null;
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
	public abstract List<Node> fulfillConstraints(NodesList graphNodes, boolean random);
	/**
	 * If this is a Node with Output, this method returns a list of all nodes, to which 
	 * this node is connected with an output edge.
	 * If this is a node without output, it returns null.
	 * @return children-nodes
	 */
	public abstract ArrayList<Node> getChildren();
	
	/**
	 * If this is a Node with Output, this method adds a node to the list of nodes to which 
	 * this node is connected with an output edge.
	 * If this is a node without output, it does nothing.
	 * 
	 * @param node the node to add.
	 */
	public abstract void addChild(Node node);
	
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
	public abstract boolean removeChild(Node node);

	protected Node(Module module){
		this.module = module;
	}
	
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
	 * Die Methode getPartens gibt die Liste parents zurück.
	 * 
	 * @return parent-moduls
	 */
	public ArrayList<Node> getParents() {
		return parents;
	}

	/**
	 * Die Methode gibt das Modul zurück, welches der Knoten darstellt.
	 * 
	 * @return Das Modul (Typ Module), welches der Knoten repräsentiert.
	 */
	public Module getModule() {
		return module;
	}

	/**
	 * Die Methode addParent fügt der Liste parents vom Typ node den übergebenen
	 * node hinzu.
	 * 
	 * @param node
	 */
	public void addParent(Node node) {
		if (!getChildren().contains(node) && !getParents().contains(node)){
			node.setPlan(plan);
			parents.add(node);
			node.addChild(this);
		}
	}

	/**
	 * die Methode getInnerNodes gibt eventuell enthaltene weitere Knoten zurück.
	 * 
	 * @return enthaltene Knoten
	 */
	public Node getInnerNode() {
		return innerNode;
	}

	public Node getOuterNode() {
		return outerNode;
	}
	
	
	/**
	 * Die Methode addInnerNode fügt dem innersten Knoten den übergebenen Knoten
	 * als inneren Knoten hinzu.
	 * 
	 * @param node
	 *            der neue innerste Knoten.
	 */
	public void addInnerNode(Node node) {
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
	public void setOuterNode(Node node){
		this.outerNode = node;
	}
	public Node getInnermostNode() {
		Node n = this;
		while (n.hasInnerNode()){
			n = n.getInnerNode();
		}
		return n;
	}
	public Node getOutermostNode() {
		Node n = this;
		while (n.hasOuterNode()){
			n = n.getOuterNode();
		}
		return n;
	}
	/**
	 * @param innerNode the innerNode to set
	 */
	public void setInnerNode(Node innerNode) {
		this.innerNode = innerNode;
		innerNode.setOuterNode(this);
	}
	public boolean isPassed(){
		return isPassed;
	}
	public ModuleEntry toModuleEntry(){
		return null;
		// TODO
	}
	/**
	 * @return the plan
	 */
	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	
	public boolean hasInnerNode() {
		if (getInnerNode() == null) {
			return false;
		}
		else {
			return true;
		}
	}
	public boolean hasOuterNode() {
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
	 * @return the isFixed
	 */
	public boolean isFixed() {
		return isFixed;
	}
	/**
	 * @param isFixed the isFixed to set
	 */
	public void setFixed(boolean isFixed) {
		this.isFixed = isFixed;
	}
	/**
	 * @return the constraintsFulfilled
	 */
	public boolean constraintsAreFulfilled() {
		return constraintsFulfilled;
	}
	/**
	 * @param constraintsFulfilled the constraintsFulfilled to set
	 */
	public void setConstraintsFulfilled(boolean constraintsFulfilled) {
		this.constraintsFulfilled = constraintsFulfilled;
	}

}