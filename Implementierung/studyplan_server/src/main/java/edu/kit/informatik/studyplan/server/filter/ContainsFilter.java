package edu.kit.informatik.studyplan.server.filter;

/**
 * Repräsentiert einen Textsuch-Attribut-Filter.
 */
public abstract class ContainsFilter implements AttributeFilter {
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
		this.substring = substring;
	}

	/**
	 * Liefert eine Filterbedingung, die das Vorkommen des Substrings im
	 * Attributswert fordert.
	 * 
	 * @return die Filterbedingung als jOOQ-Condition-Objekt
	 */
	public Condition getCondition() {
		return null;
	}

	public abstract FilterDescriptor getDescriptor();

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
