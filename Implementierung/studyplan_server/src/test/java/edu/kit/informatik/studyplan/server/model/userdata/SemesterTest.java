package edu.kit.informatik.studyplan.server.model.userdata;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SemesterTest {
	
	//TODO: test compare to
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
		
	}
	
	@Test
	public void summerWinterDistance() {
		
	}
	
	@Test
	public void summerDistance() {
		
	}
	
	@Test
	public void compareTest() {
	}
	
	@Test
	public void currentTest() {
		int distance = new Semester(SemesterType.WINTER_TERM, 2015).getDistanceToCurrentSemester();
		assertEquals(distance, 3);
	}

}
