package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.Utils;
import edu.kit.informatik.studyplan.server.model.moduledata.ModuleType;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleAttributeNames;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDao;

import java.util.List;
import java.util.stream.Collectors;

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
	public String getAttributeName() {
		return ModuleAttributeNames.MODULE_TYPE;
	}
}
