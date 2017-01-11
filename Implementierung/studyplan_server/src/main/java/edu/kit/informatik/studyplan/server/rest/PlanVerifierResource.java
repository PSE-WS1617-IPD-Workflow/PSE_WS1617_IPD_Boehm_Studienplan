package edu.kit.informatik.studyplan.server.rest;

import edu.kit.informatik.studyplan.server.pluginmanager.VerificationManager;
/**
 * Diese Klasse repräsentiert die Planverifizierer-Ressource.
 */
public class PlanVerifierResource {
	/**
	 * Erstellt eine Planverifizierer-Ressource.
	 */
	public PlanVerifierResource(){
		
	}
	/**
	 * Einen {@link edu.kit.informatik.studyplan.server.pluginmanager.VerificationManager} Instanz 
	 * um auf den Verifizierer zugreifen zu können.
	 */
	private VerificationManager verificationManager; 
	/**
	 * Gibt den Verifizierungsmanager zurück.
	 * @return der verificationManager
	 */
	public VerificationManager getVerificationManager() {
		return verificationManager;
	}
	/**
	 * GET-Anfrage:
	 * Verifiziert den Planmit den gegebenen ID, gibt den verifizierten Plan zurück und speichert ihn 
	 * in der Datenbank. 
	 * @param planID ID des plans.
	 * @return den verifizierten Plan als JSON Objekt.
	 */
	public JSONObject verifyPlan(String planID){
		return null;
	}
	
}
