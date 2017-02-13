package edu.kit.informatik.studyplan.server.model.userdata.dao;

/**
 * factory for UserDao creation
 * @author NiklasUhl
 */
public final class UserDaoFactory {
	
	private UserDaoFactory() { }

	/**
	 * 
	 * @return returns a concrete database specific implementation of UserDao
	 * 
	 * @see UserDao
	 * 
	 */
	public static UserDao getUserDao() {
		return new HibernateUserDao();
	}
	
};
