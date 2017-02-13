package edu.kit.informatik.studyplan.server.generation.standard;


import java.util.ArrayList;
import java.util.List;

import edu.kit.informatik.studyplan.server.generation.Generator;
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

	protected NodeWithOutput(Module module, SimpleGenerator generator) {
		super(module, generator);
	}	

	protected NodeWithOutput(Module module, Plan plan, SimpleGenerator generator) {
		super(module, plan, generator);
	}
	/**
	 * Die Liste children vom Typ Node enthält alle Knoten, zu denen der Knoten
	 * eine Ausgangskante hat.
	 */
	private ArrayList<Node> children = new ArrayList<Node>();

	protected ArrayList<Node> getChildren() {
		return children;
	}
	
	protected void fulfillConstraints(boolean random) {
		if(this.isPassed()) {
			return;
		}
		if(this.constraintsAreFulfilled()){
			return;
		}
		Node newNode;
		// This would be the module of the child node
		Module module = null;
		// This boolean indicates if the new node already exists in the list of nodes
		boolean y = true;
		for (ModuleConstraint c : this.getModule().getConstraints()) {
			module = getRemainingModuleFromConstraint(c);
			// Check if a node with this module already exists in the list of
			// nodes of the current plan
			newNode = getGenerator().getNodes().getFromAllNodes(module);
			if (newNode == null) {
				y = false;
				newNode = new NodeWithOutput(module, getGenerator());
				newNode.setPlan(this.getPlan());
				if(random) {
					getGenerator().getNodes().getRandomlyAddedNodes().add(newNode);
				}
			}
			if (c.getConstraintType() instanceof PrerequisiteModuleConstraintType) {
				addParent(newNode);
				getGenerator().getNodes().addToAllNodes(newNode);
			} else if (c.getConstraintType() instanceof PlanLinkModuleConstraintType) {
				addChild(newNode);
				getGenerator().getNodes().addToAllNodes(newNode);
			} else if (c.getConstraintType() instanceof SemesterLinkModuleConstraintType) {
				addInnerNode(newNode);
				getGenerator().getNodes().addToAllNodes(newNode);
			}
			if (!y) {
				newNode.fulfillConstraints(random);				
			}
			setConstraintsFulfilled(true);
		}
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