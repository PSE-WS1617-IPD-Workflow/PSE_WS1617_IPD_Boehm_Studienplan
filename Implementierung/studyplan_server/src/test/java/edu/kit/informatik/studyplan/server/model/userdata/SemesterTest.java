package edu.kit.informatik.studyplan.server.model.userdata;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SemesterTest {
	
	//TODO: test compare to
	@Test
	public void winterDistance() {
		Semester semester1 = new Semester(SemesterType.WINTER_TERM, 2015);
		Semester semester2 = new Semester(SemesterType.WINTER_TERM, 2016);
		assertEquals(2, semester1.getDistanceTo(semester2));
		assertEquals(2, semester2.getDistanceTo(semester2));
		semester2.setYear(2018);
		assertEquals(6, semester2.getDistanceTo(semester1));
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

}
