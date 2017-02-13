package edu.kit.informatik.studyplan.server.model.userdata.dao;

/**
 * factory for PlanDao creation
 * @author NiklasUhl
 */
public final class PlanDaoFactory {
	
	private PlanDaoFactory() { }

	/**
	 * 
	 * @return returns a concrete database specific implementation of PlanDao
	 * 
	 * @see PlanDao
	 * 
	 */
	public static PlanDao getPlanDao() {
		return new HibernatePlanDao();
	}
	
};
