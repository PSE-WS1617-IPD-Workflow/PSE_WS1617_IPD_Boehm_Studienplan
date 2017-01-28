package edu.kit.informatik.studyplan.server.filter;

import static org.jooq.impl.DSL.trueCondition;

/**
 * Stellt einen Filter dar, der alle Module zulässt (und dessen Filterbedingung
 * daher konstant wahr ist).
 */
public class TrueFilter implements Filter {
	/**
	 * Erzeugt einen neuen TrueFilter.
	 */
	public TrueFilter() {
	}

	/**
	 * Gibt eine konstant wahre Filterbedingung zurück.
	 * 
	 * @return die Filterbedingung als jOOQ-Condition-Objekt
	 */
	public org.jooq.Condition getCondition() {
		return trueCondition();
	}
}
