package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleAttributeNames;

/**
 * Represents a module name text search filter.
 */
public class NameFilter extends ContainsFilter {
	/**
	 * Creates a new NameFilter with a given substring
	 * 
	 * @param substring
	 *            the substring to search for
	 */
	public NameFilter(String substring) {
		super(substring);
	}

	@Override
	public String getAttributeName() {
		return ModuleAttributeNames.NAME;
	}
}
