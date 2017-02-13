package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.filter.condition.Condition;

import java.util.Collections;
import java.util.List;

/**
 * Represents a list selection attribute filter with several options.
 * @param <T> The type of the options' object representations.
 */
public abstract class ListFilter<T> extends AttributeFilter {
	/**
	 * The selected element's object representation.
	 */
	protected T selection;

	/**
	 * Creates a new ListFilter with a given selected object.
	 * 
	 * @param selection
	 *            the selected object. Must not be null.
	 */
	protected ListFilter(T selection) {
		if (selection == null) {
			throw new IllegalArgumentException("Filter selection must not be null.");
		}
		this.selection = selection;
	}

	/**
	 * 
	 * @return a list of Condition objects which demands for the module attribute to match the selection.
	 */
	public List<Condition> getConditions() {
		return Collections.singletonList(Condition.createEquals(getAttributeName(), selection));
	}

	/**
	 * 
	 * @return the selected element's object.
	 */
	public T getSelection() {
		return selection;
	}
}
