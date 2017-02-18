package edu.kit.informatik.studyplan.server.model.moduledata.dao;

/**
 * Encapsulates the module attribute names as string constants which are
 * necessary for the filter architecture. They are used during filter
 * application inside the ModuleDao methods for unambigous identification of
 * module properties.
 * 
 * @see edu.kit.informatik.studyplan.server.filter
 * @see ModuleDao
 */
public final class ModuleAttributeNames {

	private ModuleAttributeNames() {

	}

	/**
	 * credit points attribute string constant
	 */
	public static final String CREDIT_POINTS = "creditPoints";
	/**
	 * category attribute string constant
	 */
	public static final String CATEGORY = "categories";
	/**
	 * module type attribute string constant
	 */
	public static final String MODULE_TYPE = "moduleDescription.moduleType";
	/**
	 * cycle type attribute string constant
	 */
	public static final String CYCLE_TYPE = "cycleType";
	/**
	 * field attribute string constant
	 */
	public static final String FIELD = "field";
	/**
	 * compulsory attribute string constant
	 */
	public static final String IS_COMPULSORY = "compulsory";
	/**
	 * name attribute string constant
	 */
	public static final String NAME = "name";
};
