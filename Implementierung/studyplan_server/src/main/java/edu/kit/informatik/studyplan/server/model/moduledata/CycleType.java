package edu.kit.informatik.studyplan.server.model.moduledata;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * cycles types for a module
 * @author NiklasUhl
 * @version 1.0
 */
public enum CycleType {
	/**
	 * module is only offered in winter term
	 */
	@JsonProperty("WT")
	WINTER_TERM,
	/**
	 * module is only offered in summer term
	 */
	@JsonProperty("ST")
	SUMMER_TERM,
	/**
	 * module is offered every semester
	 */
	@JsonProperty("both")
	BOTH;
};
