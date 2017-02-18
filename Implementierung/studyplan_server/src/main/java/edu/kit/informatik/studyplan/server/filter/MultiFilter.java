package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.filter.condition.Condition;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Combines several filters into a single one by folding the filter conditions using the AND operator.
 */
public class MultiFilter implements Filter {
	private List<Filter> filters;

	/**
	 * Creates a new MultiFilter with given sub-filters.
	 * 
	 * @param filters
	 *            the filters to combine. Might be empty.
	 */
	public MultiFilter(List<Filter> filters) {
		this.filters = filters;
	}

	/**
	 * Gibt eine Liste der Filterbedingungen der gebündelten Filter zurück. Diese ist ggf. leer.
	 * 
	 * @return a list of the combined filters' conditions. Might be empty.
	 */
	public List<Condition> getConditions() {
		return filters.stream()
			.map(Filter::getConditions)
			.flatMap(List::stream)
			.collect(Collectors.toList());
	}
}