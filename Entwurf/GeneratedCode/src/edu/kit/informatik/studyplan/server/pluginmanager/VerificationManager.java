package edu.kit.informatik.studyplan.server.pluginmanager;

import edu.kit.informatik.studyplan.server.verification.Verifier;

/**
 * Verwaltet den Zugriff auf das Verifizierungsplug-in.
 */
public class VerificationManager {
	/**
	 * Der Verifizierer.
	 * @see edu.kit.informatik.studyplan.server.verification.Verifier
	 */
	public Verifier verifier;

	/**
	 * Gibt den Verifizierer zurück.
	 * @return verifier : der Verifizierer
	 */
	public Verifier getVerifier() {
		return verifier;
	}
}
