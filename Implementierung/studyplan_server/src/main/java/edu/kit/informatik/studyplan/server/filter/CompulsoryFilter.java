package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleAttributeNames;

import static edu.kit.informatik.studyplan.server.filter.Utils.setOf;

import java.util.*;

/**
 * Repräsentiert einen Pflicht-/Wahlmodul-Auswahlfilter mit Filterung nach
 * Pflicht-, Wahlmodulen oder beidem als Wahlmöglichkeiten.
 */
public class CompulsoryFilter extends ListFilter<Set<Boolean>> {
	/**
	 * Erzeugt einen neuen Pflicht-/Wahlmodul-Auswahlfilter mit gegebener
	 * festgelegter Auswahl.
	 *
	 * @param selection
	 *            die Nummer des ausgewählten Elements
	 */
	public CompulsoryFilter(int selection) {
		super(selection);
	}

	@Override
	public List<String> getItemStrings() {
		return Arrays.asList("Pflicht/Wahl", "Pflichtmodule", "Wahlmodule");
	}




	@Override
	public List<Set<Boolean>> getItemObjects() {
		return Arrays.asList(setOf(true, false), setOf(true), setOf(false));
	}

	@Override
	public String getAttributeName() {
		return ModuleAttributeNames.IS_COMPULSORY;
	}
}
