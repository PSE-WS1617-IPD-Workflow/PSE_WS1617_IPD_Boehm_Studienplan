package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.model.moduledata.ModuleType;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleAttributeNames;

/**
 * Represents a module type list filter with the module types as options.
 */
public class ModuleTypeFilter extends ListFilter<ModuleType> {
	/**
	 * Creates a new ModuleTypeFilter with a given selection.
	 *
	 * @param selection
	 *            the selected module type
	 */
	public ModuleTypeFilter(ModuleType selection) {
		super(selection);
	}

	@Override
	protected String getAttributeName() {
		return ModuleAttributeNames.MODULE_TYPE;
	}
}
