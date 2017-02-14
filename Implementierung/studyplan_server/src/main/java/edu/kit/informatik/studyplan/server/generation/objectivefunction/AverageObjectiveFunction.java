package edu.kit.informatik.studyplan.server.generation.objectivefunction;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/**
 * The average objective function calculates the average of all evaluation methods of 
 * the sub-functions. 
 */
public class AverageObjectiveFunction extends ObjectiveFunction {
	
	@JsonProperty
	private String descriptor;
	
	@Override
	public double evaluate(final Plan plan) {
		return this.getSubFunctions().stream()
				.mapToDouble(function -> function.evaluate(plan))
				.average().orElse(0);
	}

	@Override
	public String getDescriptor() {
		return descriptor;
	}

	@Override
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}
};
