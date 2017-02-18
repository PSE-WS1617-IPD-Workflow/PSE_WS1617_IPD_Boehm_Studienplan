package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.model.moduledata.CycleType;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleAttributeNames;

/**
 * Represents a cycle type list filter to distinguish between winter term, summer term or both.
 */
public class CycleTypeFilter extends ListFilter<CycleType> {
	/**
	 * Creates a new CycleTypeFilter with a given selection.
	 *
	 * @param selection
	 *            the selected cycle type
	 */
	public CycleTypeFilter(CycleType selection) {
		super(selection);
	}

	@Override
	protected String getAttributeName() {
		return ModuleAttributeNames.CYCLE_TYPE;
	}

}
