package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.filter.condition.Condition;

import java.util.Collections;
import java.util.List;

/**
 *  Represents a filter accepting all modules.
 */
public class TrueFilter implements Filter {
	/**
	 * Erzeugt einen neuen TrueFilter.
	 */
	public TrueFilter() {
	}

	/**
	 * @return An empty Condition list.
	 */
	public List<Condition> getConditions() {
		return Collections.emptyList();
	}
}
