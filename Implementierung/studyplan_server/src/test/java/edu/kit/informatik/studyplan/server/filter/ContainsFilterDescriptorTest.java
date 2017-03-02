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

public class ContainsFilterDescriptorTest {
    private ContainsFilterDescriptor descriptor;
    private String uri;
    private String name;
    private String tooltip;
    private String attribute;

    @Before
    public void setUp() throws Exception {
        uri = "uri";
        name = "name";
        tooltip = "tooltip";
        attribute = "attribute";
        descriptor = new ContainsFilterDescriptor(42, uri, name, tooltip,
                string -> new ContainsFilter(string) {
                    @Override
                    protected String getAttributeName() {
                        return attribute;
                    }
                });
    }

    @Test
    public void getDefaultJsonValue() throws Exception {
        assertEquals("", descriptor.getDefaultJsonValue());
    }

    @Test
    public void getJsonSpecification() throws Exception {
        Map<String, Object> expected = new HashMap<>();
        expected.put("type", "contains");
        assertEquals(expected, descriptor.getJsonSpecification());
    }

    @Test(expected = BadRequestException.class)
    public void getFilterFromRequestEmptyParams() throws Exception {
        descriptor.getFilterFromRequest(new MultivaluedHashMap<>());
    }

    @Test
    public void getFilterFromRequest() throws Exception {
        MultivaluedMap<String, String> parameters = new MultivaluedHashMap<>();
        String substring = "substring";
        parameters.putSingle(uri, substring);
        AttributeFilter expected = mock(ContainsFilter.class);
        when(expected.getAttributeName()).thenReturn(attribute);
        when(expected.getConditions()).thenReturn(
                Collections.singletonList(Condition.createContains(attribute, substring))
        );
        AttributeFilter result = descriptor.getFilterFromRequest(parameters);

        assertEquals(expected.getAttributeName(), attribute);
        assertEquals(expected.getConditions(), result.getConditions());
    }

}