package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.filter.condition.Condition;

import java.util.Collections;
import java.util.List;

/**
 * Repräsentiert einen Listenauswahl-Attribut-Filter.
 */
public abstract class ListFilter<T> extends AttributeFilter {
	/**
	 * Die Nummer des ausgewählten Elements.
	 */
	protected T selection;

	/**
	 * Erzeugt einen neuen Auswahlfilter mit gegebener festgelegter Auswahl.
	 * 
	 * @param selection
	 *            die Nummer des ausgewählten Elements
	 */
	protected ListFilter(T selection) {
		if (selection == null) {
			throw new IllegalArgumentException("Filter selection must not be null.");
		}
		this.selection = selection;
	}

	/**
	 * Liefert eine Filterbedingung, die die Übereinstimmung des untersuchten
	 * Attributswertes mit der festgelegten Auswahl (selection) fordert.
	 * 
	 * @return die Filterbedingung als jOOQ-Condition-Objekt
	 */
	public List<Condition> getConditions() {
		return Collections.singletonList(Condition.createEquals(getAttributeName(), selection));
	}

	public FilterType getFilterType() {
		return FilterType.LIST;
	}

	/**
	 * Liefert die Nummer des selektierten Elements.
	 * 
	 * @return die Element-Nummer
	 */
	public T getSelection() {
		return selection;
	}

	/**
	 * Liefert alle Wahlmöglichkeiten dieses Auswahl-Filters als Strings.
	 * 
	 * @return die Wahlmöglichkeiten des Auswahl-Filters as Strings
	 */
	public abstract List<String> getItemStrings();

	/**
	 * Liefert Objekt-Repräsentationen aller Wahlmöglichkeiten dieses Auswahl-Filters.
	 *
	 * @return die Wahlmöglichkeiten des Auswahl-Filters als Objekte
     */
	public abstract List<T> getItemObjects();
}
