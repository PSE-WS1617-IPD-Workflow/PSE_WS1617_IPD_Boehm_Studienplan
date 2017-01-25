package edu.kit.informatik.studyplan.server.filter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class Utils {
    @SafeVarargs
    public static <T> Set<T> setOf(T... elements) {
        return new HashSet<T>(Arrays.asList(elements));
    }

    public static <T,R> Predicate<T> equals(Function<T, R> mapper, R value) {
        return t -> mapper.apply(t).equals(value);
    }
}
