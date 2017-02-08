package edu.kit.informatik.studyplan.server.rest.resources;

import edu.kit.informatik.studyplan.server.filter.Filter;
import edu.kit.informatik.studyplan.server.rest.GetParameters;
import edu.kit.informatik.studyplan.server.rest.JSONObject;

import javax.ws.rs.core.MultivaluedMap;

/**
 * Diese Klasse repräsentiert die Planmodule-Ressource.
 */
public class PlanModulesResource {

	/**
	 * GET Anfrage: Gibt die Liste der JSON-Representationen von Modulen, die :
	 * -in dem Plan mit der gegebenen ID sind und -den gegebenen Filtern
	 * entsprechen, zurück.
	 * 
	 * @param planID
	 *            ID des zu bearbeitenden Plans.
	 * @param jsonFilter
	 *            die benutzeten Filtern als Get-Parameter.
	 * @return eine Liste der JSON-Representationen von Modulen.
	 */
	public JSONObject getModules(String plan_id, GetParameters jsonFilter) {
		return null;
	}

	/**
	 * GET Anfrage: Gibt eine JSON-Representation von dem Modul mit der
	 * gegebenen ID, der in dem Plan mit der gegebenen ID ist, zurück.
	 * 
	 * @param planID
	 *            ID des zu bearbeitenden Plans.
	 * @param moduleID
	 *            ID des erforderten Modul.
	 * @return eine JSON-Representation von dem Modul als JSON Objekt.
	 */
	public JSONObject getModule(String planID, String moduleID) {
		return null;
	}

	/**
	 * PUT Anfrage: fügt das Modul als JSON Objekt zur Plan mit den gegebenen
	 * ModulID bzw. PlanID hinzu.
	 * 
	 * @param planID
	 *            ID des zu bearbeitenden Plans.
	 * @param moduleID
	 *            ID des hinzuzufügenden Modul.
	 * @param jsonPutModule
	 *            das Modul als Get-Parameter.
	 * @return JSON-Representation des Moduls als JSON Objekt.
	 */
	public JSONObject putModuleSemester(String planID, String moduleID, GetParameters jsonPutModule) {
		return null;
	}

	/**
	 * DELETE-Anfrage: entfernt das Modul von dem Plan mit den gegebenen ModulID
	 * bzw. PlanID.
	 * 
	 * @param planID
	 *            ID des zu bearbeitenden Plans.
	 * @param moduleID
	 *            ID des zu entfernenden Modul.
	 */
	public void removeModuleSemester(String planID, String moduleID) {

	}

	/**
	 * PUT-Anfrage: setzt eine Bewertung als JSON Objekt für den Modul mit der
	 * gegebenen ID, der im Plan mit der gegebenen ID.
	 * 
	 * @param planID
	 *            ID des zu bearbeitenden Plans.
	 * @param moduleID
	 *            ID des zu bewertenden Modul.
	 * @param jsonModulePreference
	 *            die zu setzenden Bewertung des Moduls als GetParameters.
	 * @return die gesetzten Bewertung des Moduls als JSON Objekt.
	 */
	public JSONObject setModulePreference(String planID, String moduleID, GetParameters jsonModulePreference) {
		return null;
	}

	/**
	 * GET-Anfrage: Gibt den angefragten Filter zurück.
	 * 
	 * @param params
	 *            Anfrage als eine mehrwertige Zuordnung von Strings.
	 * @return den angefragten Filter.
	 */
	public static Filter getFilterFromRequest(MultivaluedMap<String, String> params) {
		return null;

	}

}
