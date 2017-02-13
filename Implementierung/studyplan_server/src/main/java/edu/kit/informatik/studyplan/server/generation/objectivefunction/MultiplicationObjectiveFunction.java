package edu.kit.informatik.studyplan.server.generation.objectivefunction;

import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/**
 * This Objective Function multiplies the results evaluated with one another.
 *
 */
public class MultiplicationObjectiveFunction extends ObjectiveFunction {

	/**
	 * This method calls all evaluate methods of the sub-functions and multiplies them.
	 * When one objective function returns the value 0, this method returns 0.
	 * 
	 * @param plan
	 *            the plan to evaluate
	 */
	@Override
	public double evaluate(final Plan plan) {
		double[] values = this.getSubFunctions().stream().mapToDouble(function -> function.evaluate(plan)).toArray();
		double product = 1;
		for (int i = 0; i < values.length; i++) {
			product *= values[i];
		}
		return product;
	}

}
