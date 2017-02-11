package edu.kit.informatik.studyplan.server.generation.standard;


import java.util.ArrayList;
import java.util.List;

import edu.kit.informatik.studyplan.server.generation.standard.Node;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraint;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.PlanLinkModuleConstraintType;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.PrerequisiteModuleConstraintType;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.SemesterLinkModuleConstraintType;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/************************************************************/
/**
 * Die Klasse NodeWithOutput erbt von Node und stellt einen inneren Knoten der
 * Graphenstruktur, also ein Modul, welches Vorraussetzung für andere Module
 * ist, dar.
 */
public class NodeWithOutput extends Node {

	protected NodeWithOutput(Module module) {
		super(module);
	}	

	protected NodeWithOutput(Module module, Plan plan) {
		super(module, plan);
	}
	/**
	 * Die Liste children vom Typ Node enthält alle Knoten, zu denen der Knoten
	 * eine Ausgangskante hat.
	 */
	private ArrayList<Node> children = new ArrayList<Node>();

	protected ArrayList<Node> getChildren() {
		return children;
	}
	
	protected List<Node> fulfillConstraints(NodesList nodes, boolean random) {
		//the List to return
		if(this.isPassed()) {
			return null;
		}
		List<Node> newNodes = new ArrayList<Node>();
		if(this.constraintsAreFulfilled()){
			return newNodes;
		}
		Node newNode;
		// This would be the module of the child node
		Module module = null;
		// This boolean indicates if the new node already exists in the list of nodes
		boolean y = true;
		ModuleConstraint c = null;
		for (int i = 0; i < this.getModule().getConstraints().size(); i++) {
			c = this.getModule().getConstraints().get(i);
			module = getRemainingModuleFromConstraint(c);
			// Check if a node with this module already exists in the list of
			// nodes of the current plan
			newNode = nodes.getFromAllNodes(module);
			if (newNode == null) {
				y = false;
				newNode = new NodeWithOutput(module);
				if(random) {
					nodes.getRandomlyAddedNodes().add(newNode);
				}
			}
			if (c.getConstraintType() instanceof PrerequisiteModuleConstraintType) {
				addParent(newNode);
				nodes.addToAllNodes(newNode);
				if(random) {
					nodes.getRandomlyAddedNodes().add(newNode);
				}
			} else if (c.getConstraintType() instanceof PlanLinkModuleConstraintType) {
				addChild(newNode);
				nodes.addToAllNodes(newNode);
			} else if (c.getConstraintType() instanceof SemesterLinkModuleConstraintType) {
				addInnerNode(newNode);
				nodes.addToAllNodes(newNode);
			}
			if (!y) {
				newNodes.add(newNode);
				newNodes.addAll(newNode.fulfillConstraints(nodes, random));				
			}
			setConstraintsFulfilled(true);
		}
		return newNodes;
	}
	@Override
	protected void addChild(Node node) {
		if (!children.contains(node) && !getParents().contains(node)){
			node.setPlan(getPlan());
			children.add(node);
			node.addParent(this);
		}
	}

	@Override
	protected boolean removeChild(Node node) {
		return children.remove(node);
	}



}