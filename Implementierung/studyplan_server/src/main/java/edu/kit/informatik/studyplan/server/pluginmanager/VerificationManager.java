package edu.kit.informatik.studyplan.server.pluginmanager;

import java.util.Iterator;
import java.util.ServiceLoader;

import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.verification.VerificationResult;
import edu.kit.informatik.studyplan.server.verification.Verifier;

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
		
	}

	/**
	 * Gibt den Verifizierer zurück.
	 * 
	 * @return verifier : der Verifizierer
	 */
	public Verifier getVerifier() {
		Verifier verifier = null;
		
		ServiceLoader<Verifier> loader = ServiceLoader.load(Verifier.class);
		Iterator<Verifier> iterator = loader.iterator();
		if (iterator.hasNext()) {
			
			verifier = iterator.next();
			
		}
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
		return getVerifier().verify(plan);
	}
}
