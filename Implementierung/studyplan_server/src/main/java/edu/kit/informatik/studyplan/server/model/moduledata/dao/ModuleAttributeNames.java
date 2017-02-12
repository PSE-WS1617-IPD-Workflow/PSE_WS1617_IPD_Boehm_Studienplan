package edu.kit.informatik.studyplan.server.model.moduledata.dao;

/**
 * Encapsulates the module attribute names required by filters.
 * @see edu.kit.informatik.studyplan.server.filter
 * @see ModuleDao
 */
public final class ModuleAttributeNames {
	
	private ModuleAttributeNames() {
		
	}
	/**
	 * credit points
	 */
	public static final String CREDIT_POINTS = "creditPoints";
	/**
	 *category
	 */
	public static final String CATEGORY = "categories";
	/**
	 * module type
	 */
	public static final String MODULE_TYPE = "moduleDescription.moduleType";
	/**
	 * cycle type
	 */
	public static final String CYCLE_TYPE = "cycleType";
	/**
	 * discipline
	 */
	public static final String DISCIPLINE = "discipline";
	/**
	 * is compulsory
	 */
	public static final String IS_COMPULSORY = "compulsory";
	/**
	 * name
	 */
	public static final String NAME = "name";
};
