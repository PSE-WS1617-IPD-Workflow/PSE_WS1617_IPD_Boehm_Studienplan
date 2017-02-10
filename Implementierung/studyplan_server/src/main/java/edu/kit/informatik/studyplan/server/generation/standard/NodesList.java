package edu.kit.informatik.studyplan.server.generation.standard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;

public class NodesList extends ArrayList<Node>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Plan plan;
	
	public NodesList(Plan plan){
		this.plan = plan;
	}

	public NodesList(){
	}
	private List<Node> allNodes = new ArrayList<Node>(); 
	private List<Node> randomlyAddedNodes = new NodesList(); 
	
	public void addNode(Node node){
		if(addToAllNodes(node)) {
			add(node);
		}
		
	}
	
	public boolean hasNode(Node node) {
		for(Node n : allNodes){
			if (n.equals(node)) {
				return true;
			}
		}
		return false;
	}
	public boolean addToAllNodes(Node node) {
		for(Node n: allNodes) {
			if(node.equals(n)) {
				return false;
			}
		}
		allNodes.add(node);
		return true;
	}
	public Node addToAllNodes(Module m) {
		for(Node n: allNodes) {
			if(m.getIdentifier() == n.getModule().getIdentifier()) {
				return null;
			}
		}
		Node node = new NodeWithOutput(m);
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
		for(Node n: allNodes) {
			if(m.getIdentifier() == n.getModule().getIdentifier()) {
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
	 * Returns the sum of the ECTS Points of all modules in 
	 * @return
	 */
	public double getSumCreditPoints(){
		int r = 0;
		for(Node n : allNodes) {
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
	
	protected void removeNode(Node n) {
		for(Node nodeBefRem : allNodes) { // check if node n exists in nodeslist
			if (nodeBefRem.equals(n)) {
				for(Node childNode : nodeBefRem.getChildren()) { //remove children of the node recursively
					removeNode(childNode);
				}
				//remove all semester-linked nodes with the node recursively
				while(nodeBefRem.getInnermostNode() != nodeBefRem) {
					removeNode(nodeBefRem.getInnermostNode());
				}
				while(nodeBefRem.getOutermostNode() != nodeBefRem) {
					removeNode(nodeBefRem.getOutermostNode());
				}
				//remove the node itself
				getRandomlyAddedNodes().remove(nodeBefRem);
				allNodes.remove(nodeBefRem);
				remove(nodeBefRem);
				// remove this node from the child list of the remaining nodes
				for(Node nodeAfRem : allNodes) {
					nodeAfRem.removeChild(n);
				}
			}
		}
	}
	protected int getCreditPoints(Field field) {
		int creditPoints = 0;
		for(Node n : getAllNodes()) {
			if (field.equals(n.getModule().getField())) {
				creditPoints += n.getModule().getCreditPoints();
			}
		}
		return creditPoints;
	}
}
