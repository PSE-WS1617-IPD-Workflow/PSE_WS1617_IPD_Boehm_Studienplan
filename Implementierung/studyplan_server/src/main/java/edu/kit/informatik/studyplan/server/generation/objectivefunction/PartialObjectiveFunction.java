package edu.kit.informatik.studyplan.server.generation.objectivefunction;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/**
 * An Objective function implements this class. 
 * Every Objective Function can be a part of another objective function that is why every
 * objective function is a partial objective function.
 */
public interface PartialObjectiveFunction {
	

	/**
	 * @return the descriptor
	 */
	@JsonProperty
	String getDescriptor();

	/**
	 * @param descriptor the descriptor to set
	 */
	void setDescriptor(String descriptor);
	
	/**
	  * Evaluates a plan and according to that evaluation returns a value between 0 and 1.
	 * 
	 * @return a value between 0 and 1 that evaluates a plan.
	 * 			A plan with the evaluation 1 is an ideal plan.
	 * @param plan
	 *            the plan to evaluate
	 */
	double evaluate(Plan plan);
};
