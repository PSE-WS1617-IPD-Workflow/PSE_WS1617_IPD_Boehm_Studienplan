package edu.kit.informatik.studyplan.server.filter;

import org.jooq.Condition;

import java.util.List;

import static org.jooq.impl.DSL.trueCondition;

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
	 * Gibt die UND-Verknüpfung der Filterbedingungen der gebündelten Filter als
	 * Filterbedingung zurück, oder eine konstant wahre Filterbedingung, falls
	 * filters leer ist.
	 * 
	 * @return Die neue Filterbedingung als jOOQ-Condition-Objekt
	 */
	public org.jooq.Condition getCondition() {
		return filters.parallelStream()
			.map(Filter::getCondition)
			.reduce(trueCondition(), Condition::and);
	}
}