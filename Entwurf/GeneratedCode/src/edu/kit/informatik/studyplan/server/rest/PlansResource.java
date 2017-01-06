package edu.kit.informatik.studyplan.server.rest;

import java.util.List;

import edu.kit.informatik.studyplan.server.rest.JSONObject;
/**
 * Diese Klasse repräsentiert die Pläne-Ressource.
 */
public class PlansResource {
	/**
	 * Erstellt eine Module-Ressource.
	 */
	public PlansResource(){
		
	}
	/**
	 * POST-Anfrage:
	 * Erstellt einen neuen Studienplan.
	 * @return jsonPlan der erstellte Plan als JSON Objekt.
	 */
	public JSONObject createPlan() {
		return null;
	}
	
	/**
	 * GET-Anfrage:
	 * Gibt eine Liste aller vorhandenen StudienPläne zurück. 
	 * @param jsonPlanList einen Array aller vorhandenen StudienPläne als JSON Objekte.
	 * @return jsonPlanList eine Liste aller vorhandenen StudienPläne als JSON Objekte.
	 */
	public List<JSONObject> getPlans(JSONObject[] jsonPlanList) {
		return null;
	}

	/**
	 * PUT-Anfrage:
	 * Ersetzt den Plan mit der gegebenen ID mit den gegeben Plan .
	 * @param planID ID des zu entfernenden Plans.
	 * @param jsonPlan der zu speichernden Plan als JSON Objekt.
	 * @return jsonNewPlan der gespeicherte Plan.
	 */
	public JSONObject replacePlan(String planID, JSONObject jsonPlan) {
		return null;
	}

	/**
	 * GET-Anfrage:
	 * Gibt den Plan mit der gegebenen ID zurück.
	 * @param planID ID des angefragten Plans. 
	 * @return jsonPlan der Plan als JSON Objekt.
	 */
	public JSONObject getPlan(String plan_id) {
		return null;
	}

	/**
	 * PATCH-Anfrage:
	 * Bearbeitet den Plan mit der gegebenen ID.
	 * @param planID ID des zu bearbeitenden Plans. 
	 * @param jsonPlan der Plan als Get-Parameter.
	 * @return jsonChangedPlan JSON Objekt des bearbeiteten Plans.
	 */
	public JSONObject editPlan(String planID, GetParameters jsonPlan) {
		return null;
	}

	/**
	 * DELETE-Anfrage:
	 * Löscht den Plan mit dem gegebenen ID.
	 * @param planID ID des zu löschenden Plans.
	 * @return jsonStatus 
	 */
	public JSONObject deletePlan(String planID) {
		return null;
	}

	/**
	 * POST-Anfrage:
	 * Dupliziert der Plan mit der gegebenen ID
	 * @param planID ID des zu duplizierenden Plans.
	 * @return jsonPlan Plan als JSON Objekt.
	 */
	public JSONObject duplicatePlan(String planID) {
		return null;
	}
};
