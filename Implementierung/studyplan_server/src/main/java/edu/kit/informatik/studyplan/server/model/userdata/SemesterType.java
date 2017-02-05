// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.server.model.userdata;

import java.time.LocalDate;

/************************************************************/

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Modelliert Semester-Typen
 */
public enum SemesterType {

	/**
	 * Wintersemester
	 */
	@JsonProperty("WT")
	WINTER_TERM,
	/**
	 * Sommersemester
	 */
	@JsonProperty("ST")
	SUMMER_TERM;

	public LocalDate getSemesterStartDate() {
		switch (this) {
		case WINTER_TERM:
			return LocalDate.of(0, 10, 1);
		case SUMMER_TERM:
			return LocalDate.of(0, 4, 1);
		default:
			return null;
		}
	}
};
