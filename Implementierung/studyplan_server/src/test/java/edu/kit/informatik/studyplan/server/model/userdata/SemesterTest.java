package edu.kit.informatik.studyplan.server.model.userdata;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SemesterTest {

	@Test
	public void test() {
		Semester semester = new Semester();
		semester.setSemesterType(SemesterType.SUMMER_TERM);
		semester.setYear(2015);
		assertEquals(4, semester.getDistanceToCurrentSemester());
		semester.setSemesterType(SemesterType.WINTER_TERM);
		assertEquals(3, semester.getDistanceToCurrentSemester());
	}

}
