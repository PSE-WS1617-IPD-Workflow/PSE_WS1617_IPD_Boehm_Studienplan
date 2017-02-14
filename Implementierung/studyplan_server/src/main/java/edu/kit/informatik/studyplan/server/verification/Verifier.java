package edu.kit.informatik.studyplan.server.verification;

import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/**
 * interface for a plan verifier
 * @author NiklasUhl
 */
public interface Verifier {

	/**
	 * Verifies the plan by checking for constraint violations.
	 * 
	 * @param plan
	 *            the plan
	 * @return returns the result of the verification process
	 */
	VerificationResult verify(Plan plan);
};
