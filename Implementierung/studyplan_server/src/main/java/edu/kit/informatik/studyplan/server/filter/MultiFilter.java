package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.filter.condition.Condition;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Bündelt mehrere Filter zu einem einzigen mittels UND-Verknüpfung der
 * Filterbedingungen.
 */
public class MultiFilter implements Filter {
	private List<Filter> filters;

	/**
	 * Erzeugt einen neuen MultiFilter aus gegebenen Unterfiltern.
	 * 
	 * @param filters
	 *            die Unterfilter, die gebündelt werden sollen
	 */
	public MultiFilter(List<Filter> filters) {
		this.filters = filters;
	}

	/**
	 * Gibt alle Filter zurück, die von diesem MultiFilter gebündelt werden.
	 * 
	 * @return die gebündelten Filter
	 */
	public List<Filter> getFilters() {
		return filters;
	}

	/**
	 * Gibt eine Liste der Filterbedingungen der gebündelten Filter zurück. Diese ist ggf. leer.
	 * 
	 * @return Die Liste der Filterbedingungen
	 */
	public List<Condition> getConditions() {
		return filters.parallelStream()
			.map(Filter::getConditions)
			.flatMap(List::stream)
			.collect(Collectors.toList());
	}
}