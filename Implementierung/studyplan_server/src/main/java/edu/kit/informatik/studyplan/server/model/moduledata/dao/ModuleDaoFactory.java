
package edu.kit.informatik.studyplan.server.model.moduledata.dao;

/**
 * Factory for creating a module DAO
 * @author NiklasUhl
 */
public final class ModuleDaoFactory {
	
	private ModuleDaoFactory() {
		
	}

	/**
	 * 
	 * @return a new ModuleDao
	 * 
	 * @see ModuleDao
	 */
	public static ModuleDao getModuleDao() {
		return new HibernateModuleDao();
	}
};
