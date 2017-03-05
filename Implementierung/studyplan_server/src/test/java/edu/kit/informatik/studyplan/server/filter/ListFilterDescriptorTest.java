package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.filter.condition.Condition;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class ListFilterDescriptorTest {
    private ListFilterDescriptor<String> descriptor;
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
        descriptor = new ListFilterDescriptor<>(42, uri, name, tooltip,
                () -> Arrays.asList("Scala", "Haskell", "functional programming in general"),
                items -> items.stream().map("I love "::concat).collect(Collectors.toList()),
                selection -> new ListFilter<String>(selection) {
                    @Override
                    protected String getAttributeName() {
                        return attribute;
                    }
                });
    }

    @Test
    public void getDefaultJsonValue() throws Exception {
        assertEquals(0, descriptor.getDefaultJsonValue());
    }

    @Test
    public void getJsonSpecification() throws Exception {
        Map<String, Object> expected = new HashMap<>(2);
        expected.put("type", "list");
        expected.put("items", Arrays.asList(
                new ListFilterDescriptor.ListItem(0, "I love Scala"),
                new ListFilterDescriptor.ListItem(1, "I love Haskell"),
                new ListFilterDescriptor.ListItem(2, "I love functional programming in general")
        ));
        assertEquals(expected, descriptor.getJsonSpecification());
    }

    @Test(expected = BadRequestException.class)
    public void getFilterFromRequestEmptyParams() throws Exception {
        descriptor.getFilterFromRequest(new MultivaluedHashMap<>());
    }

    @Test(expected = BadRequestException.class)
    public void getFilterFromRequestInvalidRange1() throws Exception {
        MultivaluedMap<String, String> parameters = new MultivaluedHashMap<>();
        Integer selectionNumber = -1;
        parameters.putSingle(uri, selectionNumber.toString());
        descriptor.getFilterFromRequest(parameters);
    }

    @Test(expected = BadRequestException.class)
    public void getFilterFromRequestInvalidRange2() throws Exception {
        MultivaluedMap<String, String> parameters = new MultivaluedHashMap<>();
        Integer selectionNumber = 3;
        parameters.putSingle(uri, selectionNumber.toString());
        descriptor.getFilterFromRequest(parameters);
    }

    @Test(expected = BadRequestException.class)
    public void getFilterFromRequestInvalidSelectionNumber() throws Exception {
        MultivaluedMap<String, String> parameters = new MultivaluedHashMap<>();
        parameters.putSingle(uri, "Java is garbage.");
        descriptor.getFilterFromRequest(parameters);
    }

    @Test
    public void getFilterFromRequest() throws Exception {
        MultivaluedMap<String, String> parameters = new MultivaluedHashMap<>();
        Integer selectionNumber = 2;
        parameters.putSingle(uri, selectionNumber.toString());
        AttributeFilter expected = mock(ListFilter.class);
        when(expected.getAttributeName()).thenReturn(attribute);
        when(expected.getConditions()).thenReturn(
                Collections.singletonList(Condition.createEquals(attribute, "functional programming in general"))
        );
        AttributeFilter result = descriptor.getFilterFromRequest(parameters);

        assertEquals(expected.getAttributeName(), attribute);
        assertEquals(expected.getConditions(), result.getConditions());
    }

}