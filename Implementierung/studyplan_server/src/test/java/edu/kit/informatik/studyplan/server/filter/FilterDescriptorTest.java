package edu.kit.informatik.studyplan.server.filter;

import org.apache.commons.lang.NotImplementedException;
import org.junit.Test;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FilterDescriptorTest {

    @Test
    public void toJson() throws Exception {
        Map<String, Object> jsonSpec = new HashMap<>(2);
        jsonSpec.put("this", "there");
        jsonSpec.put("something", true);
        int defaultValue = 42;
        FilterDescriptor descriptor = new FilterDescriptor(1337, "uri", "name", "tooltip") {
            @Override
            public Object getDefaultJsonValue() {
                return defaultValue;
            }

            @Override
            public Map<String, Object> getJsonSpecification() {
                return jsonSpec;
            }

            @Override
            public AttributeFilter getFilterFromRequest(MultivaluedMap<String, String> parameters) throws BadRequestException {
                throw new NotImplementedException();
            }
        };

        Map<String, Object> expected = new HashMap<>();
        expected.put("id", 1337);
        expected.put("name", "name");
        expected.put("uri-name", "uri");
        expected.put("default-value", defaultValue);
        expected.put("tooltip", "tooltip");
        expected.put("specification", jsonSpec);
        assertEquals(expected, descriptor.toJson());
    }

}