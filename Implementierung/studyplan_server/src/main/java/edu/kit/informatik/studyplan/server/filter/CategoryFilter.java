package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleAttributeNames;

/**
 * Represents a category list filter. Provides Module categories to choose from.
 */
public class CategoryFilter extends ListFilter<Category> {

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
	protected String getAttributeName() {
		return ModuleAttributeNames.CATEGORY;
	}

}
