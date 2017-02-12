// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.filter.condition.Condition;

import java.util.List;

/**
 * Represents a filter for modules by giving a list of filter conditions.
 */
public interface Filter {

	/**
	 * Returns the filter condition as a list of {@link Condition} objects.
	 * 
	 * @return the filter condition.
	 */
	public List<Condition> getConditions();
};
