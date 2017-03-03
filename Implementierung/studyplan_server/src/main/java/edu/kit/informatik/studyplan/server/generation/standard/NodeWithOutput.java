package edu.kit.informatik.studyplan.server.generation.standard;


import java.util.ArrayList;

import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.ModuleConstraint;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.PlanLinkModuleConstraintType;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.PrerequisiteModuleConstraintType;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.SemesterLinkModuleConstraintType;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/************************************************************/
/**
 * The class NodeWithOutput inherits from Node and represents a concrete inner node of the 
 * graph structure that might have children(nodes that this node's module has a prerequisite 
 * or a plan_link constraint with).
 * @author Nada_Chatti
 * @version 1.0
 */
public class NodeWithOutput extends Node {



	protected NodeWithOutput(Module module, Plan plan, SimpleGenerator generator) {
		super(module, plan, generator);
	}
	/**
	 * The list of nodes that this node's module has a prerequisite or a plan_link 
	 * constraint with).
	 */
	private ArrayList<Node> children = new ArrayList<Node>();

	protected ArrayList<Node> getChildren() {
		return children;
	}
	
	protected void fulfillConstraints(boolean random) {
//		System.out.println("FULFILLING CONST " + this.getModule().getIdentifier());
		if (this.isPassed()) {
			setConstraintsFulfilled(true);
			return;
		}
		if (this.constraintsAreFulfilled()) {
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
			newNode = getGenerator().getNodes().get(module);
			if (newNode == null) {
				y = false;
				newNode = new NodeWithOutput(module, getPlan(), getGenerator());
				newNode.setPlan(this.getPlan());
				if (random) {
					getGenerator().getNodes().getRandomlyAddedNodes().add(newNode);
				}
			}
			if (c.getConstraintType() instanceof PrerequisiteModuleConstraintType) {
				addParent(newNode);
				getGenerator().getNodes().add(newNode);
			} else if (c.getConstraintType() instanceof PlanLinkModuleConstraintType) {
				addChild(newNode);
				getGenerator().getNodes().add(newNode);
			} else if (c.getConstraintType() instanceof SemesterLinkModuleConstraintType) {
				addInnerNode(newNode);
				getGenerator().getNodes().add(newNode);
			}
			if (!y) {
				newNode.fulfillConstraints(random);				
			}
		}
		setConstraintsFulfilled(true);
	}
	@Override
	protected void addChild(Node node) {
		if (!children.contains(node) && !getParents().contains(node)) {
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