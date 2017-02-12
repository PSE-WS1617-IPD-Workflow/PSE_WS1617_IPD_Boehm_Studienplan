package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.filter.condition.Condition;

import java.util.Collections;
import java.util.List;

/**
 * Represents a text search attribute filter.
 */
public abstract class ContainsFilter extends AttributeFilter {
	/**
	 * The substring to search for.
	 */
	protected String substring;

	/**
	 * Creates a new ContainsFilter with a given substring.
	 * 
	 * @param substring
	 *            the substring.
	 */
	protected ContainsFilter(String substring) {
		if (substring == null) {
			throw new IllegalArgumentException("substring must not be null");
		}
		this.substring = substring;
	}

	/**
	 * @return a list of Condition objects which demands for the presence of the given substring
	 */
	public List<Condition> getConditions() {
		return Collections.singletonList(Condition.createContains(getAttributeName(), substring));
	}

	/**
	 * @return the CONTAINS filter type.
     */
	public FilterType getFilterType() {
		return FilterType.CONTAINS;
	}

	/**
	 * 
	 * @return the substring to search for
	 */
	public String getSubstring() {
		return substring;
	}
}
