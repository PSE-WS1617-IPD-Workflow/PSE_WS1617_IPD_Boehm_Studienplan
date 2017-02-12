package edu.kit.informatik.studyplan.server.filter;

import java.util.Collections;
import java.util.List;

import edu.kit.informatik.studyplan.server.filter.condition.Condition;

/**
 * Repräsentiert einen Intervall-Beschränkungs-Filter für ganzzahlige Attribute.
 */
public abstract class RangeFilter extends AttributeFilter {
	/**
	 * Die untere Schranke des Filters.
	 */
	protected int lower;
	/**
	 * Die obere Schranke des Filters.
	 */
	protected int upper;
	/**
	 * Die minimale untere Schranke des Filters.
	 */
	protected int min;
	/**
	 * Die maximale untere Schranke des Filters.
	 */
	protected int max;

	/**
	 * Erzeugt einen neuen Intervall-Filter mit gegebenen Schranken.
	 * 
	 * @param lower
	 *            untere Schranke des Filters
	 * @param upper
	 *            obere Schranke des Filters
	 * @param min
	 *            minimale untere Schranke des Filters
	 * @param max
	 *            maximale obere Schranke des Filters
	 */
	protected RangeFilter(int lower, int upper, int min, int max) {
		if (lower > upper || max < min || lower < min || upper > max) {
			throw new IllegalArgumentException("RangeFilter must have valid ranges");
		}
		this.lower = lower;
		this.upper = upper;
		this.min = min;
		this.max = max;
	}

	/**
	 * Liefert eine Filterbedingung, die vom Attributswert das Einhalten der
	 * festgelegten Intervall-Grenzen fordert.
	 * 
	 * @return die Filterbedingung als jOOQ-Condition-Objekt
	 */
	public List<Condition> getConditions() {
		return Collections.singletonList(Condition.createBetween(getAttributeName(), lower, upper));
	}

	public FilterType getFilterType() {
		return FilterType.RANGE;
	}

	/**
	 * Liefert die festgelegte untere Schranke des Filters.
	 * 
	 * @return die untere Schranke
	 */
	public int getLower() {
		return lower;
	}

	/**
	 * Liefert die festgelegte obere Schranke des Filters.
	 * 
	 * @return die obere Schranke
	 */
	public int getUpper() {
		return upper;
	}

	/**
	 * Liefert den Mindestwert der unteren Schranke des Filters.
	 * 
	 * @return der Mindestwert der unteren Schranke
	 */
	public int getMin() {
		return min;
	}

	/**
	 * Liefert den Maximalwert der oberen Schranke des Filters.
	 * 
	 * @return den Maximalwert der oberen Schranke
	 */
	public int getMax() {
		return max;
	}

}
