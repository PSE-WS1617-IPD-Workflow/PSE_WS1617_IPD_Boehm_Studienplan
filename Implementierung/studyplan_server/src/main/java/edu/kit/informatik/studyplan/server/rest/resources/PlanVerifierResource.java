package edu.kit.informatik.studyplan.server.rest.resources;

import edu.kit.informatik.studyplan.server.pluginmanager.VerificationManager;

/**
 * Diese Klasse repräsentiert die Planverifizierer-Ressource.
 */
public class PlanVerifierResource {
	/**
	 * Erstellt eine Planverifizierer-Ressource.
	 */
	public PlanVerifierResource() {

	}

	/**
	 * Einen
	 * {@link edu.kit.informatik.studyplan.server.pluginmanager.VerificationManager}
	 * Instanz um auf den Verifizierer zugreifen zu können.
	 */
	private VerificationManager verificationManager;

	/**
	 * Gibt den Verifizierungsmanager zurück.
	 * 
	 * @return der verificationManager
	 */
	public VerificationManager getVerificationManager() {
		return verificationManager;
	}



}
