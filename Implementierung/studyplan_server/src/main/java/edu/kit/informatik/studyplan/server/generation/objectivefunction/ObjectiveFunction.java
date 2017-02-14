
package edu.kit.informatik.studyplan.server.generation.objectivefunction;

import java.util.Collection;
import java.util.LinkedList;

import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/**
 * The ObjectiveFunction class is used to collect the sub-functions and evaluates them
 * individually.
 */
public abstract class ObjectiveFunction implements PartialObjectiveFunction {
	private Collection<PartialObjectiveFunction> subFunctions;

	public ObjectiveFunction() {
		this.subFunctions = new LinkedList();
	}

	/**
	 * The evaluate method calls all sub-functions and calculates them in a certain way.
	 * @param plan the plan to evaluate.
	 */
	public abstract double evaluate(final Plan plan);

	/**
	 * Getter for the subFunctions.
	 * 
	 * @return the collection of sub-Functions from type PartialObjectiveFunction.
	 */
	public Collection<PartialObjectiveFunction> getSubFunctions() {
		return subFunctions;
	}

	/**
	 * Adds a partial objective function to the list of sub-functions.
	 * 
	 * @param objective
	 *            the partial objective functions to add.
	 */
	public void add(final PartialObjectiveFunction objective) {
		subFunctions.add(objective);
	}

	/**
	 * Deletes a partial objective function from the collection of objectiveFunction.
	 * 
	 * @param objective
	 *            the objective function to remove.
	 */
	public void remove(final PartialObjectiveFunction objective) {
		subFunctions.remove(objective);
	}
};
