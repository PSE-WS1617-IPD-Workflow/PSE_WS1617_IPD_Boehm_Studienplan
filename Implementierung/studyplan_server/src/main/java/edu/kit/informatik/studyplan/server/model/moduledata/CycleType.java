package edu.kit.informatik.studyplan.server.model.moduledata;


import com.fasterxml.jackson.annotation.JsonProperty;

import edu.kit.informatik.studyplan.server.model.userdata.SemesterType;

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
	
	/**
	 * check if the given semester type matches this cycle type.<br>
	 * WINTER_TERM and WINTER_TERM match,
	 * SUMMER_TERM and SUMMER_TERM match,
	 * BOTH matches everything
	 * @param semesterType a semester type
	 * @return the result
	 */
	public boolean matches(SemesterType semesterType) {
		if (semesterType == null) {
			return false;
		}
		if (this == BOTH) {
			return true;
		} else {
			return semesterType.toString().equals(this.toString());
		}
	}
		
};
