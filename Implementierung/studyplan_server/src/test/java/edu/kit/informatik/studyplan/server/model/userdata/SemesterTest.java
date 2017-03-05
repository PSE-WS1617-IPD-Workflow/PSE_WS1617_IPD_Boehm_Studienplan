package edu.kit.informatik.studyplan.server.model.userdata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SemesterTest {
	
	@Test
	public void winterDistance() {
		Semester semester1 = new Semester(SemesterType.WINTER_TERM, 2015);
		Semester semester2 = new Semester(SemesterType.WINTER_TERM, 2016);
		assertEquals(3, semester1.getDistanceTo(semester2));
		assertEquals(3, semester2.getDistanceTo(semester1));
		semester2.setYear(2018);
		assertEquals(7, semester2.getDistanceTo(semester1));
	}
	
	@Test
	public void winterSummerDistance() {
		Semester semester1 = new Semester(SemesterType.WINTER_TERM, 2015);
		Semester semester2 = new Semester(SemesterType.SUMMER_TERM, 2016);
		assertEquals(2, semester1.getDistanceTo(semester2));
		assertEquals(2, semester2.getDistanceTo(semester1));
	}
	
	@Test
	public void summerWinterDistance() {
		Semester semester1 = new Semester(SemesterType.SUMMER_TERM, 2015);
		Semester semester2 = new Semester(SemesterType.WINTER_TERM, 2016);
		assertEquals(4, semester1.getDistanceTo(semester2));
		assertEquals(4, semester2.getDistanceTo(semester1));
	}
	
	@Test
	public void summerDistance() {
		Semester semester1 = new Semester(SemesterType.SUMMER_TERM, 2015);
		Semester semester2 = new Semester(SemesterType.SUMMER_TERM, 2016);
		assertEquals(3, semester1.getDistanceTo(semester2));
		assertEquals(3, semester2.getDistanceTo(semester1));
	}
	
	@Test
	public void compareTest() {
		Semester semester1 = new Semester(SemesterType.SUMMER_TERM, 2015);
		Semester semester2 = new Semester(SemesterType.SUMMER_TERM, 2016);
		assertTrue(semester1.compareTo(semester2) < 0);
		assertTrue(semester2.compareTo(semester1) > 0);
		assertTrue(semester1.compareTo(semester1) == 0);
		semester2.setYear(2015);
		semester2.setSemesterType(SemesterType.WINTER_TERM);
		assertTrue(semester1.compareTo(semester2) < 0);
		assertTrue(semester2.compareTo(semester1) > 0);
	}
	
	@Test
	public void plusOneTest() {
		Semester semester = new Semester(SemesterType.WINTER_TERM, 2015);
		semester = semester.plus(1);
		assertEquals(2015, semester.getYear());
		assertEquals(SemesterType.WINTER_TERM, semester.getSemesterType());
	}
	
	@Test
	public void winterPlusEvenTest() {
		Semester semester = new Semester(SemesterType.WINTER_TERM, 2015);
		semester = semester.plus(4);
		assertEquals(2017, semester.getYear());
		assertEquals(SemesterType.SUMMER_TERM, semester.getSemesterType());
	}
	
	@Test
	public void winterPlusOddTest() {
		Semester semester = new Semester(SemesterType.WINTER_TERM, 2015);
		semester = semester.plus(3);
		assertEquals(2016, semester.getYear());
		assertEquals(SemesterType.WINTER_TERM, semester.getSemesterType());
	}
	
	@Test
	public void summerPlusEvenTest() {
		Semester semester = new Semester(SemesterType.SUMMER_TERM, 2016);
		semester = semester.plus(4);
		assertEquals(2017, semester.getYear());
		assertEquals(SemesterType.WINTER_TERM, semester.getSemesterType());
	}
	
	@Test
	public void summerPlusOddTest() {
		Semester semester = new Semester(SemesterType.SUMMER_TERM, 2016);
		semester = semester.plus(3);
		assertEquals(2017, semester.getYear());
		assertEquals(SemesterType.SUMMER_TERM, semester.getSemesterType());
	}

}
