// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.server.model.moduledata.dao;

import java.util.List;

import edu.kit.informatik.studyplan.server.filter.Filter;
import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;

/************************************************************/
/**
 * DataAccessObject zum Zugriff auf die Modul-Datenbank
 */
public interface ModuleDao {

	/**
	 * 
	 * @param id
	 *            der String-Identifier des zu suchenden Moduls
	 * @return das Modul mit dem entsprechenden Identifier, <code>null</code>
	 *         wenn kein Modul gefunden
	 */
	Module getModuleById(String id);

	/**
	 * Sucht alle Module die den angegebenen Filterkritierien entsprechen und
	 * gibt diese zurück
	 * 
	 * @return die Modulliste
	 * @param filter
	 *            der Modulfilter
	 */
	List<Module> getModulesByFilter(Filter filter, Discipline discipline);

	/**
	 * Sucht alle Module die den angegebenen Filterkritierien entsprechen und
	 * gibt die Einträge Nr. <code>start</code> bis <code>end</code> zurück.
	 * 
	 * @return die Modulliste
	 * @param filter
	 *            der Modulfilter
	 * @param start
	 *            Start-Index
	 * @param end
	 *            End-Index
	 */
	List<Module> getModulesByFilter(Filter filter, Discipline discipline, int start, int end);

	/**
	 * Gibt ein zufälliges Modul, welches den angebenen Filterkriterien
	 * entspricht, zurück
	 * 
	 * @param filter
	 *            der Modulfilter
	 * @return das Modul
	 */
	Module getRandomModuleByFilter(Filter filter, Discipline discipline);

	/**
	 * 
	 * @return gibt eine Liste der verfügbaren Studiengänge zurück
	 */
	List<Discipline> getDisciplines();

	/**
	 * 
	 * @return gibt eine Liste der verfügbaren Kategorien zurück
	 */
	List<Category> getCategories();

	/**
	 * 
	 * @return gibt eine Liste der verfügbaren Vertiefungsfächer zurück
	 */
	List<Category> getSubjects();

	/**
	 * 
	 * @param disciplineId
	 *            the unique ID of a discipline
	 * @return returns the discipline with the specific ID, if not found
	 *         <code>null</code>
	 */
	Discipline getDisciplineById(int disciplineId);
};
