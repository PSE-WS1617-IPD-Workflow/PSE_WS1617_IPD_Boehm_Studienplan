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
	 */
	public CategoryFilter(Category selection) {
		super(selection);
	}



	@Override
	public String getAttributeName() {
		return ModuleAttributeNames.CATEGORY;
	}

}
