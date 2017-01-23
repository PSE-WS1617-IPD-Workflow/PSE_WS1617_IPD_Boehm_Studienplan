// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.server.model.moduledata.dao;

/************************************************************/
/**
 * Factory zur ModuleDao-Erzeugung
 */
public class ModuleDaoFactory {

	/**
	 * 
	 * @return liefert das für die verwendete Datenbankschnittstelle benötigte
	 *         DAO zurück <br>
	 *         Das DAO wird mit dem übergebenen Studiengang initialisiert.
	 */
	public ModuleDao getModuleDao() {
		return new HibernateModuleDao();
	}
};
