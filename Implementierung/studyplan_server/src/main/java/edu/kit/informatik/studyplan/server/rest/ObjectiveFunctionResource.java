package edu.kit.informatik.studyplan.server.rest;

import java.util.List;

import edu.kit.informatik.studyplan.server.pluginmanager.GenerationManager;

/**
 * Diese Klasse repräsentiert die Zielfunktion-Ressource.
 */
public class ObjectiveFunctionResource {
	/**
	 * Erstellt eine Zielfunktion-Ressource.
	 */
	public ObjectiveFunctionResource() {

	}

	/**
	 * Einen
	 * {@link edu.kit.informatik.studyplan.server.pluginmanager.GenerationManager}
	 * Instanz um auf den Generierer zugreifen zu können.
	 */
	private GenerationManager generationManager;

	/**
	 * Gibt den generierungsmanager zurück.
	 * 
	 * @return der generationManager
	 */
	public GenerationManager getGenerationManager() {
		return generationManager;
	}

	/**
	 * GET-Anfrage: Gibt eine Liste mit allen vorhandenen Zielfunktionen als
	 * JSON Objekte zurück.
	 * 
	 * @return Liste mit allen vorhandenen Zielfunktionen als JSON Objekte.
	 */
	public List<JSONObject> getAllObjectiveFunctions() {
		return null;
	}
}
