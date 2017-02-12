package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.Utils;
import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleAttributeNames;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a category list filter. Provides Module categories to choose from.
 */
public class CategoryFilter extends ListFilter<Category> {
	private Discipline discipline;

	/**
	 * Creates a new CategoryFilter with a given selection
	 *
	 * @param selection
	 *            the selected category
	 * @param discipline
	 * 			  the discipline of the categories to choose from
	 */
	public CategoryFilter(Category selection, Discipline discipline) {
		super(selection);
		this.discipline = discipline;
	}

	@Override
	public List<String> getItemStrings() {
		return getItemObjects().stream()
			.map(Category::getName)
			.collect(Collectors.toList());
	}

	@Override
	public List<Category> getItemObjects() {
		return Utils.withModuleDao(moduleDao -> moduleDao.getCategories(discipline));
	}

	@Override
	public String getAttributeName() {
		return ModuleAttributeNames.CATEGORY;
	}

	/**
	 * @return the discipline of the categories to choose from
     */
	public Discipline getDiscipline() {
		return discipline;
	}
}
