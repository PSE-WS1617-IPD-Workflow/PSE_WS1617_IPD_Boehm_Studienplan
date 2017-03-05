package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.filter.condition.Condition;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class MultiFilterTest {
    @Test
    public void getConditions() throws Exception {
        Filter filter1 = mock(Filter.class);
        Condition cond1 = mock(Condition.class);
        Condition cond2 = mock(Condition.class);
        when(filter1.getConditions())
                .thenReturn(Arrays.asList(cond1, cond2));

        Filter filter2 = mock(Filter.class);
        Condition cond3 = mock(Condition.class);
        when(filter2.getConditions())
                .thenReturn(Collections.singletonList(cond3));

        MultiFilter multiFilter = new MultiFilter(Arrays.asList(filter1, filter2));
        List<Condition> conditions = multiFilter.getConditions();
        assertSame(cond1, conditions.get(0));
        assertSame(cond2, conditions.get(1));
        assertSame(cond3, conditions.get(2));
        assertEquals(3, conditions.size());
    }

    @Test
    public void getConditionsEmpty() throws Exception {
        assertEquals(0, new MultiFilter(Collections.emptyList()).getConditions().size());
    }
}