package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleAttributeNames;

import java.util.Arrays;
import java.util.List;

/**
 * Repräsentiert einen Pflicht-/Wahlmodul-Auswahlfilter mit Filterung nach
 * Pflicht-, Wahlmodulen oder beidem als Wahlmöglichkeiten.
 */
public class CompulsoryFilter extends ListFilter<Boolean> {
	/**
	 * Erzeugt einen neuen Pflicht-/Wahlmodul-Auswahlfilter mit gegebener
	 * festgelegter Auswahl.
	 *
	 * @param selection
	 *            die Nummer des ausgewählten Elements
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
