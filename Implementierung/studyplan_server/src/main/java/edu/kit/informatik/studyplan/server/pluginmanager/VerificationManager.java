package edu.kit.informatik.studyplan.server.pluginmanager;


import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.verification.VerificationResult;
import edu.kit.informatik.studyplan.server.verification.Verifier;
import edu.kit.informatik.studyplan.server.verification.standard.StandardVerifier;

/**
 * Verwaltet den Zugriff auf das Verifizierungsplug-in, das die
 * Verifizierer-Schnittstelle enthält. Diese Schnittstelle wird von dem
 * VerificationManager adaptiert.
 */
public class VerificationManager {
	/**
	 * Erstellt einen VerificationManager.
	 */
	public VerificationManager() {
		this.verifier = new StandardVerifier();
	}
	private Verifier verifier;

	/**
	 * Gibt den Verifizierer zurück.
	 * 
	 * @return verifier : der Verifizierer
	 */
	public Verifier getVerifier() {
		return verifier;
	}

	/**
	 * Diese Methode ruft die verify Methode des
	 * {@link edu.kit.informatik.studyplan.server.verification.Verifier }.
	 * 
	 * @param plan
	 *            Ein zu verifizierender Studienplan wird übergeben.
	 * @return invalid Ein VerificationResult wird als Ergebnis der
	 *         Verifizierung zurückgegeben.
	 */
	public VerificationResult verify(Plan plan) {
		return verifier.verify(plan);

	}
}
