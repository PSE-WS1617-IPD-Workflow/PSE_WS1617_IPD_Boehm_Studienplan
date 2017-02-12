package edu.kit.informatik.studyplan.server.filter;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * FilterDescriptor for RangeFilters.
 */
public class RangeFilterDescriptor extends FilterDescriptor {
    private int min;
    private int max;

    private BiFunction<Integer, Integer, RangeFilter> constructor;

    /**
     * Creates a new Range Filter Descriptor
     * @param id filter id
     * @param filterUriIdentifier URI identifier
     * @param filterName GUI filter name
     * @param tooltip GUI tooltip
     * @param min minimal lower bound
     * @param max maximal upper bound
     * @param constructor factory method constructing a new RangeFilter of desired type
     */
    RangeFilterDescriptor(int id, String filterUriIdentifier, String filterName, String tooltip, int min, int max,
                          BiFunction<Integer, Integer, RangeFilter> constructor) {
        super(id, filterUriIdentifier, filterName, tooltip);
        this.min = min;
        this.max = max;
        this.constructor = constructor;
    }

    @Override
    public Object getDefaultJsonValue() {
        Map<String, Object> result = new HashMap<>(2);
        result.put("min", min);
        result.put("max", max);
        return result;
    }

    @Override
    public Map<String, Object> getJsonSpecification() {
        Map<String, Object> result = new HashMap<>(3);
        result.put("type", "range");
        result.put("min", min);
        result.put("max", max);
        return result;
    }

    @Override
    public AttributeFilter getFilterFromRequest(MultivaluedMap<String, String> parameters) throws BadRequestException {
        String minString = parameters.getFirst(getFilterUriIdentifier() + "-min");
        String maxString = parameters.getFirst(getFilterUriIdentifier() + "-max");
        if (minString == null || maxString == null) {
            throw new BadRequestException();
        }
        try {
            int lower = Integer.parseInt(minString);
            int upper = Integer.parseInt(maxString);
            if (!(min <= lower && upper <= max && lower <= upper && min <= max)) {
                throw new BadRequestException();
            }
            return constructor.apply(lower, upper);
        } catch (NumberFormatException ex) {
            throw new BadRequestException();
        }
    }

    /**
     *
     * @return the minimal lower bound of the range filter
     */
    public int getMin() {
        return min;
    }

    /**
     *
     * @return the maximal upper bound of the range filter
     */
    public int getMax() {
        return max;
    }
}
