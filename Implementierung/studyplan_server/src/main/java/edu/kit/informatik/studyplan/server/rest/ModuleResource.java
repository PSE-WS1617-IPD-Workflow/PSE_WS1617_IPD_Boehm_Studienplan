package edu.kit.informatik.studyplan.server.rest;

import java.util.List;

/**
 * Diese Klasse repräsentiert die Modul-Ressource.
 */
public class ModuleResource {
	/**
	 * Erstellt eine Module-Ressource.
	 */
	public ModuleResource() {

	}

	/**
	 * GET Anfrage: Gibt eine Liste der JSON-Representationen von Modulen, die
	 * dem gegebenen Filter entsprechen, zurück.
	 * 
	 * @param jsonFilter
	 *            die benutzeten Filtern als Get-Parameter.
	 * @return eine Liste der JSON-Representationen von Modulen.
	 */
	public List<JSONObject> getModules(GetParameters jsonFilter) {
		return null;
	}

	/**
	 * Get Anfrage: Gibt eine JSON-Representation von dem Modul mit der
	 * gegebenen ID zurück.
	 * 
	 * @param moduleID
	 *            ID des erforderten Modul.
	 * @return eine JSON-Representation von dem Modul.
	 */
	public JSONObject getModule(String moduleID) {
		return null;
	}

	/**
	 * GET Anfrage: Gibt eine Liste der JSON-Representationen von allen
	 * Fachrichtungen zurück.
	 * 
	 * @return eine Liste der JSON-Representationen von allen Fachrichtungen.
	 */
	public List<JSONObject> getAllDisciplines() {
		return null;
	}

	/**
	 * GET Anfrage: Gibt eine Liste der JSON-Representationen von allen
	 * Vertiefungsfächer zurück.
	 * 
	 * @return eine Liste der JSON-Representationen von allen Vertiefungsfächer.
	 */
	public List<JSONObject> getAllSubjects() {
		return null;
	}
}
