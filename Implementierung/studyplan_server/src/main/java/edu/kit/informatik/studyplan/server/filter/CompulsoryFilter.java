package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleAttributeNames;

import java.util.Arrays;
import java.util.List;

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
	public List<String> getItemStrings() {
		return Arrays.asList("Pflichtmodule", "Wahlmodule");
	}

	@Override
	public List<Boolean> getItemObjects() {
		return Arrays.asList(true, false);
	}

	@Override
	public String getAttributeName() {
		return ModuleAttributeNames.IS_COMPULSORY;
	}
}
