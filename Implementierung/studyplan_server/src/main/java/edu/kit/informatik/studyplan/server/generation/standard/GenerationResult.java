package edu.kit.informatik.studyplan.server.generation.standard;

import edu.kit.informatik.studyplan.server.model.userdata.Plan;

public class GenerationResult {

	Plan plan;
	NodesList nodesList;
	
	GenerationResult(Plan plan , NodesList nodesList) {
		this.plan = plan;
		this.nodesList = nodesList;
	}
	
	Plan getPlan() {
		return plan;
	}
	NodesList getNodesList() {
		return nodesList;
	}
}
