package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleAttributeNames;

/**
 * Repräsentiert einen Modulnamen-Textsuchfilter.
 */
public class NameFilter extends ContainsFilter {
	/**
	 * Erzeugt einen neuen Modulnamen-Textsuchfilter mit gegebenem Suchstring.
	 * 
	 * @param substring
	 *            der Suchstring
	 */
	public NameFilter(String substring) {
		super(substring);
	}

	@Override
	public String getAttributeName() {
		return ModuleAttributeNames.NAME;
	}
}
