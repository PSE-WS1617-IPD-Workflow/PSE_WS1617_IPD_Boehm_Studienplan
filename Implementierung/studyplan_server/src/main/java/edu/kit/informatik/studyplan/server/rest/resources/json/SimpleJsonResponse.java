package edu.kit.informatik.studyplan.server.rest.resources.json;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper class used to build a simple JSON response with one attribute.
 * @author Daniel Jungkind
 */
public final class SimpleJsonResponse {
	
	private SimpleJsonResponse() { }
    /**
     * Builds a map with one entry (key, value) and returns it.
     * @param key the key string
     * @param value the value object
     * @param <V> type parameter for the value type
     * @return the map {(key, value)}.
     */
    public static <V> Map<String, V> build(String key, V value) {
        Map<String, V> result = new HashMap<>(1);
        result.put(key, value);
        return result;
    }
}
