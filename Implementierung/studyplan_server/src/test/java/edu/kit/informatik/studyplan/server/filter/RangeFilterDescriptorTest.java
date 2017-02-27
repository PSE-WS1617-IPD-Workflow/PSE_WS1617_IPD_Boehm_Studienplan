package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.filter.condition.Condition;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class RangeFilterDescriptorTest {
    private RangeFilterDescriptor descriptor;
    private String uri;
    private String name;
    private String tooltip;
    private String attribute;
    private int min;
    private int max;

    @Before
    public void setUp() throws Exception {
        uri = "uri";
        name = "name";
        tooltip = "tooltip";
        attribute = "attribute";
        min = 4;
        max = 120;
        descriptor = new RangeFilterDescriptor(42, uri, name, tooltip, min, max,
                (lower, upper) -> new RangeFilter(lower, upper) {
                    @Override
                    protected String getAttributeName() {
                        return attribute;
                    }
        });
    }

    @Test
    public void getDefaultJsonValue() throws Exception {
        Map<String, Object> expected = new HashMap<>(2);
        expected.put("min", min);
        expected.put("max", max);
        assertEquals(expected, descriptor.getDefaultJsonValue());
    }

    @Test
    public void getJsonSpecification() throws Exception {
        Map<String, Object> expected = new HashMap<>(3);
        expected.put("type", "range");
        expected.put("min", min);
        expected.put("max", max);
        assertEquals(expected, descriptor.getJsonSpecification());
    }

    @Test(expected = BadRequestException.class)
    public void getFilterFromRequestEmptyParams() throws Exception {
        descriptor.getFilterFromRequest(new MultivaluedHashMap<>());
    }

    @Test(expected = BadRequestException.class)
    public void getFilterFromRequestInvalidRange1() throws Exception {
        MultivaluedMap<String, String> parameters = new MultivaluedHashMap<>();
        parameters.putSingle(uri + "-min", "30");
        parameters.putSingle(uri + "-max", "20");
        descriptor.getFilterFromRequest(parameters);
    }

    @Test(expected = BadRequestException.class)
    public void getFilterFromRequestInvalidRange2() throws Exception {
        MultivaluedMap<String, String> parameters = new MultivaluedHashMap<>();
        parameters.putSingle(uri + "-min", "3");
        parameters.putSingle(uri + "-max", "121");
        descriptor.getFilterFromRequest(parameters);
    }

    @Test(expected = BadRequestException.class)
    public void getFilterFromRequestBadNumberFormat() throws Exception {
        MultivaluedMap<String, String> parameters = new MultivaluedHashMap<>();
        parameters.putSingle(uri + "-min", "Java isn't statically typed.");
        parameters.putSingle(uri + "-max", "It's an insult for all statically typed languages.");
        descriptor.getFilterFromRequest(parameters);
    }

    @Test
    public void getFilterFromRequest() throws Exception {
        MultivaluedMap<String, String> parameters = new MultivaluedHashMap<>();
        Integer lower = 20;
        parameters.putSingle(uri + "-min", lower.toString());
        Integer upper = 30;
        parameters.putSingle(uri + "-max", upper.toString());
        AttributeFilter expected = mock(RangeFilter.class);
        when(expected.getAttributeName()).thenReturn(attribute);
        when(expected.getConditions()).thenReturn(
                Collections.singletonList(Condition.createBetween(attribute, lower, upper))
        );
        AttributeFilter result = descriptor.getFilterFromRequest(parameters);

        assertEquals(expected.getAttributeName(), attribute);
        assertEquals(expected.getConditions(), result.getConditions());
    }

}