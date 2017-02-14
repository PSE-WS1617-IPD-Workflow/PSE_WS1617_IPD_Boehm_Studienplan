package edu.kit.informatik.studyplan.server.generation.standard;

/************************************************************/
/**
 * Die Klasse Weightfunction enthält eine Methode, welche die Anzahl an
 * Creditpoints eines Knotens und aller seiner innerer Knoten bestimmt.
 */
public class WeightFunction {
	/**
	 * Die Methode getWeight errechnet die Punktanzahl eines Knoten (Node) und
	 * aller innerer Knoten dieses Knotens, addiert sie und gibt diese double-Zahl
	 * zurück.
	 * 
	 * @param node
	 *            der Knoten, dessen Creditpoint-Anzahl bestimmt werden soll.
	 * @return die Gesamtanzahl an Creditpoints des Knotens.
	 */
	public double getWeight(Node node) {
		double r = node.getModule().getCreditPoints();
		Node n = node;
		while (n.getInnerNode() != null) {
			n = n.getInnerNode();
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
