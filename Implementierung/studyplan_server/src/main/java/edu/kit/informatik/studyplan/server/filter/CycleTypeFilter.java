package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.model.moduledata.CycleType;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleAttributeNames;

import java.util.Arrays;
import java.util.List;

/**
 * Repräsentiert einen Turnus-Auswahlfilter mit Filterung nach Winter-,
 * Sommersemester oder beidem als Wahlmöglichkeiten.
 */
public class CycleTypeFilter extends ListFilter<CycleType> {
	/**
	 * Erzeugt einen neuen Turnus-Auswahlfilter mit gegebener festgelegter
	 * Auswahl.
	 *
	 * @param selection
	 *            die Nummer des ausgewählten Elements
	 */
	public CycleTypeFilter(CycleType selection) {
		super(selection);
	}

	@Override
	public List<String> getItemStrings() {
		return Arrays.asList("WS/SS", "WS", "SS");
	}

	@Override
	public List<CycleType> getItemObjects() {
		return Arrays.asList(CycleType.BOTH, CycleType.WINTER_TERM, CycleType.SUMMER_TERM);
	}

	@Override
	public String getAttributeName() {
		return ModuleAttributeNames.CYCLE_TYPE;
	}

}
