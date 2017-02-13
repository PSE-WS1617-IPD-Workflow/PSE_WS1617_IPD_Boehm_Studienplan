package edu.kit.informatik.studyplan.server.model.userdata;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

/************************************************************/

/**
 * A semester type
 */
public enum SemesterType {

	/**
	 * the semester started in winter
	 */
	@JsonProperty("WT")
	WINTER_TERM,
	/**
	 * the semester started in summer
	 */
	@JsonProperty("ST")
	SUMMER_TERM;

	/**
	 * 
	 * @return returns this semester's types start date with year set to 0.
	 */
	public LocalDate getSemesterStartDate() {
		switch (this) {
		case WINTER_TERM:
			return LocalDate.of(0, 10, 1);
		case SUMMER_TERM:
			return LocalDate.of(0, 4, 1);
		default:
			throw new IllegalStateException("Unknown semester type");
		}
	}

	/**
	 * 
	 * @param string
	 *            a string abbreviation for a type ("WT" or "ST")
	 * @return returns the semester type associated with the abbreviation, or
	 *         <code>null</code> if no matching type is found.
	 */
	public static SemesterType fromString(String string) {
		switch (string) {
		case "WT":
			return SemesterType.WINTER_TERM;
		case "ST":
			return SemesterType.SUMMER_TERM;
		default:
			return null;
		}
	}
};
