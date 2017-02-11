package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.Utils;
import edu.kit.informatik.studyplan.server.model.moduledata.ModuleType;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleAttributeNames;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDao;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Repräsentiert einen Modultyp-Wahlfilter mit den Modultypen als
 * Wahlmöglichkeiten.
 */
public class ModuleTypeFilter extends ListFilter<ModuleType> {
	/**
	 * Erzeugt einen neuen Modultyp-Wahlfilter mit gegebener festgelegter
	 * Auswahl.
	 *
	 * @param selection
	 *            die Nummer des ausgewählten Elements
	 */
	public ModuleTypeFilter(ModuleType selection) {
		super(selection);
	}

	@Override
	public List<String> getItemStrings() {
		return getItemObjects().stream()
				.map(ModuleType::getName)
				.collect(Collectors.toList());
	}

	@Override
	public List<ModuleType> getItemObjects() {
		return Utils.withModuleDao(ModuleDao::getModuleTypes);
	}

	@Override
	public String getAttributeName() {
		return ModuleAttributeNames.MODULE_TYPE;
	}
}
