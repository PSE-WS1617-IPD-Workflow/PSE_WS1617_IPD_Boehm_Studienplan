package edu.kit.informatik.studyplan.server.generation.standard;


import java.util.List;

import edu.kit.informatik.studyplan.server.generation.standard.Node;
import edu.kit.informatik.studyplan.server.generation.standard.NodeWithOutput;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;

/************************************************************/
/**
 * Die Klasse NodeWithoutOutput erbt von Node und stellt Blätter der
 * Graphenstruktur da, das heißt Module, die nicht Voraussetzung für ein anderes
 * Modul des Studienplans sind.
 */
public class NodeWithoutOutput extends Node {

	protected NodeWithoutOutput(Module module) {
		super(module);
	}

	@Override
	protected NodesList getChildren() {
		return null;
	}

	@Override
	protected void addChild(Node node) {
		
	}

	@Override
	protected boolean removeChild(Node node) {
		return false;
	}

	@Override
	protected void fulfillConstraints(boolean random) {
	}

}
