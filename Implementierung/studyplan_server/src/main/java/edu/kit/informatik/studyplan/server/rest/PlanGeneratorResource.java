package edu.kit.informatik.studyplan.server.rest;

import edu.kit.informatik.studyplan.server.pluginmanager.GenerationManager;
/**
 * Diese Klasse repräsentiert die Plangenerierer-Ressource.
 */
public class PlanGeneratorResource {
	/**
	 * Erstellt eine Plangenerierer-Ressource.
	 */
	public PlanGeneratorResource(){
		
	}
	/**
	 * Einen {@link edu.kit.informatik.studyplan.server.pluginmanager.GenerationManager} Instanz 
	 * um auf den Generierer zugreifen zu können.
	 */
	private GenerationManager generationManager; 
	/**
	 * Gibt den Generierungsmanager zurück.
	 * @return der generationManager
	 */
	public GenerationManager getGenerationManager() {
		return generationManager;
	}
	/**
	 * GET-Anfrage:
	 * Erstellt und gibt einen auf Basis des Plans mit der gegebenen ID generierten Plan 
	 * als JSON-Objekt zurück.
	 * @param planID ID des Basis-Plans.
	 * @param jsonSettings die gesetzten Einstellungen des Plans als Get-Parameter. 
	 * @return den generierten Plan als JSON Objekt.
	 */
	public JSONObject generatePlan(String planID, GetParameters jsonSettings){
		return null;
	}
}
