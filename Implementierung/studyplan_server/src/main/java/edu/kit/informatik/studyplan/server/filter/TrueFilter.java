package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.filter.condition.Condition;

import java.util.Collections;
import java.util.List;

/**
 *  Represents a filter accepting all modules.
 */
public class TrueFilter implements Filter {
	/**
	 * Creates a new TrueFilter.
	 */
	public TrueFilter() {
	}

	/**
	 * @return an empty Condition list.
	 */
	public List<Condition> getConditions() {
		return Collections.emptyList();
	}
}
