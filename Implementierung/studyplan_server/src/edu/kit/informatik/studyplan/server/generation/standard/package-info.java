/**
 * Das Paket server.generation.standard beinhaltet eine konkrete 
 * Generation-Implementierung. Hierzu wird neben der 
 * Generator-implementierenden Klasse SimpleGenerator eine Graphstruktur
 * verwendet. Das Kompositum besteht aus Knoten vom Typ "Node", die 
 * entweder Ausgangskanten zu anderen Knoten "Node" haben (NodeWithOutput)
 * oder keine Ausgangskanten haben(NodeWithoutOutput). 
 * Zudem nutzt der SimpleGenerator die Klasse WeightFunction.
 */
package edu.kit.informatik.studyplan.server.generation.standard;