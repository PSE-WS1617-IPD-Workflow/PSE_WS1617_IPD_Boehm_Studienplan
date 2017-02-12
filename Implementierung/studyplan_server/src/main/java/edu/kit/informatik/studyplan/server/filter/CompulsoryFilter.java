package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleAttributeNames;

/**
 * Represents a filter for compulsory/non-compulsory modules.
 */
public class CompulsoryFilter extends ListFilter<Boolean> {
	/**
	 * Creates a new CompulsoryFilter with a given selection.
	 *
	 * @param selection
	 *            if the filtered modules sould be compulsory or not
	 */
	public CompulsoryFilter(boolean selection) {
		super(selection);
	}

	@Override
	public String getAttributeName() {
		return ModuleAttributeNames.IS_COMPULSORY;
	}
}
