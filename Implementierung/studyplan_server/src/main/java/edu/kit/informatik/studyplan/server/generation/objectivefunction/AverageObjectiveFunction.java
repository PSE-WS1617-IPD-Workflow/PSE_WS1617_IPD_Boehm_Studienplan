package edu.kit.informatik.studyplan.server.generation.objectivefunction;

import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/**
 * The average objective function calculates the average of all evaluation methods of 
 * the sub-functions. 
 */
public class AverageObjectiveFunction extends ObjectiveFunction {

	@Override
	public double evaluate(final Plan plan) {
		return this.getSubFunctions().stream()
				.mapToDouble(function -> function.evaluate(plan))
				.average().orElse(0);
	}
};
