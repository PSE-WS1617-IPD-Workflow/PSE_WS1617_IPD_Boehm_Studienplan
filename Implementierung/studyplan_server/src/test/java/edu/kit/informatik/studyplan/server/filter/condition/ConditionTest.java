package edu.kit.informatik.studyplan.server.filter.condition;

import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConditionTest {
    @Test
    public void createEquals() {
        Category category = new Category();
        String lhs = "Scala";
        Condition condition = Condition.createEquals(lhs, category);
        assertEquals(lhs, condition.getLhsName());
        assertEquals(1, condition.getRhsValues().length);
        assertEquals(category, condition.getRhsValues()[0]);
    }

    @Test
    public void createBetween() throws Exception {
        int lower = 41;
        int upper = 43;
        String lhs = "Haskell";
        Condition condition = Condition.createBetween(lhs, lower, upper);
        assertEquals(lhs, condition.getLhsName());
        assertEquals(2, condition.getRhsValues().length);
        assertEquals((double) lower, condition.getRhsValues()[0]);
        assertEquals((double) upper, condition.getRhsValues()[1]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createBetweenInvalidRange() throws Exception {
        int lower = 1337;
        int upper = 42;
        String lhs = "Java";
        Condition.createBetween(lhs, lower, upper);
    }

    @Test
    public void createContains() throws Exception {
        String lhs = "Functional Programming", rhs = "type inference";
        Condition condition = Condition.createContains(lhs, rhs);
        assertEquals(lhs, condition.getLhsName());
        assertEquals(1, condition.getRhsValues().length);
        assertEquals(rhs, condition.getRhsValues()[0]);
    }

}