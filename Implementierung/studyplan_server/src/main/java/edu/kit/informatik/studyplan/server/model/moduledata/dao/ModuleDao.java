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
	public Module getModuleById(String id);

	/**
	 * Sucht alle Module die den angegebenen Filterkritierien entsprechen und
	 * gibt diese zurück
	 * 
	 * @return die Modulliste
	 * @param filter
	 *            der Modulfilter
	 * @param discipline der Studiengang, in welchem gefiltert werden soll
	 */
	public List<Module> getModulesByFilter(Filter filter, Discipline discipline);

	/**
	 * Sucht alle Module die den angegebenen Filterkritierien entsprechen und
	 * gibt die Einträge Nr. <code>start</code> bis <code>end</code> zurück.
	 * 
	 * @return die Modulliste
	 * @param filter
	 *            der Modulfilter
	 * @param discipline der Studiengang, in welchem gefiltert werden soll
	 * @param start
	 *            Start-Index
	 * @param end
	 *            End-Index
	 */
	public List<Module> getModulesByFilter(Filter filter, Discipline discipline, int start, int end);

	/**
	 * Gibt ein zufälliges Modul, welches den angebenen Filterkriterien
	 * entspricht, zurück
	 * 
	 * @param filter
	 *            der Modulfilter
	 * @param discipline der Studiengang, aus welchem das Modul gefischt werden soll
	 * @return das Modul
	 */
	public Module getRandomModuleByFilter(Filter filter, Discipline discipline);

	/**
	 *
	 * @return gibt eine Liste der verfügbaren Studiengänge zurück
	 */
	public List<Discipline> getDisciplines();

	/**
	 * @param discipline der die Kategorien enthaltende Studiengang
	 * @return gibt eine Liste der zum Studiengang gehörenden verfügbaren Kategorien zurück
	 */
	public List<Category> getCategories(Discipline discipline);

	/**
	 * @param discipline der die Bereiche enthaltende Studiengang
	 * @return gibt eine Liste der zum Studiengang gehörenden verfügbaren Bereiche zurück
	 */
	public List<Category> getFields(Discipline discipline);
};
