package edu.kit.informatik.studyplan.server.generation.standard;

/************************************************************/
/**
 * The class weight function contains a method, that the amount of credit points 
 * of a node and all its inner and outer nodes. 
 * 
 * @author Nada_Chatti
 * @version 1.0
 */
public class WeightFunction {
	/**
	 * The getWeight method calculates the credit points of a node and all its inner 
	 * nodes, adds it, and returns that double number.
	 * 
	 * @param node
	 * 			the node whose weight is to be determined.
	 * @return the sum of the credit points of this node and all its inner 
	 * and outer nodes.
	 */
	public double getWeight(Node node) {
		double r = node.getModule().getCreditPoints();
		Node n = node;
		while (n.getInnerNode() != null) {
			n = n.getInnerNode();
//			System.out.println(n.getModule().getIdentifier());
			r += n.getModule().getCreditPoints();
		}
		n = node;
		while (n.getOuterNode() != null) {
			n = n.getOuterNode();
			r += n.getModule().getCreditPoints();
		}
		return r;
	}
};
