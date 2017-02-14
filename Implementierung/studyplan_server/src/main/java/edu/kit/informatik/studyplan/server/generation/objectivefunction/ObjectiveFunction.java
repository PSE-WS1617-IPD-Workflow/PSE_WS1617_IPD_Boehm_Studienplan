
package edu.kit.informatik.studyplan.server.generation.objectivefunction;

import java.util.Collection;
import java.util.LinkedList;

/**
 * The ObjectiveFunction class is used to collect the sub-functions and evaluates them
 * individually.
 */
public abstract class ObjectiveFunction implements PartialObjectiveFunction {
	private Collection<PartialObjectiveFunction> subFunctions;
	
	/**
	 * creates a new instance
	 */
	public ObjectiveFunction() {
		this.subFunctions = new LinkedList<PartialObjectiveFunction>();
	}


	/**
	 * Getter for the subFunctions.
	 * 
	 * @return the collection of sub-Functions from tyme PartialObjectiveFunction.
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
