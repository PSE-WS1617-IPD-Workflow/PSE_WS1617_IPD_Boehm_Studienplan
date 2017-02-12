package edu.kit.informatik.studyplan.server.filter;

import java.util.Collections;
import java.util.List;

import edu.kit.informatik.studyplan.server.filter.condition.Condition;

/**
 * Represents an interval range filter for integer attributes.
 */
public abstract class RangeFilter extends AttributeFilter {
	/**
	 * the filter's lower bound
	 */
	protected int lower;
	/**
	 * the filter's upper bound
	 */
	protected int upper;
	/**
	 * the filter's minimal lower bound
	 */
	protected int min;
	/**
	 * the filter's maximal upper bound
	 */
	protected int max;

	/**
	 * Creates a new RangeFilter with given ranges. The following conditions
	 * must hold: min <= lower, lower <= upper, upper <= max, min <= max
	 * 
	 * @param lower
	 *            the filter's lower bound
	 * @param upper
	 *            the filter's upper bound
	 * @param min
	 *            the filter's minimal lower bound
	 * @param max
	 *            the filter's maximal upper bound
	 */
	protected RangeFilter(int lower, int upper, int min, int max) {
		if (!(min <= lower && lower <= upper && upper <= max && min <= max)) {
			throw new IllegalArgumentException("RangeFilter must have valid ranges");
		}
		this.lower = lower;
		this.upper = upper;
		this.min = min;
		this.max = max;
	}

	/**
	 * @return a list of Condition objects which demands for the attribute to be
	 *         inside specified range
	 */
	public List<Condition> getConditions() {
		return Collections.singletonList(Condition.createBetween(getAttributeName(), lower, upper));
	}

	public FilterType getFilterType() {
		return FilterType.RANGE;
	}

	/**
	 * 
	 * @return the filter's lower bound
	 */
	public int getLower() {
		return lower;
	}

	/**
	 * 
	 * @return the filter's upper bound
	 */
	public int getUpper() {
		return upper;
	}

	/**
	 * 
	 * @return the filter's minimum lower bound
	 */
	public int getMin() {
		return min;
	}

	/**
	 * 
	 * @return the filter's maximum upper bound
	 */
	public int getMax() {
		return max;
	}

}
