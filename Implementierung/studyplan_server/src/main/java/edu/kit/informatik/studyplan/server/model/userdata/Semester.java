package edu.kit.informatik.studyplan.server.model.userdata;

import java.time.LocalDate;
import java.time.Period;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class modeling a semester.
 */
@Embeddable
public class Semester implements Comparable<Semester> {

	@Column(name = "semester_type")
	@Enumerated(EnumType.STRING)
	@JsonProperty("semester-type")
	@NotNull
	@QueryParam("semester-type")
	private SemesterType semesterType;
	
	@Column(name = "year")
	@JsonProperty("year")
	@DefaultValue("-1") @QueryParam("year")
	private int year = -1;
	
	/**
	 * creates a new empty semester with year set to -1
	 */
	public Semester() {
		
	}
	
	/**
	 * Creates a new semester
	 * @param semesterType the semester Type
	 * @param year the start year of the semester
	 * @throws IllegalArgumentException if semesterType is set to <code>null</code> or year is set to non positive value
	 */
	public Semester(SemesterType semesterType, int year) throws IllegalArgumentException {
		if (semesterType == null) {
			throw new IllegalArgumentException("SemesterType must not be null");
		}
		if (year < 0) {
			throw new IllegalArgumentException("Year must be positive");
		}
		this.semesterType = semesterType;
		this.year = year;
	}

	/**
	 * Calculates the number of modules passed since this semester to the current one, including the current one.
	 * 
	 * @return die the semester number
	 */
	@JsonIgnore
	public int getDistanceToCurrentSemester() {
		return getDistanceTo(getCurrentSemester());
	}

	/**
	 *
	 * @return returns the current running semester
	 */
	public static Semester getCurrentSemester() {
		Semester currentSemester;
		LocalDate today = LocalDate.now();
		LocalDate summerTermStart = SemesterType.SUMMER_TERM.getSemesterStartDate().withYear(today.getYear());
		LocalDate winterTermStart = SemesterType.WINTER_TERM.getSemesterStartDate().withYear(today.getYear());
		if (today.isBefore(summerTermStart)) {
			currentSemester = new Semester(SemesterType.WINTER_TERM, today.getYear() - 1);
		} else {
			if (today.isBefore(winterTermStart)) {
				currentSemester = new Semester(SemesterType.SUMMER_TERM, today.getYear());
			} else {
				currentSemester = new Semester(SemesterType.WINTER_TERM, today.getYear());

			}
		}
		return currentSemester;
	}

	/**
	 * 
	 * @param semester the semester
	 * @return calculates the distance to the given semester including this one.<br>
	 * 			always returns a value greater zero
	 */
	@JsonIgnore
	public int getDistanceTo(Semester semester) {
		LocalDate thisStart = this.semesterType.getSemesterStartDate().withYear(year);
		LocalDate otherStart = semester.semesterType.getSemesterStartDate().withYear(semester.year);
		Period studyPeriod = thisStart.until(otherStart);
		int distance = Math.abs(studyPeriod.getYears()) * 2 + 1;
		if (studyPeriod.getMonths() != 0) {
			distance++;
		}
		return distance;
	}

	/**
	 * 
	 * @return returns the semester type
	 * @see edu.kit.informatik.studyplan.server.model.userdata.SemesterType
	 */
	public SemesterType getSemesterType() {
		return semesterType;
	}

	/**
	 * 
	 * @param semesterType
	 *            the semester type to set
	 */
	public void setSemesterType(SemesterType semesterType) {
		this.semesterType = semesterType;
	}

	/**
	 * 
	 * @return returns the year the semester has started
	 */
	public int getYear() {
		return year;
	}

	/**
	 * 
	 * @param year
	 *            sets the year the semester has started
	 */
	public void setYear(int year) {
		this.year = year;
	}

	@Override
	@JsonIgnore
	public int compareTo(Semester o) {
		if (year < o.getYear()) {
			return -1;
		} else {
			if (year == o.getYear()) {
				return semesterType.getSemesterStartDate().compareTo(o.semesterType.getSemesterStartDate());
			} else {
				return 1;
			}
		}
	}
	
	/**
	 * Calculates the {semester}th semester after this one.<br>
	 * Note that this.plus(1) will return a copy of this semester.
	 * If a number < 1 is provided, <code>null</code> will be return
	 * @param semester a positive semester number
	 * @return the result
	 */
	public Semester plus(int semester) {
		if (semester < 1) {
			return null;
		}
		int offset = semester - 1;
		if (offset % 2 == 0) {
			int newYear = this.year + offset / 2;
			return new Semester(this.semesterType, newYear);
		} else {
			int newYear = this.year;
			if (this.semesterType == SemesterType.WINTER_TERM) {
				newYear++;
			}
			SemesterType newSemesterType = this.semesterType.toggle();
			offset--;
			newYear += offset / 2;
			return new Semester(newSemesterType, newYear);
		}
	}

};
