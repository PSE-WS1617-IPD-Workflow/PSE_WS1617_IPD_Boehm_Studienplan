package edu.kit.informatik.studyplan.server.filter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class Utils {
    @SafeVarargs
    static <T> Set<T> setOf(T... elements) {
        return new HashSet<T>(Arrays.asList(elements));
    }
}
