package edu.kit.informatik.studyplan.server.generation.objectivefunction;

import java.util.Collections;

import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/**
 * Evaluates the plan according to the number of semesters in the plan. 
 * The lower the number of semesters in the plan, the better the evaluation is.
 * Theoretically a plan with only one semester obtains the best evaluation.
 */
public class MinimalSemestersAtomObjectiveFunction extends AtomObjectiveFunction {
	/**
	 * Quotient needed for the evaluation.
	 */
	private static double quotient = 0.965;
	
	/**
	 * creates a new instance
	 */
	public MinimalSemestersAtomObjectiveFunction() {
		setDescriptor("minimale Semesterzahl");
	}
	
	/*
	 * {@inheritDoc}
	 */
	@Override
	public double evaluate(final Plan plan) {
		int maxSemester = Collections.max(plan.getAllModuleEntries()).getSemester();
		if (maxSemester < 1) {
			maxSemester = 1;
		}
		return Math.pow(quotient, maxSemester - 1);
	}
};
