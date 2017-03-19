package edu.kit.informatik.studyplan.server.generation.standard;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.kit.informatik.studyplan.server.model.moduledata.CycleType;

public class NodesListSorter {
	/**
	 * Auxiliary attribute: says if a child of the node currently being sorted has already been visited.
	 */
	private boolean nodeHasSortedChild;

	/**
	 * Auxiliary attribute: stores result while sorting the list.
	 */
	private LinkedList<Node> stack = new LinkedList<Node>();
	/**
	 * Auxiliary attribute: stores visited nodes while sorting the list.
	 */
	Set<Node> visited = new LinkedHashSet<Node>();
	private NodesList nodesList;
	
	NodesListSorter (NodesList nodesList) {
		this.nodesList = nodesList;
	}
	
	/**
	 * Returns a topologically sorted list of the nodes of the list given. This
	 * method considers a node and its inner nodes as one so that the result
	 * stack only has one node of the whole list.
	 * 
	 * @param nodes
	 *            list of nodes
	 * @return a list of sorted nodes
	 */
	protected List<Node> sort() {
		visited.clear();
		List<Node> result = new ArrayList<Node>();
		for (Node n : nodesList) {
			/* sort the modules that have a specific cycle type(summer, or winter) 
			 before other modules. */
			if (!visited.contains(n) && n.getModule().getCycleType() != CycleType.BOTH) {
				//initialize auxiliary attributes
				stack.clear();
				nodeHasSortedChild = false;
				sortUtil(n); // delivers a stack of sorted nodes.
				
				/*the purpose of the following if statement is to leave the priority to
				the modules with a specific cycle type as much as possible :
				- if the node has a sorted (visited) child add the stack delivered at the top
				of the result list,
				- if not add the stack delivered at the bottom of the result list. */
				if (nodeHasSortedChild) {
					result.addAll(0, stack);
				} else {
					result.addAll(result.size(), stack);
				}
			}
		}
		// sort the rest of the modules (with cycle type BOTH). 
		for (Node n : nodesList) {
			if (!visited.contains(n)) {
				//initialize auxiliary attributes
				stack.clear();
				nodeHasSortedChild = false;
				sortUtil(n);// delivers a stack of sorted nodes.
				if (nodeHasSortedChild) {
					result.addAll(0, stack);
				} else {
					result.addAll(result.size(), stack);
				}
			}
		}
		return result;
	}

	/**
	 * Adds a node to the stack recursively so that the stack always stays
	 * topologically sorted. This method considers a node and its inner nodes as
	 * one so that the result stack only has one node of the whole list.
	 * 
	 * @param node
	 *            the node to begin with
	 * @return true if one of this node's children has already been visited,
	 *         false if not.
	 */
	private void sortUtil(Node node) {
		visited.add(node);
		Node n = node;
		//mark all inner nodes as visited
		while (n.hasInnerNode()) {
			n = node.getInnerNode();
			visited.add(n);
		}
		n = node;
		//mark all outer nodes as visited
		while (n.hasOuterNode()) {
			n = node.getOuterNode();
			visited.add(n);
		}
		
		//sort the children of the node if not visited
		ArrayList<Node> children = node.getChildren();
		int j = 0;
		while (j < children.size()) {
			if (!visited.contains(children.get(j))) {
				sortUtil(children.get(j));
			} else {
				nodeHasSortedChild = true;
			}
			j++;
		}
		stack.push(node);
	}
}
