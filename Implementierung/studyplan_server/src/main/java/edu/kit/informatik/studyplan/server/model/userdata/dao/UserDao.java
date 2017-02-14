package edu.kit.informatik.studyplan.server.model.userdata.dao;

import edu.kit.informatik.studyplan.server.model.userdata.User;

/**
 * DataAccessObject for accessing userdata
 * @author NiklasUhl
 */
public interface UserDao {

	/**
	 * deletes the user from database
	 * 
	 * @param user
	 *            the user
	 */
	void deleteUser(User user);

	/**
	 * Saves all changes for this user in the database.<br>
	 * If the user does not exist yet, it is created
	 * 
	 * @param user
	 *            the user
	 */
	void updateUser(User user);

	/**
	 * Searches for the given user by it's user name
	 * @param name the user name
	 * @return return the found user or <code>null</code> if not found
	 */
	User getUserByName(String name);

};
