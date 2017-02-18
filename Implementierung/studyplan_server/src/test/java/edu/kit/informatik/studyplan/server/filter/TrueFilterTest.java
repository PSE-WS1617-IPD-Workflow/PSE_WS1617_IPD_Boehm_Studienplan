package edu.kit.informatik.studyplan.server.filter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TrueFilterTest {
    @Test
    public void getConditions() throws Exception {
        assertEquals(0, new TrueFilter().getConditions().size());
    }

}