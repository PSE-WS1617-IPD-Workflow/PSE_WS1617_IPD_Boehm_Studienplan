package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleAttributeNames;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDaoFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Repräsentiert einen Kategorie-Wahlfilter mit den Modulkategorien als
 * Wahlmöglichkeiten.
 */
public class CategoryFilter extends ListFilter<Category> {
	private Discipline discipline;

	/**
	 * Erzeugt einen neuen Kategorie-Wahlfilter mit gegebener festgelegter
	 * Auswahl.
	 *
	 * @param selection
	 *            die Nummer des ausgewählten Elements
	 * @param discipline
	 * 			  the discipline of the categories
	 */
	public CategoryFilter(int selection, Discipline discipline) {
		super(selection);
		this.discipline = discipline;
	}

	@Override
	public List<String> getItemStrings() {
		return getItemObjects().parallelStream()
			.map(Category::getName)
			.collect(Collectors.toList());
	}

	@Override
	public List<Category> getItemObjects() {
		return ModuleDaoFactory.getModuleDao().getCategories(discipline);
	}

	@Override
	public String getAttributeName() {
		return ModuleAttributeNames.CATEGORY;
	}

	public Discipline getDiscipline() {
		return discipline;
	}
}
