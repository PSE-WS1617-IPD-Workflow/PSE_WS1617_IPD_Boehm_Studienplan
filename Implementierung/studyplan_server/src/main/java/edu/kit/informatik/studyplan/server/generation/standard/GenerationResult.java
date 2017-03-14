package edu.kit.informatik.studyplan.server.generation.standard;

import edu.kit.informatik.studyplan.server.model.userdata.Plan;
/**
 * This Class represents the result of a random generation executed by the generator.
 * It consists of a plan,which is the plan generated, and a nodesList, which is the list of 
 * nodes based on which the plan was generated.
 * @author Nada_Chatti
 *
 */
public class GenerationResult {
	/**
	 * The result of the evaluation of the plan 
	 */
	double evaluation;
	/**
	 * The plan generated.
	 */
	Plan plan;
	/**
	 * The list of nodes based on which the plan was generated.
	 */
	NodesList nodesList;
	/**
	 * Creates a new GenerationResult and stores in it the plan and nodesList created.
	 * @param plan generated
	 * @param nodesList based on which the plan has been generated.
	 */
	GenerationResult(Plan plan , NodesList nodesList) {
		this.plan = plan;
		this.nodesList = nodesList;
	}
	/**
	 * @return the plan generated and stored in this generationResult.
	 */
	Plan getPlan() {
		return plan;
	}
	/**
	 * @return the nodesList, based on which the plan has been generated, stored in 
	 * this generationResult.
	 */
	NodesList getNodesList() {
		return nodesList;
	}
}
