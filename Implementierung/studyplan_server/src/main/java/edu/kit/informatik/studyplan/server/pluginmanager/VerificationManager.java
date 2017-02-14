package edu.kit.informatik.studyplan.server.pluginmanager;

import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.verification.VerificationResult;
import edu.kit.informatik.studyplan.server.verification.Verifier;
import edu.kit.informatik.studyplan.server.verification.standard.StandardVerifier;

/**
 * Manages the access to the verification plug-in, that contains the verifier interface.
 */
public class VerificationManager {
	/**
	 * Creates a verification manager.
	 */
	public VerificationManager() {
		this.verifier = new StandardVerifier();
	}

	/**
	 * The verifier.
	 * 
	 * @see edu.kit.informatik.studyplan.server.verification.Verifier
	 */
	public Verifier verifier;

	/**
	 * returns the verifier.
	 * 
	 * @return the verifier.
	 */
	public Verifier getVerifier() {
		return verifier;
	}

	/**
	 * This method calls the verify method of the Verifier interface
	 * {@link edu.kit.informatik.studyplan.server.verification.Verifier }.
	 * 
	 * @param plan
	 *            the plan to verify.
	 * @return a VerificationResult would be returned: that means valid if the plan doesn't
	 * contain violations and invalid if it does.
	 */
	public VerificationResult verify(Plan plan) {
		return verifier.verify(plan);

	}
}
