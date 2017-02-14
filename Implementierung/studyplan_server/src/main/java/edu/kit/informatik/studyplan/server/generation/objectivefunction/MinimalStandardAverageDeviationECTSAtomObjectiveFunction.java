package edu.kit.informatik.studyplan.server.generation.objectivefunction;

import java.util.Collections;

import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.rest.resources.DisplayablePlan;

/**
 * Minimizes the standard deviation of credit points per semester.
 * The ideal case would be that all semesters of the plan have the same amount of
 * credit points.
 */
public class MinimalStandardAverageDeviationECTSAtomObjectiveFunction extends AtomObjectiveFunction {

	/*
	 * {@inheritDoc}
	 */
	@Override
	public double evaluate(final Plan plan) {
		if (plan.getAllModuleEntries().isEmpty()) {
			return 1;
		}
		int number = Collections.max(plan.getAllModuleEntries()).getSemester();
		if (number < 2) {
			return 1;
		}
		DisplayablePlan displayablePlan = new DisplayablePlan(plan);
		double average = displayablePlan.getSemesters().stream()
			.mapToDouble(semester -> semester.getTotalCredits()).average().orElse(0);
		double partialResult = displayablePlan.getSemesters().stream()
			.mapToDouble(semester -> Math.pow(semester.getTotalCredits() - average, 2)).sum();
		double s = Math.sqrt(partialResult * (1.0/(number - 1)));
		double v = s/average;
		return 1 - v / Math.sqrt(number - 1);
	}
};
