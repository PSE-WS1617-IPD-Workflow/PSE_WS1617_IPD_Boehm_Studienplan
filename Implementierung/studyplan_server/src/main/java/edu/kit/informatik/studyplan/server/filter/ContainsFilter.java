package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.filter.condition.Condition;

import java.util.Collections;
import java.util.List;

/**
 * Repräsentiert einen Textsuch-Attribut-Filter.
 */
public abstract class ContainsFilter extends AttributeFilter {
	/**
	 * Der Suchstring.
	 */
	protected String substring;

	/**
	 * Erzeugt einen neuen Textsuch-Filter mit gegebenem Suchstring.
	 * 
	 * @param substring
	 *            der Suchstring
	 */
	protected ContainsFilter(String substring) {
		if (substring == null) {
			throw new IllegalArgumentException("substring must not be null");
		}
		this.substring = substring;
	}

	/**
	 * Liefert eine Filterbedingung, die das Vorkommen des Substrings im
	 * Attributswert fordert.
	 * 
	 * @return die Filterbedingung als jOOQ-Condition-Objekt
	 */
	public List<Condition> getConditions() {
		return Collections.singletonList(Condition.createContains(getAttributeName(), substring));
	}

	public FilterType getFilterType() {
		return FilterType.CONTAINS;
	}

	/**
	 * Liefert den Substring, nach welchem gefiltert werden soll.
	 * 
	 * @return der Substring
	 */
	public String getSubstring() {
		return substring;
	}
}
