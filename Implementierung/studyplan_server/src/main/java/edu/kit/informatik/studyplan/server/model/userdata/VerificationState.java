package edu.kit.informatik.studyplan.server.model.userdata;

/************************************************************/

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The verification state of a plan
 */
public enum VerificationState {
	/**
	 * the plan hasn't been verified yet
	 */
	@JsonProperty("not-verified")
	NOT_VERIFIED,
	/**
	 * the plan is valid
	 */
	@JsonProperty("valid")
	VALID,
	/**
	 * the plan is invalid, which means that several constraints are violated
	 */
	@JsonProperty("invalid")
	INVALID;
};
