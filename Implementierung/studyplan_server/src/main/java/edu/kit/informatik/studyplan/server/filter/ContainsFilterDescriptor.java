package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.rest.resources.json.SimpleJsonResponse;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Map;
import java.util.function.Function;

/**
 * FilterDescriptor for ContainsFilters.
 */
public class ContainsFilterDescriptor extends FilterDescriptor {
    private Function<String, ContainsFilter> constructor;

    /**
     * Creates a new Contains Filter Descriptor
     * @param id filter id
     * @param filterUriIdentifier URI identifier
     * @param filterName GUI filter name
     * @param tooltip GUI tooltip
     * @param constructor factory method constructing a new ContainsFilter of desired type
     */
    ContainsFilterDescriptor(int id, String filterUriIdentifier, String filterName, String tooltip,
                             Function<String, ContainsFilter> constructor) {
        super(id, filterUriIdentifier, filterName, tooltip);
        this.constructor = constructor;
    }

    @Override
    public Object getDefaultJsonValue() {
        return "";
    }

    @Override
    public Map<String, Object> getJsonSpecification() {
        return SimpleJsonResponse.build("type", "contains");
    }

    @Override
    public AttributeFilter getFilterFromRequest(MultivaluedMap<String, String> parameters) {
        if (!parameters.containsKey(getFilterUriIdentifier())) {
            throw new BadRequestException();
        }
        return constructor.apply(parameters.getFirst(getFilterUriIdentifier()));
    }
}
