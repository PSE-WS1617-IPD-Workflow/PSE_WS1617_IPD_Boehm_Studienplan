package edu.kit.informatik.studyplan.server.generation.objectivefunction;

import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/**
 * Evaluates the plan according to the number of semesters in the plan. 
 * The lower the amount of credit points in the plan, the better the evaluation is.
 * Note that if the amount is less then the threshold, the plan would obtain the 
 * highest evaluation.
 */
public class MinimalECTSAtomObjectiveFunction extends AtomObjectiveFunction {
	
	private static double quotient = 1.0 / 1.03;
	
	/*
	 * {@inheritDoc}
	 */
	@Override
	public double evaluate(final Plan plan) {
		int threshold = (int) plan.getUser().getDiscipline().getFields()
				.stream().mapToDouble(field -> field.getMinEcts()).sum();
		int exponent = (int) plan.getCreditPoints() - threshold;
		if (exponent < 0) {
			exponent = 0;
		}
		return Math.pow(quotient, exponent);
	}
};
